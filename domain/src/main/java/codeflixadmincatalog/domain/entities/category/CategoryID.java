package codeflixadmincatalog.domain.entities.category;

import codeflixadmincatalog.core.entities.Identifier;

public final class CategoryID extends Identifier {

    private CategoryID() {
        super();
    }


    private CategoryID(String value) {
        super(value);
    }

    public static CategoryID newId() {
        return new CategoryID();
    }

    public static CategoryID newId(String value) {
        return new CategoryID(value);
    }
}
