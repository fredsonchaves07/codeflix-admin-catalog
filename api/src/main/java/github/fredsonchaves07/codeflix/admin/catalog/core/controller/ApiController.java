package github.fredsonchaves07.codeflix.admin.catalog.core.controller;

import codeflixadmincatalog.core.errors.Error;
import jakarta.ws.rs.core.Response;

public interface ApiController {

    Response error(Error error);

    Response success();
}
