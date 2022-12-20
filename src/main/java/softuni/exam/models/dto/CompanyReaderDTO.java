package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Country;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "company")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyReaderDTO {

    @Size(min = 2,max = 40)
    @Column(nullable = false,unique = true)
    @XmlElement(name = "companyName")
    private String name;

    @Column(nullable = false)
    @XmlElement
    private String dateEstablished;

    @Size(min = 2,max = 30)
    @XmlElement
    @Column(nullable = false)
    private String website;

    @Column(nullable = false)
    @XmlElement
    private Long countryId;
}
