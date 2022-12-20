package softuni.exam.models.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Country;
import softuni.exam.models.enums.StatusType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class PersonReaderDTO {
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
    @Column
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusType statusType;

    @SerializedName(value = "country")
    private Long countryId;
}
