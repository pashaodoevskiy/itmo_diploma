package itmo_diploma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo_diploma.requests.UserCourseRequest;
import itmo_diploma.requests.UserRequest;
import itmo_diploma.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@Tag(name = "Пользователи")
public class UserController extends Controller {

    private final UserService userService;

    @Operation(summary = "Обновить пользователя")
    @PutMapping
    public ResponseEntity<Object> update(@Valid @RequestBody UserRequest userRequest) {
        userService.update(userRequest);

        return response();
    }

    @Operation(summary = "Информация о пользователе")
    @GetMapping
    public ResponseEntity<Object> get() {
        return response(userService.get());
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping
    public ResponseEntity<Object> delete() {
        userService.delete();

        return response();
    }

    @Operation(summary = "Записаться на курс")
    @PostMapping("/addCourse")
    public ResponseEntity<Object> addCourse(@Valid @RequestBody UserCourseRequest userCourseRequest) {
        userService.addCourse(userCourseRequest);

        return response();
    }

    @Operation(summary = "Удалить запись на курс")
    @DeleteMapping("/removeCourse")
    public ResponseEntity<Object> removeCourse(@Valid @RequestBody UserCourseRequest userCourseRequest) {
        userService.removeCourse(userCourseRequest);

        return response();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Список всех пользователей")
    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        return response(userService.getAll());
    }
}
