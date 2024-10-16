package itmo_diploma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo_diploma.requests.LecturerRequest;
import itmo_diploma.services.LecturerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Преподаватели")
@RestController
@RequestMapping("/api/lecturer")
@RequiredArgsConstructor
public class LecturerController extends Controller {

    private final LecturerService lecturerService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Создать преподавателя")
    public ResponseEntity<Object> create(@RequestBody @Valid LecturerRequest lecturerRequest) {
        return response(lecturerService.create(lecturerRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Обновить преподавателя")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid LecturerRequest lecturerRequest) {
        lecturerService.update(id, lecturerRequest);

        return response();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить преподавателя")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        lecturerService.delete(id);

        return response();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Информация о преподавателе")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        return response(lecturerService.get(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список всех преподавателей")
    public ResponseEntity<Object> getAll() {
        return response(lecturerService.getAll());
    }
}
