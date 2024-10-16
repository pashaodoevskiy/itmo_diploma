package itmo_diploma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo_diploma.requests.CourseRequest;
import itmo_diploma.requests.CourseToLecturerDto;
import itmo_diploma.services.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Курсы")
@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController extends Controller{

    private final CourseService courseService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать курс")
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid CourseRequest courseRequest) {
        return response(courseService.create(courseRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "обновить курс")
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid CourseRequest courseRequest) {
        courseService.update(id, courseRequest);

        return response();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить курс")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        courseService.delete(id);

        return response();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Добавить преподавателя на курс")
    @PutMapping("/addLecturer")
    public ResponseEntity<Object> addLecturer(@RequestBody @Valid CourseToLecturerDto courseToLecturerDto) {
        courseService.addLecturer(courseToLecturerDto);
        return response();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить преподавателя из курса")
    @DeleteMapping("/removeLecturer")
    public ResponseEntity<Object> removeLecturer(@RequestBody @Valid CourseToLecturerDto courseToLecturerDto) {
        courseService.removeLecturer(courseToLecturerDto);
        return response();
    }

    @Operation(summary = "Детальная информация по курсу")
    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        return response(courseService.get(id));
    }

    @Operation(summary = "Список курсов")
    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        return response(courseService.getAll());
    }

    @Operation(summary = "Список курсов авторизованного пользователя")
    @GetMapping("/byUser")
    public ResponseEntity<Object> getUserCourses() {
        return response(courseService.getUserCourses());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Список курсов преподавателя")
    @GetMapping("/byLecturer/{id}")
    public ResponseEntity<Object> getLecturerCourses(@PathVariable Long id) {
        return response(courseService.getLecturerCourses(id));
    }
}
