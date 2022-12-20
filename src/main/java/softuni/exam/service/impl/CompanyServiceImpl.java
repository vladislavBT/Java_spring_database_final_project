package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CompanyReaderDTO;
import softuni.exam.models.dto.wrappers.CompanyReaderWrapperDTO;
import softuni.exam.models.entity.Company;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.service.CompanyService;
import softuni.exam.util.ValidationUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final ValidationUtils validationUtils;


    private final ModelMapper model;

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(ValidationUtils validationUtils, ModelMapper model, CompanyRepository companyRepository) {
        this.validationUtils = validationUtils;
        this.model = model;
        this.companyRepository = companyRepository;
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count()>0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        Path path = Path.of("D:\\Intelij\\IdeaProjects\\JobFinder\\skeleton\\src\\main\\resources\\files\\xml\\companies.xml");
        return Files.readString(path);
    }

    @Override
    public String importCompanies() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext companyContext = JAXBContext.newInstance(CompanyReaderWrapperDTO.class);

        final FileReader fileReader = new FileReader("D:\\Intelij\\IdeaProjects\\JobFinder\\skeleton\\src\\main\\resources\\files\\xml\\companies.xml");

        final Unmarshaller unmarshaller = companyContext.createUnmarshaller();

        final CompanyReaderWrapperDTO companyReaderWrapperDTO = (CompanyReaderWrapperDTO) unmarshaller.unmarshal(fileReader);

        final List<CompanyReaderDTO> companyReaderDTO = companyReaderWrapperDTO.getCompanies();

        for (CompanyReaderDTO companyDTO : companyReaderDTO) {
            boolean isValid = validationUtils.isValid(companyDTO);

            if (this.companyRepository.findByName(companyDTO.getName()).isPresent()) {
                isValid = false;
            }
            if(isValid){

                String date = companyDTO.getDateEstablished();
                LocalDate dateEstablished = LocalDate.parse(date);


                Company company =model.map(companyDTO,Company.class);
                company.setDateEstablished(dateEstablished);
                this.companyRepository.save(company);

                sb.append(String.format("Successfully imported company %s - %d%n",company.getName(),company.getCountry().getId()));
            }else {
                sb.append(String.format("Invalid company%n"));
            }
        }

        fileReader.close();
        return sb.toString();

    }
}
