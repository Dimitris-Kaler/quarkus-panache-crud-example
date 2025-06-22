package dim.kal.com.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
@Entity
@Table(name = "classes")
public class ClassEntity extends PanacheEntity {

    private String title;

    @ManyToOne
    @JoinColumn(
            name = "teacher_id",
            foreignKey = @ForeignKey(
                    name = "fk_classes_teacher",
                    foreignKeyDefinition = "FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE"
            )
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Teacher teacher;

    @ManyToMany
    @JoinTable(
            name = "classes_students",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
