package itmo_diploma.responses;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LecturerResponse {

    Long id;

    String name;

    String surname;

    String email;

    String phone;
}
