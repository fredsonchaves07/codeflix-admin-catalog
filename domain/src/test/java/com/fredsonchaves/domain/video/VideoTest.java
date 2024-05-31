package com.fredsonchaves.domain.video;

import com.fredsonchaves.domain.castmember.CastMemberID;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.genre.GenreID;
import com.fredsonchaves.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VideoTest {

    @Test
    public void givenAValidParams_whenCallsNewVideo_shouldInstantiate() {
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "Descrição detalhada do vídeo";
        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedPublished = false;
        final var expectedOpened = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());
        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedPublished,
                expectedOpened,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );
        assertNotNull(actualVideo);
        assertNotNull(actualVideo.getId());
        assertEquals(expectedTitle, actualVideo.getTitle());
        assertEquals(expectedDescription, actualVideo.getDescription());
        assertEquals(expectedLaunchedAt, actualVideo.getLaunchedAt());
        assertEquals(expectedDuration, actualVideo.getDuration());
        assertEquals(expectedPublished, actualVideo.getPublished());
        assertEquals(expectedOpened, actualVideo.getOpened());
        assertEquals(expectedGenres, expectedCategories, actualVideo.getGenres());
        assertEquals(expectedMembers, actualVideo.getMembers());
        assertTrue(actualVideo.getVideo().isEmpty());
        assertTrue(actualVideo.getTrailer().isEmpty());
        assertTrue(actualVideo.getBanner().isEmpty());
        assertTrue(actualVideo.getThumbnail().isEmpty());
        assertTrue(actualVideo.getThumbnailHalf().isEmpty());
        assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }
}
