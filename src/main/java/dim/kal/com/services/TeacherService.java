package dim.kal.com.services;

import dim.kal.com.dtos.TeacherDTO;
import dim.kal.com.mappers.TeacherMapper;
import dim.kal.com.models.Teacher;
import dim.kal.com.repositories.ITeacherRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TeacherService implements ITeacherService{

    ITeacherRepository repository;

    TeacherMapper mapper;

    @Inject
    public TeacherService(ITeacherRepository repository,TeacherMapper mapper){

        this.repository=repository;
        this.mapper = mapper;
    }


    @Override
    public TeacherDTO findById(Long id) {
        return mapper.toDTO(repository.findById(id));
    }

    @Override
    public List<TeacherDTO> findAll() {

        return repository.findAllTeachers().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public TeacherDTO findByName(String name) {

        return mapper.toDTO(repository.findByName(name));
    }

    @Override
    @Transactional
    public void save(TeacherDTO teacherDTO) {
        repository.save(mapper.toEntity(teacherDTO));
    }

    @Override
    @Transactional
    public void update(Long id, TeacherDTO updatedTeacherDTO) {
        repository.update(id, mapper.toEntity(updatedTeacherDTO));
//        Teacher existing = repository.findById(id);
//        if(existing !=null){
//            existing.setName(updatedTeacherDTO.getName());
//            existing.setEmail(updatedTeacherDTO.getEmail());
//        }


    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.delete(id);

    }
}
