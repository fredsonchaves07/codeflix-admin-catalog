package codeflixadmincatalog.core.entities;

import codeflixadmincatalog.core.errors.DomainError;

public interface Validator {

    void validate() throws DomainError;
}
