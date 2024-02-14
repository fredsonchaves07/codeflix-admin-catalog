package com.fredsonchaves.infraestructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredsonchaves.application.genre.create.CreateGenreCommand;
import com.fredsonchaves.application.genre.create.CreateGenreOutput;
import com.fredsonchaves.application.genre.create.CreateGenreUseCase;
import com.fredsonchaves.application.genre.delete.DeleteGenreUseCase;
import com.fredsonchaves.application.genre.retrieve.get.GenreOutput;
import com.fredsonchaves.application.genre.retrieve.get.GetGenreByIdUseCase;
import com.fredsonchaves.application.genre.retrieve.list.GenreListUseCase;
import com.fredsonchaves.application.genre.update.UpdateGenreUseCase;
import com.fredsonchaves.config.annotations.ControllerTest;
import com.fredsonchaves.domain.genre.Genre;
import com.fredsonchaves.domain.genre.GenreID;
import com.fredsonchaves.infraestructure.genre.api.GenreAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = GenreAPI.class)
public class GenreApiTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateGenreUseCase createGenreUseCase;

    @MockBean
    private GetGenreByIdUseCase getGenreByIdUseCase;

    @MockBean
    private UpdateGenreUseCase updateGenreUseCase;

    @MockBean
    private DeleteGenreUseCase deleteGenreUseCase;

    @MockBean
    private GenreListUseCase genreListUseCase;

    @Test
    public void givenAValidCommandWhenCallsCreateGenreShouldReturnGenreId() throws Exception {
        final var expectedName = "Ação";
        final var expectedCategories = List.of("123", "456");
        final var expectedIsActive = true;
        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, expectedCategories);
        when(createGenreUseCase.execute(any())).thenReturn(CreateGenreOutput.from("123"));
        MockHttpServletRequestBuilder aRequest = post("/genres").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(aCommand));
        ResultActions aResponse = mvc.perform(aRequest).andDo(print());
        aResponse.andExpect(status().isCreated()).andExpect(header().string("Location", "/genres/123"));
    }

    @Test
    public void givenAValidId_whenCallsGetGenre_shouldReturnGenre() throws Exception {
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        Genre genre = Genre.newGenre(expectedName, expectedIsActive);
        GenreID expectedId = genre.getId();
        when(getGenreByIdUseCase.execute(expectedId)).thenReturn(GenreOutput.from(genre));
        MockHttpServletRequestBuilder request = get("/genres/{id}", expectedId.getValue());
        mvc.perform(request).andDo(print()).andExpectAll(
                status().isOk(),
                jsonPath("$.id", equalTo(expectedId.getValue())),
                jsonPath("$.name", equalTo(expectedName)),
                jsonPath("$.categories_id", equalTo(List.of())),
                jsonPath("$.is_active", equalTo(expectedIsActive))
        );
    }
}
