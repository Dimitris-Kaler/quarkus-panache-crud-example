package dim.kal.com.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ClassEntityDTO {

    private Long id;

//    @NotNull(message = "Class title must not be null")
//    @NotEmpty(message = "Class title  must not ber empty")
//    @Size(min = 2, max = 50,message = "Class title must be between 2 and 50 characters")
    private String title;

    private TeacherDTO teacher;

    private List<StudentDTO> students;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }
}
