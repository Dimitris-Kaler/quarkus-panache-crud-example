package dim.kal.com.services;

import dim.kal.com.dtos.StudentDTO;
import dim.kal.com.mappers.StudentMapper;
import dim.kal.com.models.Student;
import dim.kal.com.repositories.IStudentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class StudentService implements IStudentService {

    IStudentRepository repository;

    StudentMapper mapper;

    @Inject
    StudentService(IStudentRepository repository,StudentMapper mapper){

        this.repository=repository;
        this.mapper = mapper;
    }

    @Override
    public List<StudentDTO> getAllStudents() {

        return repository.findAllStudents().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        return mapper.toDTO(repository.findById(id));
    }

    @Override
    public StudentDTO getStudentByEmail(String email) {
        return mapper.toDTO(repository.findByEmail(email));
    }

    @Override
    public StudentDTO getStudentByName(String name) {
        return mapper.toDTO(repository.findByName(name));
    }

    @Override
    @Transactional
    public void createStudent(StudentDTO studentDTO) {
        Student entity = mapper.toEntity(studentDTO);
        System.out.println("Entity ID before persist: " + entity.id);  // θα τυπώσει το id

        repository.save(entity);

//        repository.save(mapper.toEntity(studentDTO));

    }

    @Override
    @Transactional
    public void updateStudent(Long id, StudentDTO studentDTO) {
        repository.update(id, mapper.toEntity(studentDTO));
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        repository.delete(id);
    }
}
