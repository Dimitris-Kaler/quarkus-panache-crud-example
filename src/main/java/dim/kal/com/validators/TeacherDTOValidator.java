package dim.kal.com.validators;

import dim.kal.com.dtos.TeacherDTO;
import dim.kal.com.models.ApiException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.util.regex.Pattern;

@ApplicationScoped
public class TeacherDTOValidator implements Validator<TeacherDTO>{
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);

    @Override
    public void validate(TeacherDTO dto) {
        if (dto == null) {
            throw new ApiException("Teacher cannot be null", Response.Status.BAD_REQUEST);
        }

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new ApiException("Teacher name is required", Response.Status.BAD_REQUEST);
        }

        String name = dto.getName().trim();
        if (dto.getName().trim().length() < 2 || dto.getName().trim().length() > 50) {
            throw new ApiException("The teacher's name must be between 2 and 50 characters", Response.Status.BAD_REQUEST);
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new ApiException("Teacher email is required", Response.Status.BAD_REQUEST);
        }

        if (!EMAIL_PATTERN.matcher(dto.getEmail().trim()).matches()) {
            throw new ApiException("Invalid email format", Response.Status.BAD_REQUEST);
        }
    }
}