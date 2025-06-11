package dim.kal.com.mappers;

import dim.kal.com.dtos.ClassEntityDTO;
import dim.kal.com.models.ClassEntity;
import dim.kal.com.models.Student;
import dim.kal.com.models.Teacher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClassEntityMapper implements IMapper<ClassEntity, ClassEntityDTO> {
    TeacherMapper teacherMapper;

    StudentMapper studentMapper;

    @Inject
    public ClassEntityMapper(TeacherMapper teacherMapper,StudentMapper studentMapper){
        this.teacherMapper=teacherMapper;
        this.studentMapper=studentMapper;
    }


    @Override
    public ClassEntityDTO toDTO(ClassEntity entity) {
        if(entity == null) return null;
        ClassEntityDTO dto = new ClassEntityDTO();
        dto.setId(entity.id);
        dto.setTitle(entity.getTitle());
        if (entity.getTeacher()!= null) {
            dto.setTeacher(teacherMapper.toDTO(entity.getTeacher()));
        }
        if (entity.getStudents() != null) {
           dto.setStudents(entity.getStudents().stream().map(student -> studentMapper.toDTO(student)).collect(Collectors.toList()));
        }
        return dto;
    }

    @Override
    public ClassEntity toEntity(ClassEntityDTO dto) {
        if(dto == null) return null;
        ClassEntity entity= new ClassEntity();
//        entity.id = dto.getId();
        entity.setTitle(dto.getTitle());

        if (dto.getTeacher() != null) {
        entity.setTeacher(teacherMapper.toEntity(dto.getTeacher()));
        }

        if (dto.getStudents() != null) {
        entity.setStudents(dto.getStudents().stream().map(studentMapper::toEntity).collect(Collectors.toList()));
        }

        return entity;
    }

}
