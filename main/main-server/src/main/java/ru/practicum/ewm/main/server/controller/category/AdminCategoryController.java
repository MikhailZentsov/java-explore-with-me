package ru.practicum.ewm.main.server.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.category.CategoryDto;
import ru.practicum.ewm.main.api.category.NewCategoryDto;
import ru.practicum.ewm.main.server.service.CategoryService;
import ru.practicum.ewm.util.aspect.log.ToLog;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
@Validated
@ToLog
public class AdminCategoryController {
    private final CategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        return service.create(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) long catId) {
        service.delete(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto update(@Valid @RequestBody NewCategoryDto newCategoryDto, @PathVariable @Min(1) long catId) {
        return service.update(newCategoryDto, catId);
    }
}
