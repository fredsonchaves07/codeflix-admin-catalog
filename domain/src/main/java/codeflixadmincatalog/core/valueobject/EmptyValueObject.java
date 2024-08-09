package codeflixadmincatalog.core.valueobject;

public class EmptyValueObject implements ValueObject {

    private final static String VALUE = "";

    private EmptyValueObject() {};

    public static EmptyValueObject create() {
        return new EmptyValueObject();
    }

    @Override
    public String toString() {
        return VALUE;
    }
}