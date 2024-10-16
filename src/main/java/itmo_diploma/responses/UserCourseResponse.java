package itmo_diploma.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import itmo_diploma.enums.PaymentType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCourseResponse {

    Long id;

    String name;

    Integer price;

    Boolean isPaid;

    @JsonFormat(pattern = "dd.MM.yyyy")
    LocalDate startDate;

    @JsonFormat(pattern = "dd.MM.yyyy")
    LocalDate endDate;

    @JsonFormat(pattern = "dd.MM.yyyy")
    LocalDateTime paymentDate;

    PaymentType paymentType;

    LecturerResponse lecturer;
}
