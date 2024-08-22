package github.fredsonchaves07.codeflix.admin.catalog.core.errors;

public abstract class ApiError extends RuntimeException {

    protected String message;

    protected int statusCode;

    public ApiError(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public String message() {
        return this.message;
    }

    public int statusCode() {
        return statusCode;
    }
}
