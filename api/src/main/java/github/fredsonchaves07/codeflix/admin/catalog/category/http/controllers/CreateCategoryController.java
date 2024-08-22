package github.fredsonchaves07.codeflix.admin.catalog.category.http.controllers;

import codeflixadmincatalog.core.either.Either;
import codeflixadmincatalog.core.errors.Error;
import codeflixadmincatalog.core.valueobject.EmptyValueObject;
import codeflixadmincatalog.domain.errors.category.CategoryError;
import codeflixadmincatalog.domain.usecases.category.CreateCategoryInput;
import codeflixadmincatalog.domain.usecases.category.CreateCategoryUseCase;
import github.fredsonchaves07.codeflix.admin.catalog.category.http.models.CreateCategoryResponse;
import github.fredsonchaves07.codeflix.admin.catalog.core.controller.ApiController;
import github.fredsonchaves07.codeflix.admin.catalog.core.errors.BadRequestError;
import github.fredsonchaves07.codeflix.admin.catalog.core.errors.InternalServerError;
import github.fredsonchaves07.codeflix.admin.catalog.core.responses.ApiResponse;
import github.fredsonchaves07.codeflix.admin.catalog.core.responses.NotContent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;

import java.net.URI;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CreateCategoryController implements ApiController {

    private static final String MESSAGE_RESPONSE = "Category created";

    @Inject
    CreateCategoryUseCase useCase;

    @POST
    @Transactional
    public Response execute(CreateCategoryResponse categoryResponse) {
        Either<CategoryError, EmptyValueObject> output = useCase.execute(CreateCategoryInput.with(
                categoryResponse.name(), categoryResponse.description(), categoryResponse.isActive())
        );
        if (output.getError().isPresent())
            return error(output.getError().get());
        return success();
    }

    @Override
    public Response error(Error error) {
        if (error.isInternalError()) {
            return Response
                    .created(URI.create("/categories"))
                    .status(RestResponse.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.create(InternalServerError.trows(error)))
                    .build();
        }
        return Response
                .created(URI.create("/categories"))
                .status(RestResponse.Status.BAD_REQUEST)
                .entity(ApiResponse.create(BadRequestError.trows(error)))
                .build();
    }

    @Override
    public Response success() {
        return Response
                .created(URI.create("/categories"))
                .entity(ApiResponse
                        .create(RestResponse.StatusCode.CREATED, MESSAGE_RESPONSE, NotContent.create()))
                .build();
    }
}
