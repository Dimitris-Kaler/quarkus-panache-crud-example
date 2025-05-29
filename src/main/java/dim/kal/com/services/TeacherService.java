package dim.kal.com.services;

import dim.kal.com.models.Teacher;
import dim.kal.com.repositories.ITeacherRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TeacherService implements ITeacherService{

    ITeacherRepository repository;

    @Inject
    public TeacherService(ITeacherRepository repository){
        this.repository=repository;
    }


    @Override
    public Teacher findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Teacher> findAll() {
        return repository.findAllTeachers();
    }

    @Override
    public Teacher findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    @Transactional
    public void save(Teacher teacher) {
        repository.save(teacher);
    }

    @Override
    @Transactional
    public void update(Long id, Teacher updatedTeacher) {
        Teacher existing = repository.findById(id);
        if(existing !=null){
            existing.setName(updatedTeacher.getName());
            existing.setEmail(updatedTeacher.getEmail());
        }


    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.delete(id);

    }
}
