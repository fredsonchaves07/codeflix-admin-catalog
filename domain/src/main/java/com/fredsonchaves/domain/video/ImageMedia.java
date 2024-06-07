package com.fredsonchaves.domain.video;

import com.fredsonchaves.domain.ValueObject;

public class ImageMedia extends ValueObject {

    private final String checkSum;

    private final String name;

    private final String location;

    private ImageMedia(String checkSum, String name, String location) {
        this.checkSum = checkSum;
        this.name = name;
        this.location = location;
    }

    public static ImageMedia with(String checkSum, String name, String location) {
        return new ImageMedia(checkSum, name, location);
    }

    public String checkSum() {
        return checkSum;
    }

    public String name() {
        return name;
    }

    public String location() {
        return location;
    }
}
