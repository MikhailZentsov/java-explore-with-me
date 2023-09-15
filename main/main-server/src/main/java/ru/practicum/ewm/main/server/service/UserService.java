package ru.practicum.ewm.main.server.service;

import ru.practicum.ewm.main.api.user.NewUserRequest;
import ru.practicum.ewm.main.api.user.UserDto;

import java.util.Collection;
import java.util.Set;

public interface UserService {

    Collection<UserDto> getUsers(Set<Long> ids, int from, int size);

    UserDto create(NewUserRequest userDto);

    void delete(long userId);
}
