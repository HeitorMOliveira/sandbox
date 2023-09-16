package creativity.sandbox.controller.exceptions;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends StandardError {
    private String fields;
    private String fieldMessage;
}

