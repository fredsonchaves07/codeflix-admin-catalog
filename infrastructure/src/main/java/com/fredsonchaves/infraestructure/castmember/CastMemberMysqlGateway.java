package com.fredsonchaves.infraestructure.castmember;

import com.fredsonchaves.domain.castmember.CastMember;
import com.fredsonchaves.domain.castmember.CastMemberGateway;
import com.fredsonchaves.domain.castmember.CastMemberID;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.pagination.SearchQuery;
import com.fredsonchaves.infraestructure.castmember.persistence.CastMemberJpaEntity;
import com.fredsonchaves.infraestructure.castmember.persistence.CastMemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CastMemberMysqlGateway implements CastMemberGateway {

    private final CastMemberRepository repository;

    public CastMemberMysqlGateway(CastMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public CastMember create(CastMember castMember) {
        return save(castMember);
    }

    @Override
    public void deleteById(CastMemberID id) {
        if (findCastMember(id).isPresent()) {
            repository.delete(CastMemberJpaEntity.from(findCastMember(id).orElseThrow()));
        }
    }

    @Override
    public Optional<CastMember> findById(CastMemberID id) {
        return findCastMember(id);
    }

    @Override
    public CastMember update(CastMember castMember) {
        return save(castMember);
    }

    @Override
    public Pagination<CastMember> findAll(SearchQuery query) {
        final PageRequest page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );
        final Specification<CastMemberJpaEntity> specification = Optional.ofNullable(query.terms())
                .filter(str -> !str.isBlank())
                .map(str ->
                        ((Specification<CastMemberJpaEntity>) (root, criteriaQuery, criteriaBuilder) ->
                                criteriaBuilder.like(
                                        criteriaBuilder.upper(root.get("name")), "%" + str.toUpperCase() + "%"))
                                .or((Specification<CastMemberJpaEntity>) (root, criteriaQuery, criteriaBuilder) ->
                                        criteriaBuilder.like(
                                                criteriaBuilder.upper(root.get("description")), "%" + str.toUpperCase() + "%")
                                ))
                .orElse(null);
        final Page<CastMemberJpaEntity> pageResult = repository.findAll(Specification.where(specification), page);
        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(
                        CastMemberJpaEntity::toAggregate
                ).stream().toList());
    }

    private CastMember save(final CastMember castMember) {
        return repository.save(CastMemberJpaEntity.from(castMember)).toAggregate();
    }

    private Optional<CastMember> findCastMember(final CastMemberID id) {
        Optional<CastMemberJpaEntity> castMemberJpa = repository.findById(id.getValue());
        if (castMemberJpa.isPresent())
            return Optional.of(repository.findById(id.getValue()).get().toAggregate());
        return Optional.empty();
    }
}
