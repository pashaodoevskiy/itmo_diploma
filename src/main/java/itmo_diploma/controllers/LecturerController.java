package itmo_diploma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo_diploma.requests.LecturerRequest;
import itmo_diploma.responses.LecturerResponse;
import itmo_diploma.services.LecturerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Преподаватели")
@RestController
@RequestMapping("/api/lecturer")
@RequiredArgsConstructor
public class LecturerController {

    private final LecturerService lecturerService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Создать преподавателя")
    public LecturerResponse create(@RequestBody @Valid LecturerRequest lecturerRequest) {
        return lecturerService.create(lecturerRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Обновить информацию о преподавателе")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid LecturerRequest lecturerRequest) {
        lecturerService.update(id, lecturerRequest);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить преподавателя")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lecturerService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Информация о преподавателе")
    public LecturerResponse get(@PathVariable Long id) {
        return lecturerService.get(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список всех преподавателей")
    public List<LecturerResponse> getAll() {
        return lecturerService.getAll();
    }
}
