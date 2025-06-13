package dim.kal.com.models;

import jakarta.ws.rs.core.Response;

public class ApiException extends RuntimeException{

    private final int statusCode;

    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    public ApiException(String message, Response.Status status) {
        super(message);
        this.statusCode = status.getStatusCode();
    }

    public int getStatusCode() {
        return statusCode;
    }
}
