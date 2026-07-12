package org.backend.user.presentation;

import org.backend.user.application.UserServiceInterface;
import org.backend.user.application.dto.GetUsersDto;
import org.backend.user.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceInterface userService;

    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @GetMapping
    public Set<User> get(@RequestParam String s) {
        GetUsersDto dto = new GetUsersDto();
        dto.setInput(s);

        return userService.get(dto);
    }
}
