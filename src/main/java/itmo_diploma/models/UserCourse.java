package itmo_diploma.models;

import itmo_diploma.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "users_courses")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCourse extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;

    @Enumerated(EnumType.STRING)
    @Column
    PaymentType paymentType;

    @Column
    Boolean isPaid = false;

    LocalDateTime paymentDate;
}
