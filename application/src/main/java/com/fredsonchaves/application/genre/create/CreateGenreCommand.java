package com.fredsonchaves.application.genre.create;

import java.util.List;

public record CreateGenreCommand(String name, boolean isActive, List<String> categoriesId) {

    public static CreateGenreCommand with(String name, boolean isActive, List<String> categoriesId) {
        return new CreateGenreCommand(name, isActive, categoriesId);
    }
}
