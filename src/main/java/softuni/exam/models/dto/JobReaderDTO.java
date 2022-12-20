package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Company;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "job")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobReaderDTO {

    @Size(min = 2,max = 40)
    @Column(unique = true,nullable = false)
    @XmlElement(name = "jobTitle")
    private String title;

    @Column(unique = true,nullable = false)
    @XmlElement
    private double salary;

    @Column(nullable = false)
    @XmlElement
    private double hoursAWeek;

    @Size(min = 5)
    @Column(columnDefinition = "TEXT")
    @XmlElement
    private String description;

    @XmlElement
    private Long companyId;
}
