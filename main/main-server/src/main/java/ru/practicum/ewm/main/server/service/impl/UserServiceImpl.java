package ru.practicum.ewm.main.server.service.impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.api.user.NewUserRequest;
import ru.practicum.ewm.main.api.user.UserDto;
import ru.practicum.ewm.main.server.entity.QUser;
import ru.practicum.ewm.main.server.mapper.UserMapper;
import ru.practicum.ewm.main.server.repository.UserRepository;
import ru.practicum.ewm.main.server.service.UserService;
import ru.practicum.ewm.main.util.exception.AlreadyExistsException;
import ru.practicum.ewm.main.util.exception.NotFoundException;
import ru.practicum.ewm.util.pageable.OffsetBasedPageRequest;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.ewm.util.constant.Constants.SORT_BY_ID_ASC;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Collection<UserDto> getUsers(Set<Long> ids, int from, int size) {
        BooleanBuilder builder = new BooleanBuilder();

        if (ids != null) {
            builder.and(QUser.user.id.in(ids));
        }

        final Pageable pageable = new OffsetBasedPageRequest(from, size, SORT_BY_ID_ASC);
        return userRepository.findAll(builder, pageable)
                .getContent()
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto create(NewUserRequest userDto) {
        try {
            return userMapper.toUserDto(userRepository.save(userMapper.toUser(userDto)));
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("User with email " + userDto.getEmail() + " already exists.");
        }
    }

    @Override
    @Transactional
    public void delete(long userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with ID = " + userId + " does not exists")
        );

        userRepository.deleteById(userId);
    }
}
