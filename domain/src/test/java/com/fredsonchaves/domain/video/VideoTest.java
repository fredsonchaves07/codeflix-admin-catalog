package com.fredsonchaves.domain.video;

import com.fredsonchaves.domain.castmember.CastMemberID;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.genre.GenreID;
import com.fredsonchaves.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(expectedTitle, actualVideo.title());
        assertEquals(expectedDescription, actualVideo.description());
        assertEquals(expectedLaunchedAt, actualVideo.launchedAt());
        assertEquals(expectedDuration, actualVideo.duration());
        assertEquals(expectedPublished, actualVideo.published());
        assertEquals(expectedOpened, actualVideo.opended());
        assertEquals(expectedGenres, actualVideo.genres());
        assertEquals(expectedCategories, actualVideo.categories());
        assertEquals(expectedMembers, actualVideo.castMemberIDS());
        assertTrue(actualVideo.video().isEmpty());
        assertTrue(actualVideo.trailer().isEmpty());
        assertTrue(actualVideo.banner().isEmpty());
        assertTrue(actualVideo.thumbnail().isEmpty());
        assertTrue(actualVideo.thumbnailHalf().isEmpty());
        assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }
}
