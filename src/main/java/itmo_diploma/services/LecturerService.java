package itmo_diploma.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import itmo_diploma.exceptions.CustomException;
import itmo_diploma.exceptions.ValidationException;
import itmo_diploma.models.Lecturer;
import itmo_diploma.requests.LecturerRequest;
import itmo_diploma.repositories.LecturerRepository;
import itmo_diploma.responses.LecturerResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Formatter;
import java.util.List;

@Service
@AllArgsConstructor
public class LecturerService {

    private final LecturerRepository lecturerRepository;
    private final ObjectMapper mapper;

    public Lecturer getLecturerFromDb(Long id) throws EntityNotFoundException {
        return lecturerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Преподаватель не найден"));
    }

    private void isLecturerExists(Long id) throws EntityNotFoundException {
        if (!lecturerRepository.existsById(id)) {
            throw new EntityNotFoundException("Преподаватель не найден");
        }
    }

    private void isLecturerExists(String email, String phone) throws EntityNotFoundException {
        if (lecturerRepository.existsByEmailOrPhone(email, phone)) {
            throw new CustomException(new Formatter().format("Преподаватель c email %s или телефоном %s уже существует", email, phone).toString(), HttpStatus.CONFLICT.value());
        }
    }

    public void updateData(Lecturer lecturer) {
        lecturerRepository.save(lecturer);
    }

    public LecturerResponse create(LecturerRequest lecturerRequest) throws ValidationException {
        Lecturer lecturer = mapper.convertValue(lecturerRequest, Lecturer.class);

        isLecturerExists(lecturer.getEmail(), lecturer.getPhone());

        Lecturer savedLecturer = lecturerRepository.save(lecturer);

        return mapper.convertValue(savedLecturer, LecturerResponse.class);
    }

    public void update(Long id, LecturerRequest lecturerRequest) throws EntityNotFoundException {
        Lecturer lecturer = getLecturerFromDb(id);

        isLecturerExists(lecturer.getEmail(), lecturer.getPhone());

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

    public LecturerResponse get(Long id) {
        isLecturerExists(id);

        return mapper.convertValue(lecturerRepository.findById(id), LecturerResponse.class);
    }

    public List<LecturerResponse> getAll() {
        List<Lecturer> lecturers = lecturerRepository.findAll();

        return lecturers.stream()
                .map(lecturer -> mapper.convertValue(lecturer, LecturerResponse.class))
                .toList();
    }
}
