package github.fredsonchaves07.codeflix.admin.catalog.core.errors;

import codeflixadmincatalog.core.errors.Error;
import org.jboss.resteasy.reactive.RestResponse;


public class BadRequestError extends ApiError {

    private final static int statusCode = RestResponse.StatusCode.BAD_REQUEST;

    private BadRequestError(String message) {
        super(statusCode, message);
    }

    public static BadRequestError trows(Error error) {
        return new BadRequestError(error.getMessage());
    }
}
