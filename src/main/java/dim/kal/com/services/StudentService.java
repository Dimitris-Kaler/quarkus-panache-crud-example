package dim.kal.com.services;

import dim.kal.com.models.Student;
import dim.kal.com.repositories.IStudentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class StudentService implements IStudentService {

    IStudentRepository repository;

    @Inject
    StudentService(IStudentRepository repository){
        this.repository=repository;
    }

    @Override
    public List<Student> getAllStudents() {
        return repository.findAllStudents();
    }

    @Override
    public Student getStudentById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Student getStudentByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Student getStudentByName(String name) {
        return repository.findByName(name);
    }

    @Override
    @Transactional
    public void createStudent(Student student) {
        repository.save(student);

    }

    @Override
    @Transactional
    public void updateStudent(Long id, Student student) {
        repository.update(id, student);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        repository.delete(id);
    }
}
