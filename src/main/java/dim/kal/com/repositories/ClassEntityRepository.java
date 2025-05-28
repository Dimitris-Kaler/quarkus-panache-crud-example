package dim.kal.com.repositories;

import dim.kal.com.models.ClassEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ClassEntityRepository implements IClassEntityRepository,PanacheRepository<ClassEntity> {
    @Override
    public List<ClassEntity> findAllClasses() {
        return listAll();
    }

    @Override
    public ClassEntity findClassById(Long id) {
        return find("id", id).firstResult();
    }


    @Override
    public List<ClassEntity> findByTeacherId(Long teacherId) {
        return list("teacher.id", teacherId);
    }

    @Override
    @Transactional
    public void save(ClassEntity classEntity) {
        persist(classEntity);
    }

    @Override
    @Transactional
    public void update(Long id, ClassEntity updatedClass) {
        ClassEntity existing = findById(id);
        if (existing != null) {
            existing.setTitle(updatedClass.getTitle());
            existing.setTeacher(updatedClass.getTeacher());
            existing.setStudents(updatedClass.getStudents());
        }

    }

    @Override
    @Transactional
    public void delete(Long id) {
        delete("id", id);
    }
}
