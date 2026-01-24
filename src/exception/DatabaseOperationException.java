package exception;

import javax.management.RuntimeOperationsException;

public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException(String message, Throwable cause){
        super(message, cause);
    }
}
