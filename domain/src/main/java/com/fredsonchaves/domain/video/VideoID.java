package com.fredsonchaves.domain.video;

import com.fredsonchaves.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class VideoID extends Identifier {

    protected final String value;

    private VideoID(String value) {
        Objects.requireNonNull(value, "'id' should not be null");
        this.value = value;
    }

    public static VideoID unique() {
        return VideoID.from(UUID.randomUUID());
    }

    public static VideoID from(final String id) {
        return new VideoID(id);
    }

    public static VideoID from(final UUID id) {
        return new VideoID(id.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        VideoID that = (VideoID) object;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return getValue();
    }
}
