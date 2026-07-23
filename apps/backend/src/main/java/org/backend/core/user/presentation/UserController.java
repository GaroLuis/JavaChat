package org.backend.core.user.presentation;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
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

    private final Validator validator;

    public UserController(
            UserServiceInterface userService,
            Validator validator
    ) {
        this.userService = userService;
        this.validator = validator;
    }

    @GetMapping
    public List<UserResponseDto> getUsers(@RequestParam String s, @AuthenticationPrincipal SessionUser principal) {
        GetUsersDto dto = new GetUsersDto();
        dto.setInput(s);
        dto.setUserID(principal.id());

        var validations = validator.validate(dto);
        if (!validations.isEmpty()) {
            throw new ConstraintViolationException(validations);
        }

        return userService.getUsers(dto).stream()
                .map(UserMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/me")
    public UserResponseDto getMe(@AuthenticationPrincipal SessionUser principal) {
        return UserMapper.toResponseDto(userService.getUser(principal.id()));
    }
}
