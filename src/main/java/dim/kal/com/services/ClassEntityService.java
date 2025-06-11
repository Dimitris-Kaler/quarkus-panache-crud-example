package dim.kal.com.services;

import dim.kal.com.dtos.ClassEntityDTO;
import dim.kal.com.mappers.ClassEntityMapper;
import dim.kal.com.models.ClassEntity;
import dim.kal.com.repositories.IClassEntityRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClassEntityService implements IClassEntityService{
    IClassEntityRepository repository;

    ClassEntityMapper mapper;

    @Inject
    public ClassEntityService(IClassEntityRepository repository,ClassEntityMapper mapper){
        this.repository=repository;
        this.mapper=mapper;
    }


    @Override
    public List<ClassEntityDTO> findAllClasses() {

        return repository.findAllClasses().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ClassEntityDTO findClassById(Long id) {

        return mapper.toDTO(repository.findClassById(id));
    }

    @Override
    public ClassEntityDTO findClassEntityByTitle(String title) {
        return mapper.toDTO(repository.findByTitle(title));
    }

    @Override
    public List<ClassEntityDTO> findByTeacherId(Long teacherId) {
        return repository.findByTeacherId(teacherId).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(ClassEntityDTO classEntityDTO) {
        repository.save(mapper.toEntity(classEntityDTO));
    }

    @Override
    @Transactional
    public void update(Long id, ClassEntityDTO updatedClass) {
    repository.update(id,mapper.toEntity(updatedClass));

    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.delete(id);


    }
}
