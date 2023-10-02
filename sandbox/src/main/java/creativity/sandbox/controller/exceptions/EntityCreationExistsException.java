package creativity.sandbox.controller.exceptions;

public class EntityCreationExistsException extends RuntimeException{

    public EntityCreationExistsException(String msg){
        super(msg);
    }
}
