package dim.kal.com.services;

import dim.kal.com.dtos.ClassEntityDTO;
import dim.kal.com.mappers.ClassEntityMapper;
import dim.kal.com.models.ApiException;
import dim.kal.com.models.ClassEntity;
import dim.kal.com.repositories.IClassEntityRepository;
import dim.kal.com.validators.ClassEntityDTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClassEntityService implements IClassEntityService{
    IClassEntityRepository repository;

    ClassEntityMapper mapper;

    ClassEntityDTOValidator validator;

    @Inject
    public ClassEntityService(IClassEntityRepository repository,ClassEntityMapper mapper, ClassEntityDTOValidator validator){
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }


    @Override
    public List<ClassEntityDTO> findAllClasses() {

        return repository.findAllClasses().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ClassEntityDTO findClassById(Long id) {
        ClassEntity classEntity = repository.findClassById(id);
        if(classEntity == null){
            throw new ApiException("Class with ID " + id + " not found", Response.Status.NOT_FOUND);
        }
        return mapper.toDTO(classEntity);
    }

    @Override
    public ClassEntityDTO findClassEntityByTitle(String title) {
        ClassEntity classEntity = repository.findByTitle(title);
        if(classEntity == null){
            throw new ApiException("Class with name '"+title+"' not found",Response.Status.NOT_FOUND);
        }
        return mapper.toDTO(classEntity);
    }

    @Override
    public List<ClassEntityDTO> findByTeacherId(Long teacherId) {
        return repository.findByTeacherId(teacherId).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(ClassEntityDTO classEntityDTO) {
        validator.validate(classEntityDTO);
        ClassEntity entity = mapper.toEntity(classEntityDTO);
        System.out.println("Entity ID before persist: " + entity.id);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void update(Long id, ClassEntityDTO classEntityDTO) {
        validator.validate(classEntityDTO);
        ClassEntity entity = repository.findClassById(id);
        if(entity == null){
            throw new ApiException("Class with id '"+id+"' not found",Response.Status.NOT_FOUND);
        }
        repository.update(id,mapper.toEntity(classEntityDTO));

    }

    @Override
    @Transactional
    public void delete(Long id) {
        ClassEntity entity = repository.findClassById(id);
        if(entity == null){
            throw new ApiException("Class with id '"+id+"' not found",Response.Status.NOT_FOUND);
        }
        repository.delete(id);


    }
}
