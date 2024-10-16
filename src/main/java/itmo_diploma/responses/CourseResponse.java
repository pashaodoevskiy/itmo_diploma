package itmo_diploma.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    Long id;

    String name;

    Integer price;

    @JsonFormat(pattern = "dd.MM.yyyy")
    LocalDate startDate;

    @JsonFormat(pattern = "dd.MM.yyyy")
    LocalDate endDate;

    LecturerResponse lecturer;
}
