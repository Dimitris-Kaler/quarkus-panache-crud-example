package dim.kal.com.services;

import dim.kal.com.dtos.TeacherDTO;
import dim.kal.com.mappers.TeacherMapper;
import dim.kal.com.models.ApiException;
import dim.kal.com.models.Teacher;
import dim.kal.com.repositories.ITeacherRepository;
import dim.kal.com.validators.TeacherDTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TeacherService implements ITeacherService{

    ITeacherRepository repository;

    TeacherMapper mapper;

    TeacherDTOValidator validator;

    @Inject
    public TeacherService(ITeacherRepository repository,TeacherMapper mapper, TeacherDTOValidator validator){
        this.validator = validator;
        this.repository=repository;
        this.mapper = mapper;
    }


    @Override
    public TeacherDTO findById(Long id) {
        Teacher teacher = repository.findById(id);
        if (teacher == null) {
            throw new ApiException("Teacher with ID " + id + " not found", Response.Status.NOT_FOUND);
        }
        return mapper.toDTO(teacher);
    }

    @Override
    public List<TeacherDTO> findAll() {

        return repository.findAllTeachers().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public TeacherDTO findByName(String name) {
        Teacher teacher = repository.findByName(name);
        if (teacher == null) {
            throw new ApiException("Teacher with name '" + name + "' not found", Response.Status.NOT_FOUND);
        }
        return mapper.toDTO(teacher);
    }

    @Override
    @Transactional
    public void save(TeacherDTO teacherDTO) {
        validator.validate(teacherDTO);
        repository.save(mapper.toEntity(teacherDTO));
    }

    @Override
    @Transactional
    public void update(Long id, TeacherDTO updatedTeacherDTO) {
        validator.validate(updatedTeacherDTO);
        Teacher existing = repository.findById(id);
        if (existing == null) {
            throw new ApiException("Cannot update — teacher with ID " + id + " not found", Response.Status.NOT_FOUND);
        }
        repository.update(id, mapper.toEntity(updatedTeacherDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Teacher existing = repository.findById(id);
        if (existing == null) {
            throw new ApiException("Cannot delete — teacher with ID " + id + " not found", Response.Status.NOT_FOUND);
        }
        repository.delete(id);
    }
}
