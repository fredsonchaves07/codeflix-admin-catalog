package github.fredsonchaves07.codeflix.admin.catalog.core.errors;

import codeflixadmincatalog.core.errors.Error;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.Arrays;


@ApplicationScoped
public class InternalServerError extends ApiError {

    private static final Logger logger = Logger.getLogger(InternalServerError.class);

    private final static int statusCode = RestResponse.StatusCode.INTERNAL_SERVER_ERROR;

    private final static String message =  "Internal Server Error. Consult your system administrator";

    private InternalServerError() {
        super(statusCode, message);
    }

    public static InternalServerError trows(Error error) {
        InternalServerError internalServerError = new InternalServerError();
        internalServerError.logger(error);
        return internalServerError;
    }

    private void logger(Error error) {
        logger.error(message);
        logger.error(Arrays.toString(error.getStackTrace()));
    }
}
