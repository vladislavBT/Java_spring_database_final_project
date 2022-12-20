package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryReaderDTO;
import softuni.exam.models.dto.PersonReaderDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Person;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.PersonService;
import softuni.exam.util.ValidationUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class PersonServiceImpl implements PersonService {
    private final ModelMapper model;

    private final Gson gson;

    private final ValidationUtils validationUtils;

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(ModelMapper model, Gson gson, ValidationUtils validationUtils, PersonRepository personRepository) {
        this.model = model;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.personRepository = personRepository;
    }

    @Override
    public boolean areImported() {
        return this.personRepository.count()>0;
    }

    @Override
    public String readPeopleFromFile() throws IOException {
        Path path = Path.of("D:\\Intelij\\IdeaProjects\\JobFinder\\skeleton\\src\\main\\resources\\files\\json\\people.json");
        return Files.readString(path);
    }

    @Override
    public String importPeople() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readPeopleFromFile(), PersonReaderDTO[].class))
                .forEach(personReaderDTO ->{

                    boolean isValid = validationUtils.isValid(personReaderDTO);

                    if (this.personRepository.findFirstByFirstNameAndLastName(personReaderDTO.getFirstName(), personReaderDTO.getLastName()).isPresent()) {
                        isValid = false;
                    }
                    if(isValid){
                        Person person=model.map(personReaderDTO,Person.class);
                        this.personRepository.save(person);

                        sb.append(String.format("Successfully imported person %s %s%n",person.getFirstName(),person.getLastName()));
                    }else {
                        sb.append(String.format("Invalid person%n"));
                    }
                } );
        return sb.toString();
    }
}
