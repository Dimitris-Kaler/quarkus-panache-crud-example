package dim.kal.com.mappers;

import dim.kal.com.models.ApiException;
import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<String> errorMessages = exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

       return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(errorMessages)).build();
    }


    public static class ErrorResponse {
        public List<String> errors;

        public ErrorResponse(List<String> errors) {
            this.errors = errors;
        }
    }
}

