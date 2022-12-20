package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "companies")
public class Company {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2,max = 40)
    @Column(nullable = false,unique = true)
    private String name;

    @Size(min = 2,max = 30)
    @Column(nullable = false)
    private String website;

    @Column(nullable = false)
    private LocalDate dateEstablished;

    @ManyToOne
    private Country country;

    @OneToMany
    private List<Job> jobs;
}
