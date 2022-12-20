package softuni.exam.models.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CountryReaderDTO {
    @Size(min = 2,max = 30)
    @Column(unique = true,nullable = false)
    private String name;

    @Size(min = 2,max = 19)
    @Column(unique = true,nullable = false)
    @SerializedName(value = "countryCode")
    private String code;

    @Size(min = 2,max = 19)
    @Column(nullable = false)
    private String currency;
}
