package dim.kal.com.services;

import dim.kal.com.models.ClassEntity;
import dim.kal.com.repositories.IClassEntityRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ClassEntityService implements IClassEntityService{
    IClassEntityRepository repository;

    @Inject
    public ClassEntityService(IClassEntityRepository repository){
        this.repository=repository;
    }


    @Override
    public List<ClassEntity> findAllClasses() {
        return repository.findAllClasses();
    }

    @Override
    public ClassEntity findClassById(Long id) {
        return repository.findClassById(id);
    }

    @Override
    public ClassEntity findClassEntityByTitle(String title) {
        return repository.findByTitle(title);
    }

    @Override
    public List<ClassEntity> findByTeacherId(Long teacherId) {
        return repository.findByTeacherId(teacherId);
    }

    @Override
    @Transactional
    public void save(ClassEntity classEntity) {
        repository.save(classEntity);
    }

    @Override
    @Transactional
    public void update(Long id, ClassEntity updatedClass) {
    repository.update(id,updatedClass);

    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.delete(id);


    }
}
