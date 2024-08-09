package codeflixadmincatalog.core.errors;

public abstract class Error extends RuntimeException {

    protected TypeError typeError;

    protected Error(String message) {
        super(message);
        typeError = TypeError.INTERNAL_ERROR;
    }

    public boolean isInternalError() {
        return typeError.equals(TypeError.INTERNAL_ERROR);
    }

    public boolean isDomainError() {
        return typeError.equals(TypeError.DOMAIN_ERROR);
    }
}
