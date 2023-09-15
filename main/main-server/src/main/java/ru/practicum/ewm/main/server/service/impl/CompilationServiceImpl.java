package ru.practicum.ewm.main.server.service.impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.api.compilation.CompilationDto;
import ru.practicum.ewm.main.api.compilation.NewCompilationDto;
import ru.practicum.ewm.main.api.compilation.UpdateCompilationRequest;
import ru.practicum.ewm.main.server.entity.Compilation;
import ru.practicum.ewm.main.server.entity.Event;
import ru.practicum.ewm.main.server.entity.QCompilation;
import ru.practicum.ewm.main.server.entity.QEvent;
import ru.practicum.ewm.main.server.mapper.CompilationMapper;
import ru.practicum.ewm.main.server.repository.CompilationRepository;
import ru.practicum.ewm.main.server.repository.EventRepository;
import ru.practicum.ewm.main.server.service.CompilationService;
import ru.practicum.ewm.main.util.exception.AlreadyExistsException;
import ru.practicum.ewm.main.util.exception.NotFoundException;
import ru.practicum.ewm.util.pageable.OffsetBasedPageRequest;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static ru.practicum.ewm.util.constant.Constants.SORT_BY_ID_ASC;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper mapper;

    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto dto) {
        try {
            Compilation compilation = mapper.toCompilation(dto);

            if (dto.getEvents() != null && !dto.getEvents().isEmpty()) {
                BooleanBuilder builder = new BooleanBuilder();
                builder.and(QEvent.event.id.in(dto.getEvents()));

                try {
                    Set<Event> events = StreamSupport
                            .stream(eventRepository.findAll(builder).spliterator(), false)
                            .collect(Collectors.toSet());
                    compilation.setEvents(events);
                } catch (DataIntegrityViolationException e) {
                    throw new AlreadyExistsException("Some events  " + dto.getEvents() + " not exists.");
                }
            } else {
                compilation.setEvents(new HashSet<>());
            }

            return mapper.toCompilationDto(compilationRepository.save(compilation));
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("Compilation with events " + dto.getEvents() + " already exists.");
        }
    }

    @Override
    @Transactional
    public void delete(Long compId) {
        getCompilationById(compId);

        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto update(UpdateCompilationRequest dto, Long compId) {
        Compilation compilation = getCompilationById(compId);

        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            compilation.setTitle(dto.getTitle());
        }
        if (dto.getPinned() != null) {
            compilation.setPinned(dto.getPinned());
        }

        if (dto.getEvents() != null && !dto.getEvents().isEmpty()) {
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(QEvent.event.id.in(dto.getEvents()));
            Collection<Event> events = StreamSupport
                    .stream(eventRepository.findAll(builder, SORT_BY_ID_ASC).spliterator(), false)
                    .collect(Collectors.toList());
            compilation.setEvents(new HashSet<>(events));
        }

        try {
            return mapper.toCompilationDto(compilationRepository.save(compilation));
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("Compilation with events " + dto.getEvents() + " already exists.");
        }
    }

    @Override
    public Collection<CompilationDto> getAll(Boolean pinned, int from, int size) {
        BooleanBuilder builder = new BooleanBuilder();

        if (pinned != null) {
            builder.and(QCompilation.compilation.pinned.eq(pinned));
        }

        Pageable pageable = new OffsetBasedPageRequest(from, size, SORT_BY_ID_ASC);

        return compilationRepository.findAll(builder, pageable)
                .getContent()
                .stream()
                .map(mapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getById(Long compId) {
        return mapper.toCompilationDto(getCompilationById(compId));
    }

    private Compilation getCompilationById(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("Compilation with ID = " + compId + " does not exists")
        );
    }
}
