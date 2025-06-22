package dim.kal.com.mappers;

import dim.kal.com.dtos.StudentDTO;
import dim.kal.com.models.Student;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StudentMapper implements IMapper<Student, StudentDTO>{


    @Override
    public StudentDTO toDTO(Student entity) {
        if(entity == null) return null;

        StudentDTO dto =new StudentDTO();
        dto.setId(entity.id);
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());

        return dto;
    }

    @Override
    public Student toEntity(StudentDTO dto) {
        if(dto == null) return null;

        Student entity = new Student();

//        entity.id = dto.getId();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());

        return entity;
    }
}
