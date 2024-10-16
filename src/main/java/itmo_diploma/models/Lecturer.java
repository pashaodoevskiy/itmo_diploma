package itmo_diploma.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "lecturers")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lecturer extends BaseEntity {

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String surname;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false, unique = true)
    String phone;

    @OneToMany(mappedBy = "lecturer")
    @JsonManagedReference
    List<Course> courses;
}
