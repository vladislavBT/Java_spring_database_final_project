package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.enums.StatusType;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "people")
public class Person {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2,max = 30)
    @Column(nullable = false)
    private String firstName;

    @Size(min = 2,max = 30)
    @Column(nullable = false)
    private String lastName;

    @Email
    @Column(nullable = false)
    private String email;

    @Size(min = 2,max = 13)
    @Column()
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusType statusType;

    @ManyToOne
    private Country country;
}
