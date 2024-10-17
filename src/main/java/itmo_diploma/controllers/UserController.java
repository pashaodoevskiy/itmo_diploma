package itmo_diploma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo_diploma.requests.UserCourseRequest;
import itmo_diploma.requests.UserRequest;
import itmo_diploma.responses.UserResponse;
import itmo_diploma.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Пользователи")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Обновить пользователя")
    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody UserRequest userRequest) {
        userService.update(userRequest);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Информация о пользователе")
    @GetMapping
    public UserResponse get() {
        return userService.get();
    }

    @Operation(summary = "Записаться на курс")
    @PostMapping("/addCourse")
    public ResponseEntity<Void> addCourse(@Valid @RequestBody UserCourseRequest userCourseRequest) {
        userService.addCourse(userCourseRequest);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удалить запись на курс")
    @DeleteMapping("/removeCourse")
    public ResponseEntity<Void> removeCourse(@Valid @RequestBody UserCourseRequest userCourseRequest) {
        userService.removeCourse(userCourseRequest);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Список всех пользователей")
    @GetMapping("/all")
    public List<UserResponse> getAll() {
        return userService.getAll();
    }
}
