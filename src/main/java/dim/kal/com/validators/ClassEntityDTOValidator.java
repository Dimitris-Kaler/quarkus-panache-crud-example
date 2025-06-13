package dim.kal.com.validators;

import dim.kal.com.dtos.ClassEntityDTO;
import dim.kal.com.models.ApiException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ClassEntityDTOValidator implements Validator<ClassEntityDTO> {
    @Override
    public void validate(ClassEntityDTO dto) {
        if (dto == null) {
            throw new ApiException("ClassEntityDTO must not be null", Response.Status.BAD_REQUEST);
        }

        // Title validation
        String title = dto.getTitle();
        if (title == null || title.trim().isEmpty()) {
            throw new ApiException("Class title must not be null or empty", Response.Status.BAD_REQUEST);
        }
        if (title.length() < 2 || title.length() > 50) {
            throw new ApiException("Class title must be between 2 and 50 characters", Response.Status.BAD_REQUEST);
        }



    }
}
