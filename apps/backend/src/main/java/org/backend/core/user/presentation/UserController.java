package org.backend.core.user.presentation;

import org.backend.config.security.SessionUser;
import org.backend.core.user.application.UserServiceInterface;
import org.backend.core.user.application.dto.GetUsersDto;
import org.backend.core.user.presentation.mapper.UserResponseDto;
import org.backend.core.user.presentation.mapper.UserMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceInterface userService;

    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> getUsers(@RequestParam String s, @AuthenticationPrincipal SessionUser principal) {
        GetUsersDto dto = new GetUsersDto();
        dto.setInput(s);
        dto.setUserID(principal.id());

        return userService.getUsers(dto).stream()
                .map(UserMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/me")
    public UserResponseDto getMe(@AuthenticationPrincipal SessionUser principal) {
        return UserMapper.toResponseDto(userService.getUser(principal.id()));
    }
}
