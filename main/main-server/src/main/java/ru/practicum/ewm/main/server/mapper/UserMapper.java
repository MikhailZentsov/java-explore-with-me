package ru.practicum.ewm.main.server.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.main.api.user.NewUserRequest;
import ru.practicum.ewm.main.api.user.UserDto;
import ru.practicum.ewm.main.api.user.UserShortDto;
import ru.practicum.ewm.main.server.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(NewUserRequest newUserRequest);

    UserDto toUserDto(User user);

    UserShortDto toUserShortDto(User user);
}
