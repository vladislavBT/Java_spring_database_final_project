package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "countries")
public class Country {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2,max = 30)
    @Column(unique = true,nullable = false)
    private String name;

    @Size(min = 2,max = 19)
    @Column(unique = true,nullable = false)
    private String code;

    @Size(min = 2,max = 19)
    @Column(nullable = false)
    private String currency;
}
