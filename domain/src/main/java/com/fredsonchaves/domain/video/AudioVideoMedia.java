package com.fredsonchaves.domain.video;

import com.fredsonchaves.domain.ValueObject;

import java.util.Objects;

public class AudioVideoMedia extends ValueObject {

    private final String checkSum;

    private final String name;

    private final String rawLocation;

    private final String encoderLocation;

    private final MediaStatus status;

    private AudioVideoMedia(
            String checkSum, String name, String rawLocation, String encoderLocation, MediaStatus status) {
        this.checkSum = checkSum;
        this.name = name;
        this.rawLocation = rawLocation;
        this.encoderLocation = encoderLocation;
        this.status = status;
    }

    public static AudioVideoMedia with(
            String checkSum, String name, String rawLocation, String encoderLocation, MediaStatus status
    ) {
        return new AudioVideoMedia(checkSum, name, rawLocation, encoderLocation, status);
    }

    public String checkSum() {
        return checkSum;
    }

    public String name() {
        return name;
    }

    public String rawLocation() {
        return rawLocation;
    }

    public String encoderLocation() {
        return encoderLocation;
    }

    public MediaStatus status() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AudioVideoMedia that = (AudioVideoMedia) o;
        return Objects.equals(checkSum, that.checkSum) && Objects.equals(rawLocation, that.rawLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkSum, rawLocation);
    }
}
