package dim.kal.com.validators;

public interface Validator<T> {
    void validate(T dto);
}
