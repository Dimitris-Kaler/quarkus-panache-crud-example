package dim.kal.com.repositories;

import dim.kal.com.models.Teacher;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TeacherRepository implements PanacheRepository<Teacher>,ITeacherRepository {
    @Override
    public List<Teacher> findAllTeachers() {
        return listAll();
    }

    @Override
    public Teacher findById(Long id) {
        return find("id", id).firstResult();  // ή getEntityManager().find(Teacher.class, id)
    }

    @Override
    public Teacher findByName(String name) {
        return find("name", name).firstResult();
    }

    @Override
    public Teacher findByEmail(String email) {
        return find("email", email).firstResult();
    }

    @Override
    @Transactional
    public void save(Teacher teacher) {
        persist(teacher);

    }

    @Override
    @Transactional
    public void update(Long id, Teacher updatedTeacher) {
        Teacher existing = findById(id);
        if (existing != null) {
            existing.setName(updatedTeacher.getName());
            existing.setEmail(updatedTeacher.getEmail());
//            existing.name = updatedTeacher.name;
//            existing.email = updatedTeacher.email;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        delete("id", id);
    }
}
