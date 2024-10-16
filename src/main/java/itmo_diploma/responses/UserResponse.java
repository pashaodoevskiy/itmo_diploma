package itmo_diploma.responses;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    Long id;

    String username;

    String name;

    String surname;

    String email;

    String phone;
}
