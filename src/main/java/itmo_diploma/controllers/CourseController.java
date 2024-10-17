package itmo_diploma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo_diploma.requests.CourseRequest;
import itmo_diploma.requests.CourseToLecturerDto;
import itmo_diploma.responses.CourseResponse;
import itmo_diploma.responses.UserCourseResponse;
import itmo_diploma.services.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Курсы")
@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать курс")
    @PostMapping
    public CourseResponse create(@RequestBody @Valid CourseRequest courseRequest) {
        return courseService.create(courseRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "обновить курс")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid CourseRequest courseRequest) {
        courseService.update(id, courseRequest);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить курс")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Добавить преподавателя на курс")
    @PutMapping("/addLecturer")
    public ResponseEntity<Void> addLecturer(@RequestBody @Valid CourseToLecturerDto courseToLecturerDto) {
        courseService.addLecturer(courseToLecturerDto);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить преподавателя из курса")
    @DeleteMapping("/removeLecturer")
    public ResponseEntity<Void> removeLecturer(@RequestBody @Valid CourseToLecturerDto courseToLecturerDto) {
        courseService.removeLecturer(courseToLecturerDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Детальная информация по курсу")
    @GetMapping("/{id}")
    public CourseResponse get(@PathVariable Long id) {
        return courseService.get(id);
    }

    @Operation(summary = "Список курсов")
    @GetMapping("/all")
    public List<CourseResponse> getAll() {
        return courseService.getAll();
    }

    @Operation(summary = "Список курсов авторизованного пользователя")
    @GetMapping("/byUser")
    public List<UserCourseResponse> getUserCourses() {
        return courseService.getUserCourses();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Список курсов преподавателя")
    @GetMapping("/byLecturer/{id}")
    public List<CourseResponse> getLecturerCourses(@PathVariable Long id) {
        return courseService.getLecturerCourses(id);
    }
}
