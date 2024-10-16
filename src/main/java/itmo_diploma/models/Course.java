package itmo_diploma.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course extends BaseEntity {

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false)
    Integer price;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(nullable = false)
    LocalDate startDate;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(nullable = false)
    LocalDate endDate;

    @ManyToOne
    @JsonBackReference
    Lecturer lecturer;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<UserCourse> userCourses;
}
