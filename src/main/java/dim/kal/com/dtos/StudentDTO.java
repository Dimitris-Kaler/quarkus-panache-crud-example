package dim.kal.com.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class StudentDTO {
    public Long id;
//    @NotNull(message = "Student's name must not be null")
//    @NotEmpty(message = "Student's name must not be empty")
//    @Size(min = 2, max = 50,message = "The student's name must be between 2 and 50 characters")
    public String name;

//    @NotNull(message = "email must no be null")
//    @NotEmpty(message = "email must not be empty")
//    @Email(message = "must be a well-formed email address")
    public String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
