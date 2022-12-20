package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "jobs")
public class Job {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2,max = 40)
    @Column(unique = true,nullable = false)
    private String title;

    @Column(unique = true,nullable = false)
    private double salary;

    @Column(nullable = false)
    private double hoursAWeek;

    @Size(min = 5)
    @Column(columnDefinition = "TEXT")
    private String description;

    public Job(Long id, String title, double salary, double hoursAWeek, String description, Company company) {
        this.id = id;
        this.title = title;
        this.salary = salary;
        this.hoursAWeek = hoursAWeek;
        this.description = description;
        this.company = company;
    }

    @ManyToOne
    private Company company;
}
