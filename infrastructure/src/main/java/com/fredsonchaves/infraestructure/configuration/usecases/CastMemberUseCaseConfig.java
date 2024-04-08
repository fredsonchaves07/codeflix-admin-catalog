package com.fredsonchaves.infraestructure.configuration.usecases;

import com.fredsonchaves.application.castmember.create.CreateCastMemberUseCase;
import com.fredsonchaves.application.castmember.delete.DeleteCastMemberUseCase;
import com.fredsonchaves.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import com.fredsonchaves.application.castmember.retrieve.list.ListCastMemberUseCase;
import com.fredsonchaves.application.castmember.update.UpdateCastMemberUseCase;
import com.fredsonchaves.domain.castmember.CastMemberGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CastMemberUseCaseConfig {

    private final CastMemberGateway gateway;

    public CastMemberUseCaseConfig(CastMemberGateway gateway) {
        this.gateway = gateway;
    }

    @Bean
    public CreateCastMemberUseCase createCastMemberUseCase() {
        return new CreateCastMemberUseCase(gateway);
    }

    @Bean
    public UpdateCastMemberUseCase updateCastMemberUseCase() {
        return new UpdateCastMemberUseCase(gateway);
    }

    @Bean
    public GetCastMemberByIdUseCase getCastMemberByIdUseCase() {
        return new GetCastMemberByIdUseCase(gateway);
    }

    @Bean
    public ListCastMemberUseCase castMemberListUseCase() {
        return new ListCastMemberUseCase(gateway);
    }

    @Bean
    public DeleteCastMemberUseCase deleteCastMemberUseCase() {
        return new DeleteCastMemberUseCase(gateway);
    }
}
