package auth_service.exception;

public class UserNotFoundException extends  NotFoundException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
