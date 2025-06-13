package dim.kal.com.mappers;

import dim.kal.com.models.ApiException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {
    @Override
    public Response toResponse(ApiException exception) {
        System.out.println("Message: "+exception.getMessage());
//        exception.printStackTrace();
        Map<String, String> errorResponse = Map.of("error", exception.getMessage());

        return Response.status(exception.getStatusCode())
                .entity(errorResponse)
                .build();
    }
}