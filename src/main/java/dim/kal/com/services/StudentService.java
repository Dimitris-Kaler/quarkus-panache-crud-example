package dim.kal.com.services;

import dim.kal.com.dtos.StudentDTO;
import dim.kal.com.mappers.StudentMapper;
import dim.kal.com.models.ApiException;
import dim.kal.com.models.Student;
import dim.kal.com.repositories.IStudentRepository;
import dim.kal.com.validators.StudentDTOValidator;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class StudentService implements IStudentService {

    @Inject
    @ConfigProperty(name = "quarkus.profile")
    String activeProfile;

    @PostConstruct
    public void init() {
        System.out.println("Active Quarkus profile: " + activeProfile);
    }

    IStudentRepository repository;

    StudentMapper mapper;

    StudentDTOValidator validator;

    @Inject
    StudentService(IStudentRepository repository,StudentMapper mapper,StudentDTOValidator validator){
        this.repository=repository;
        this.mapper = mapper;
        this.validator=validator;
    }

    @Override
    public List<StudentDTO> getAllStudents() {

        return repository.findAllStudents().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student student = repository.findById(id);
        if (student == null) {
            throw new ApiException("Student with ID " + id + " not found", Response.Status.NOT_FOUND);
        }
        return mapper.toDTO(student);
    }

    @Override
    public StudentDTO getStudentByEmail(String email) {
        Student entity = repository.findByEmail(email);
        if (entity == null) {
            throw new ApiException("Student with email '" + email + "' not found", Response.Status.NOT_FOUND);
        }

        return mapper.toDTO(entity);
    }


    @Override
    public StudentDTO getStudentByName(String name) {
        Student student = repository.findByName(name);
        if (student == null) {
            throw new ApiException("Student with name '" + name + "' not found", Response.Status.NOT_FOUND);
        }
        return mapper.toDTO(student);
    }

    @Override
    @Transactional
    public void createStudent(StudentDTO studentDTO) {
        validator.validate(studentDTO);
        Student entity = mapper.toEntity(studentDTO);
        System.out.println("Entity ID before persist: " + entity.id);

        repository.save(entity);

    }

    @Override
    @Transactional
    public void updateStudent(Long id, StudentDTO studentDTO) {
        validator.validate(studentDTO);
        Student existing = repository.findById(id);
        if (existing == null) {
            throw new ApiException("Student with ID " + id + " not found", Response.Status.NOT_FOUND);
        }
        repository.update(id, mapper.toEntity(studentDTO));


    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student existing = repository.findById(id);
        if (existing == null) {
            throw new ApiException("Cannot delete — student with ID " + id + " not found", Response.Status.NOT_FOUND);
        }
        repository.delete(id);
    }
}
