package org.backend.core.user.presentation;

import org.backend.config.security.SessionUser;
import org.backend.core.user.application.UserServiceInterface;
import org.backend.core.user.application.dto.GetUsersDto;
import org.backend.core.user.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceInterface userService;

    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(@RequestParam String s, @AuthenticationPrincipal SessionUser principal) {
        GetUsersDto dto = new GetUsersDto();
        dto.setInput(s);
        dto.setUserID(principal.id());

        return userService.getUsers(dto);
    }

    @GetMapping("/me")
    public User getMe(@AuthenticationPrincipal SessionUser principal) {
        return userService.getUser(principal.id());
    }
}
