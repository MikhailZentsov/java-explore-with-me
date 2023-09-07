package ru.practicum.ewm.main.server.service;

import ru.practicum.ewm.main.api.category.CategoryDto;
import ru.practicum.ewm.main.api.category.NewCategoryDto;

import java.util.Collection;

public interface CategoryService {

    CategoryDto create(NewCategoryDto newCategoryDto);

    void delete(long catId);

    CategoryDto update(NewCategoryDto newCategoryDto, long catId);

    Collection<CategoryDto> getAll(int from, int size);

    CategoryDto getById(long catId);
}
