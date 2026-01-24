package utils;

import exception.*;

public class ExceptionMapper {
    public static ApiResponse toResponse(Exception ex) {
        if (ex instanceof DuplicateResourceException) {
            return new ApiResponse(false, ex.getMessage(), null);
        }
        if (ex instanceof InvalidInputException) {
            return new ApiResponse(false, ex.getMessage(), null);
        }
        if (ex instanceof ResourceNotFoundException) {
            return new ApiResponse(false, ex.getMessage(), null);
        }
        if (ex instanceof DatabaseOperationException) {
            return new ApiResponse(false, "Database error: " + ex.getMessage(), null);
        }
        return new ApiResponse(false, "Unexpected error: " + ex.getMessage(), null);
    }
}
