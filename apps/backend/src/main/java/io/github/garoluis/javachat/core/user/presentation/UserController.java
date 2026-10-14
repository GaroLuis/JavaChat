package io.github.garoluis.javachat.core.user.presentation;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import io.github.garoluis.javachat.config.security.SessionUser;
import io.github.garoluis.javachat.core.user.application.UserServiceInterface;
import io.github.garoluis.javachat.core.user.application.dto.AddContactDto;
import io.github.garoluis.javachat.core.user.application.dto.CreateUserDto;
import io.github.garoluis.javachat.core.user.application.dto.DeleteContactDto;
import io.github.garoluis.javachat.core.user.application.dto.GetUsersDto;
import io.github.garoluis.javachat.core.user.application.dto.UpdateUserDto;
import io.github.garoluis.javachat.core.user.presentation.mapper.UserResponseDto;
import io.github.garoluis.javachat.core.user.presentation.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto create(@Valid @RequestBody CreateUserDto dto) {
        return UserMapper.toResponseDto(userService.create(dto));
    }

    @PutMapping("/me")
    public UserResponseDto updateMe(@Valid @RequestBody UpdateUserDto dto, @AuthenticationPrincipal SessionUser principal) {
        dto.setUserId(principal.id());

        return UserMapper.toResponseDto(userService.update(dto));
    }

    @GetMapping("/contacts")
    public List<UserResponseDto> getContacts(@AuthenticationPrincipal SessionUser principal) {
        return userService.getContacts(principal.id()).stream()
                .map(UserMapper::toResponseDto)
                .toList();
    }

    @PostMapping("/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@Valid @RequestBody AddContactDto dto, @AuthenticationPrincipal SessionUser principal) {
        dto.setUserId(principal.id());

        userService.addContact(dto);
    }

    @DeleteMapping("/contacts/{contactId}")
    public void removeContact(@PathVariable UUID contactId, @AuthenticationPrincipal SessionUser principal) {
        DeleteContactDto dto = new DeleteContactDto();
        dto.setUserId(principal.id());
        dto.setContactId(contactId);

        userService.removeContact(dto);
    }
}
