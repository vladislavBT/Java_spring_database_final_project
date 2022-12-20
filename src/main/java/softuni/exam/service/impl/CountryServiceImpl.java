package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryReaderDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CountryServiceImpl implements CountryService {

    private final ModelMapper model;

    private final Gson gson;

    private final ValidationUtils validationUtils;

    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(ModelMapper model, Gson gson, ValidationUtils validationUtils, CountryRepository countryRepository) {
        this.model = model;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count()>0;
    }

    @Override
    public String readCountriesFileContent() throws IOException {
        Path path = Path.of("D:\\Intelij\\IdeaProjects\\JobFinder\\skeleton\\src\\main\\resources\\files\\json\\countries.json");
        return Files.readString(path);
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readCountriesFileContent(), CountryReaderDTO[].class))
                .forEach(countryReaderDTO ->{

                    boolean isValid = validationUtils.isValid(countryReaderDTO);

                    if (this.countryRepository.findFirstByName(countryReaderDTO.getName()).isPresent()) {
                        isValid = false;
                    }
                    if(isValid){
                        Country country=model.map(countryReaderDTO,Country.class);
                        this.countryRepository.save(country);

                        sb.append(String.format("Successfully imported country %s - %s%n",country.getName(),country.getCode()));
                    }else {
                        sb.append(String.format("Invalid country%n"));
                    }
                } );
        return sb.toString();
    }
}
