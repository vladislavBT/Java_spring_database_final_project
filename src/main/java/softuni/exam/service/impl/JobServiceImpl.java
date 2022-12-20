package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CompanyReaderDTO;
import softuni.exam.models.dto.JobReaderDTO;
import softuni.exam.models.dto.wrappers.CompanyReaderWrapperDTO;
import softuni.exam.models.dto.wrappers.JobsReaderWrapperDTO;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Job;
import softuni.exam.repository.JobRepository;
import softuni.exam.service.JobService;
import softuni.exam.util.ValidationUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final ModelMapper model;

    private final Gson gson;

    private final ValidationUtils validationUtils;

    private final JobRepository jobRepository;

    public JobServiceImpl(ModelMapper model, Gson gson, ValidationUtils validationUtils, JobRepository jobRepository) {
        this.model = model;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.jobRepository = jobRepository;
    }

    @Override
    public boolean areImported() {
        return jobRepository.count()>0;
    }

    @Override
    public String readJobsFileContent() throws IOException {
        Path path = Path.of("D:\\Intelij\\IdeaProjects\\JobFinder\\skeleton\\src\\main\\resources\\files\\xml\\jobs.xml");
        return Files.readString(path);
    }

    @Override
    public String importJobs() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jobContext = JAXBContext.newInstance(JobsReaderWrapperDTO.class);

        final FileReader fileReader = new FileReader("D:\\Intelij\\IdeaProjects\\JobFinder\\skeleton\\src\\main\\resources\\files\\xml\\jobs.xml");

        final Unmarshaller unmarshaller = jobContext.createUnmarshaller();

        final JobsReaderWrapperDTO jobsReaderWrapperDTO = (JobsReaderWrapperDTO) unmarshaller.unmarshal(fileReader);

        final List<JobReaderDTO> jobReaderDTO = jobsReaderWrapperDTO.getJobs();

        for (JobReaderDTO jobDTO : jobReaderDTO) {
            boolean isValid = validationUtils.isValid(jobDTO);

            if (this.jobRepository.findByTitle(jobDTO.getTitle()).isPresent()) {
                isValid = false;
            }
            if(isValid){

                Job job =model.map(jobDTO,Job.class);
                this.jobRepository.save(job);

                sb.append(String.format("Successfully imported job %s%n",job.getTitle()));
            }else {
                sb.append(String.format("Invalid job%n"));
            }
        }

        fileReader.close();
        return sb.toString();
        }


    @Override
    public String getBestJobs() {
        List<Job> jobs = this.jobRepository.findBySalaryIsGreaterThanEqualAndHoursAWeekIsLessThanEqualOrderBySalaryDesc(5000.00,30.00);
        StringBuilder sb = new StringBuilder();
        for (Job job : jobs) {
            sb.append(String.format("Job title: %s%n",  job.getTitle()));
            sb.append(String.format("-Salary: %.2f$%n", job.getSalary()));
            sb.append(String.format("--Hours a week: %.2fh%n", job.getHoursAWeek()));
        }

        return sb.toString();
    }
}
