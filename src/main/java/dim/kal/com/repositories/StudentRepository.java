package dim.kal.com.repositories;

import dim.kal.com.models.Student;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class StudentRepository implements IStudentRepository,PanacheRepository<Student> {

    @Override
    public List<Student> findAllStudents() {
        return listAll();
    }

    @Override
    public Student findById(Long id) {
        return find("id", id).firstResult();
        //return Student.findById(id)
    }

    @Override
    public Student findByEmail(String email) {
        return find("email", email).firstResult();
    }

    @Override
    public Student findByName(String name) {
        return find("name", name).firstResult();
    }

    @Override
    @Transactional
    public void save(Student student) {
        persist(student);

    }

    @Override
    @Transactional
    public void update(Long id, Student updatedStudent) {
        Student existing = findById(id);
        if(existing !=null){
            existing.setName(updatedStudent.getName());
            existing.setEmail(updatedStudent.getEmail());
//            existing.name = updatedStudent.name;
//            existing.email = updatedStudent.email;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        delete("id", id);
    }
}
