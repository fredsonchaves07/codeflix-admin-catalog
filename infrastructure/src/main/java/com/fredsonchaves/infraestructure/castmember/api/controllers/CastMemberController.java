package com.fredsonchaves.infraestructure.castmember.api.controllers;

import com.fredsonchaves.application.castmember.create.CreateCastMemberCommand;
import com.fredsonchaves.application.castmember.create.CreateCastMemberOutput;
import com.fredsonchaves.application.castmember.create.CreateCastMemberUseCase;
import com.fredsonchaves.application.castmember.delete.DeleteCastMemberUseCase;
import com.fredsonchaves.application.castmember.retrieve.get.CastMemberOutput;
import com.fredsonchaves.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import com.fredsonchaves.application.castmember.retrieve.list.ListCastMemberUseCase;
import com.fredsonchaves.application.castmember.update.UpdateCastMemberCommand;
import com.fredsonchaves.application.castmember.update.UpdateCastMemberUseCase;
import com.fredsonchaves.domain.castmember.CastMemberID;
import com.fredsonchaves.domain.castmember.CastMemberType;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.pagination.SearchQuery;
import com.fredsonchaves.infraestructure.castmember.api.CastMemberAPI;
import com.fredsonchaves.infraestructure.castmember.models.CastMemberListResponse;
import com.fredsonchaves.infraestructure.castmember.models.CastMemberResponse;
import com.fredsonchaves.infraestructure.castmember.models.CreateCastMemberResponse;
import com.fredsonchaves.infraestructure.castmember.models.UpdateCastMemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class CastMemberController implements CastMemberAPI {

    private final CreateCastMemberUseCase createCastMemberUseCase;

    private final GetCastMemberByIdUseCase getCastMemberByIdUseCase;

    private final UpdateCastMemberUseCase updateCastMemberUseCase;

    private final DeleteCastMemberUseCase deleteCastMemberUseCase;

    private final ListCastMemberUseCase listCastMemberUseCase;

    public CastMemberController(
            final CreateCastMemberUseCase createCastMemberUseCase,
            final GetCastMemberByIdUseCase getCastMemberByIdUseCase,
            final UpdateCastMemberUseCase updateCastMemberUseCase,
            final DeleteCastMemberUseCase deleteCastMemberUseCase,
            final ListCastMemberUseCase listCastMemberUseCase
    ) {
        this.createCastMemberUseCase = createCastMemberUseCase;
        this.getCastMemberByIdUseCase = getCastMemberByIdUseCase;
        this.updateCastMemberUseCase = updateCastMemberUseCase;
        this.deleteCastMemberUseCase = deleteCastMemberUseCase;
        this.listCastMemberUseCase = listCastMemberUseCase;
    }

    @Override
    @ResponseBody()
    public ResponseEntity<?> createCastMember(CreateCastMemberResponse input) {
        final CreateCastMemberCommand castMemberCommand = CreateCastMemberCommand.with(
                input.name(),
                CastMemberType.valueOf(input.type())
        );
        CreateCastMemberOutput output = createCastMemberUseCase.execute(castMemberCommand);
        return ResponseEntity.created(URI.create("/castmembers/" + output.id())).body(output);
    }

    @Override
    public Pagination<CastMemberListResponse> listCastMembers(String search, int page, int perPage, String sort, String direction) {
        return listCastMemberUseCase.execute(new SearchQuery(page, perPage, search, sort, direction)).map(CastMemberListResponse::from);

    }

    @Override
    public CastMemberResponse getById(String id) {
        CastMemberOutput castMemberOutput = getCastMemberByIdUseCase.execute(CastMemberID.from(id));
        return CastMemberResponse.from(castMemberOutput);
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCastMemberResponse input) {
        UpdateCastMemberCommand updateCastMemberCommand = UpdateCastMemberCommand.with(
                id, input.name(), CastMemberType.valueOf(input.type())
        );
        return ResponseEntity.ok(updateCastMemberUseCase.execute(updateCastMemberCommand));
    }

    @Override
    public void deleteById(String id) {
        deleteCastMemberUseCase.execute(CastMemberID.from(id));
    }
}
