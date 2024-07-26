package springboot.springboot_testing.exceptions;

public class UserAlreadyFoundException extends RuntimeException{
    public UserAlreadyFoundException(String message) {
        super(message);
    }
}
