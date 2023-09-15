package ru.practicum.ewm.main.server.controller.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.compilation.CompilationDto;
import ru.practicum.ewm.main.server.service.CompilationService;
import ru.practicum.ewm.util.aspect.log.ToLog;

import javax.validation.constraints.Min;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
@Validated
@ToLog
public class PublicCompilationController {
    private final CompilationService service;

    @GetMapping
    public Collection<CompilationDto> getAll(@RequestParam(required = false) boolean pinned,
                                             @RequestParam(defaultValue = "0") @Min(0) int from,
                                             @RequestParam(defaultValue = "10") @Min(0) int size) {
        return service.getAll(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable @Min(1) Long compId) {
        return service.getById(compId);
    }
}
