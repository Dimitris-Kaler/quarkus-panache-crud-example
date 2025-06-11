package dim.kal.com.mappers;

import dim.kal.com.dtos.TeacherDTO;
import dim.kal.com.models.Teacher;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TeacherMapper implements IMapper<Teacher, TeacherDTO>{
    @Override
    public TeacherDTO toDTO(Teacher entity) {
        if(entity == null) return null;

        TeacherDTO dto = new TeacherDTO();
        dto.setId(entity.id);
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());

        return dto;
    }

    @Override
    public Teacher toEntity(TeacherDTO dto) {
        if(dto == null) return null;
        Teacher entity = new Teacher();
//        entity.id = dto.getId();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());

        return entity;

    }
}
