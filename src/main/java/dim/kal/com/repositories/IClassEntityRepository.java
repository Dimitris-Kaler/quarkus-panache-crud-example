package dim.kal.com.repositories;

import dim.kal.com.models.ClassEntity;

import java.util.List;

public interface IClassEntityRepository {
    List<ClassEntity> findAllClasses();
    ClassEntity findClassById(Long id);
    List<ClassEntity> findByTeacherId(Long teacherId);
    void save(ClassEntity classEntity);
    void update(Long id, ClassEntity updatedClass);
    void delete(Long id);
}
