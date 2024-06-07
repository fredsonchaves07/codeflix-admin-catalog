package com.fredsonchaves.domain.video;

import com.fredsonchaves.domain.AggregateRoot;
import com.fredsonchaves.domain.castmember.CastMemberID;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.genre.GenreID;
import com.fredsonchaves.domain.validation.ValidationHandler;

import java.time.Instant;
import java.time.Year;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
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

    private Video(
            final VideoID videoID,
            final String title,
            final String description,
            final Year aLaunchYear,
            final double duration,
            final boolean opened,
            final boolean published,
            final Rating rating,
            final Instant createdAt,
            final Instant updatedAt,
            final ImageMedia banner,
            final ImageMedia thumb,
            final ImageMedia thumbHalf,
            final AudioVideoMedia trailer,
            final AudioVideoMedia video,
            final Set<CategoryID> categories,
            final Set<GenreID> genres,
            final Set<CastMemberID> castMemberIDS
    ) {
        super(videoID);
        this.title = title;
        this.description = description;
        this.launchedAt = aLaunchYear;
        this.duration = duration;
        this.opended = opened;
        this.published = published;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.banner = banner;
        this.thumbnail = thumb;
        this.thumbnailHalf = thumbHalf;
        this.trailer = trailer;
        this.video = video;
        this.categories = categories;
        this.genres = genres;
        this.castMemberIDS = castMemberIDS;
    }

    private Video(
            VideoID id,
            String title,
            String description,
            Year aLaunchYear,
            double duration,
            boolean opened,
            boolean published,
            Rating rating,
            Instant createdAt,
            Instant updatedAt,
            Set<CategoryID> categories,
            Set<GenreID> genres,
            Set<CastMemberID> castMemberIDS) {
        super(id);
        this.title = title;
        this.description = description;
        this.launchedAt = aLaunchYear;
        this.duration = duration;
        this.opended = opened;
        this.published = published;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.banner = null;
        this.thumbnail = null;
        this.thumbnailHalf = null;
        this.trailer = null;
        this.video = null;
        this.categories = categories;
        this.genres = genres;
        this.castMemberIDS = castMemberIDS;
    }


    @Override
    public void validate(ValidationHandler handler) {

    }

    public static Video newVideo(
            final String title,
            final String description,
            final Year aLaunchYear,
            final double duration,
            final boolean opened,
            final boolean published,
            final Rating rating,

            final Set<CategoryID> categories,
            final Set<GenreID> genres,
            final Set<CastMemberID> castMemberIDS
    ) {
        final VideoID id = VideoID.unique();
        final Instant now = Instant.now();
        return new Video(
                id,
                title,
                description,
                aLaunchYear,
                duration,
                opened,
                published,
                rating,
                now,
                now,
                categories,
                genres,
                castMemberIDS
        );
    }

    public static Video with(
            Video video
    ) {
        return new Video(
                video.getId(),
                video.title(),
                video.description(),
                video.launchedAt(),
                video.duration(),
                video.opended(),
                video.published(),
                video.rating(),
                video.createdAt(),
                video.updatedAt(),
                video.banner().orElseGet(null),
                video.thumbnail().orElseGet(null),
                video.thumbnailHalf().orElseGet(null),
                video.trailer().orElseGet(null),
                video.video().orElseGet(null),
                new HashSet<>(video.categories()),
                new HashSet<>(video.genres()),
                new HashSet<>(video.castMemberIDS())
        );
    }

    @Override
    public VideoID getId() {
        return super.getId();
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public Year launchedAt() {
        return launchedAt;
    }

    public double duration() {
        return duration;
    }

    public boolean opended() {
        return opended;
    }

    public boolean published() {
        return published;
    }

    public Rating rating() {
        return rating;
    }

    public Optional<ImageMedia> banner() {
        return Optional.ofNullable(banner);
    }

    public Optional<ImageMedia> thumbnail() {
        return Optional.ofNullable(thumbnail);
    }

    public Optional<ImageMedia> thumbnailHalf() {
        return Optional.ofNullable(thumbnailHalf);
    }

    public Optional<AudioVideoMedia> trailer() {
        return Optional.ofNullable(trailer);
    }

    public Optional<AudioVideoMedia> video() {
        return Optional.ofNullable(video);
    }

    public Set<CategoryID> categories() {
        return categories != null ? Collections.unmodifiableSet(categories) : Collections.emptySet();
    }

    public Set<GenreID> genres() {
        return genres != null ? Collections.unmodifiableSet(genres) : Collections.emptySet();
    }

    public Set<CastMemberID> castMemberIDS() {
        return castMemberIDS != null ? Collections.unmodifiableSet(castMemberIDS) : Collections.emptySet();
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Video setCategories(Set<CategoryID> categories) {
        this.categories = categories != null ? new HashSet<>(categories) : Collections.emptySet();
        return this;
    }

    public Video setGenres(Set<GenreID> genres) {
        this.genres = genres != null ? new HashSet<>(genres) : Collections.emptySet();
        ;
        return this;
    }

    public Video setCastMemberIDS(Set<CastMemberID> castMemberIDS) {
        this.castMemberIDS = castMemberIDS != null ? new HashSet<>(castMemberIDS) : Collections.emptySet();
        ;
        return this;
    }
}
