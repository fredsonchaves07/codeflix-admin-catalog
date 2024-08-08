package codeflixadmincatalog.core.errors;

public abstract class Error extends RuntimeException {

    protected Error(String message) {
        super(message);
    }
}
