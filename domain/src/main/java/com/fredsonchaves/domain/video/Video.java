package com.fredsonchaves.domain.video;

import com.fredsonchaves.domain.AggregateRoot;
import com.fredsonchaves.domain.castmember.CastMemberID;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.genre.GenreID;
import com.fredsonchaves.domain.validation.ValidationHandler;

import java.time.Instant;
import java.time.Year;
import java.util.Set;

public class Video extends AggregateRoot<VideoID> {

    private String title;

    private String description;

    private Year launchedAt;

    private double duration;

    private boolean opended;

    private boolean published;

    private Rating rating;

    private ImageMedia banner;

    private ImageMedia thumbnail;

    private ImageMedia thumbnailHalf;

    private AudioVideoMedia trailer;

    private AudioVideoMedia video;

    private Set<CategoryID> categories;

    private Set<GenreID> genres;

    private Set<CastMemberID> castMemberIDS;

    private Instant updatedAt;

    private Instant createdAt;

    public Video(VideoID videoID) {
        super(videoID);
    }

    @Override
    public void validate(ValidationHandler handler) {

    }
}
