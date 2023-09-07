package ru.practicum.ewm.main.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.server.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
