package utils;

public class ApiResponse {
    private boolean success;
    private String message;
    private String data;

    public ApiResponse(boolean success, String message, String data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public String toJsonLikeString() {

        return "{ \"success\": " + success +
                ", \"message\": \"" + safe(message) + "\"" +
                ", \"data\": " + (data == null ? "null" : "\"" + safe(data) + "\"") +
                " }";
    }

    private String safe(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}
