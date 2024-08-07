package codeflixadmincatalog.core.errors;

public class DomainError extends Error {

    private DomainError(String message) {
        super(message);
    }

    public static DomainError trows(String message) {
        return new DomainError(message);
    }
}
