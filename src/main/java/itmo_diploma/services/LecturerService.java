package itmo_diploma.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import itmo_diploma.exceptions.RecordAlreadyExistsException;
import itmo_diploma.exceptions.ValidationException;
import itmo_diploma.models.Lecturer;
import itmo_diploma.requests.CourseRequest;
import itmo_diploma.requests.LecturerRequest;
import itmo_diploma.repositories.LecturerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class LecturerService {

    private final LecturerRepository lecturerRepository;
    private final ObjectMapper mapper;

    public LecturerRequest create(LecturerRequest lecturerRequest) throws ValidationException {
        Lecturer lecturer = mapper.convertValue(lecturerRequest, Lecturer.class);

        if (lecturerRepository.findByEmailOrPhone(lecturer.getEmail(), lecturer.getPhone()) != null) {
            throw new ValidationException(Map.of("lecturer", List.of("Преподаватель уже существует")));
        }

        Lecturer savedLecturer = lecturerRepository.save(lecturer);

        return mapper.convertValue(savedLecturer, LecturerRequest.class);
    }

    public void update(Long id, LecturerRequest lecturerRequest) throws EntityNotFoundException {
        Lecturer lecturer = getLecturerFromDb(id);

        lecturer.setName(lecturerRequest.getName() == null ? lecturer.getName() : lecturerRequest.getName());
        lecturer.setSurname(lecturerRequest.getSurname() == null ? lecturer.getSurname() : lecturerRequest.getSurname());
        lecturer.setPhone(lecturerRequest.getPhone() == null ? lecturer.getPhone() : lecturerRequest.getPhone());
        lecturer.setEmail(lecturerRequest.getEmail() == null ? lecturer.getEmail() : lecturerRequest.getEmail());

        lecturerRepository.save(lecturer);
     }

    public void delete(Long id) {
        isLecturerExists(id);

        lecturerRepository.deleteById(id);
    }

    public LecturerRequest get(Long id) {
        isLecturerExists(id);

        return mapper.convertValue(lecturerRepository.findById(id), LecturerRequest.class);
    }

    public List<LecturerRequest> getAll() {
        List<Lecturer> lecturers = lecturerRepository.findAll();

        return lecturers.stream()
                .map(lecturer -> mapper.convertValue(lecturer, LecturerRequest.class))
                .toList();
    }

    public Lecturer getLecturerFromDb(Long id) throws EntityNotFoundException {
        return lecturerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Преподаватель не найден"));
    }

    private void isLecturerExists(Long id) throws EntityNotFoundException {
        if (!lecturerRepository.existsById(id)) {
            throw new EntityNotFoundException("Преподаватель не найден");
        }
    }

    public void updateData(Lecturer lecturer) {
        lecturerRepository.save(lecturer);
    }
}
