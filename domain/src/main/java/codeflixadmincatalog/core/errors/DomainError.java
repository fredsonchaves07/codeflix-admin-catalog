package codeflixadmincatalog.core.errors;

public abstract class DomainError extends Error {

    protected DomainError(String message) {
        super(message);
        typeError = TypeError.DOMAIN_ERROR;
    }

    protected DomainError(Exception exception) {
        super(exception.getMessage());
    }
}
