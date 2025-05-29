package dim.kal.com.services;

import dim.kal.com.models.ClassEntity;

import java.util.List;

public interface IClassEntityService {
    List<ClassEntity> findAllClasses();
    ClassEntity findClassById(Long id);
    ClassEntity findClassEntityByTitle(String title);
    List<ClassEntity> findByTeacherId(Long teacherId);
    void save(ClassEntity classEntity);
    void update(Long id, ClassEntity updatedClass);
    void delete(Long id);
}
