package ru.practicum.ewm.main.server.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.main.api.category.CategoryDto;
import ru.practicum.ewm.main.api.category.NewCategoryDto;
import ru.practicum.ewm.main.server.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(NewCategoryDto newCategoryDto);

    CategoryDto toCategoryDto(Category category);
}
