package com.fredsonchaves.infraestructure.category;

import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.pagination.SearchQuery;
import com.fredsonchaves.infraestructure.category.persistence.CategoryJpaEntity;
import com.fredsonchaves.infraestructure.category.persistence.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryMySQLGateway(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(final Category category) {
        return save(category);
    }

    @Override
    public void deleteById(final CategoryID categoryID) {
        delete(categoryID);
    }

    void delete(final CategoryID categoryID) {
        if(findCategory(categoryID).isPresent()){
            repository.delete(CategoryJpaEntity.from(findCategory(categoryID).orElseThrow()));
        }
    }

    @Override
    public Optional<Category> findById(final CategoryID categoryID) {
        return findCategory(categoryID);
    }

    private Optional<Category> findCategory(final CategoryID categoryID) {
        Optional<CategoryJpaEntity> categoryJpa = repository.findById(categoryID.getValue());
        if (categoryJpa.isPresent())
            return Optional.of(repository.findById(categoryID.getValue()).get().toAggregate());
        return Optional.empty();
    }

    @Override
    public Category update(final Category category) {
        return save(category);
    }

    private Category save(final Category category) {
        return repository.save(CategoryJpaEntity.from(category)).toAggregate();
    }

    @Override
    public Pagination<Category> findAll(final SearchQuery query) {
        final PageRequest page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );
        final Specification<CategoryJpaEntity> specification = Optional.ofNullable(query.terms())
                .filter(str -> !str.isBlank())
                .map(str ->
                        ((Specification<CategoryJpaEntity>) (root, criteriaQuery, criteriaBuilder) ->
                                criteriaBuilder.like(
                                        criteriaBuilder.upper(root.get("name")), "%" + str.toUpperCase() + "%"))
                                .or((Specification<CategoryJpaEntity>) (root, criteriaQuery, criteriaBuilder) ->
                                        criteriaBuilder.like(
                                                criteriaBuilder.upper(root.get("description")), "%" + str.toUpperCase() + "%")
                                        ))
                .orElse(null);
        final Page<CategoryJpaEntity> pageResult = repository.findAll(Specification.where(specification), page);
        return new Pagination<>(
               pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(
                        CategoryJpaEntity::toAggregate
                ).stream().toList());
    }

    @Override
    public List<CategoryID> existsByIds(final Iterable<CategoryID> categoryIDS) {
        List<String> ids = StreamSupport.stream(categoryIDS.spliterator(), false)
                .map(CategoryID::getValue)
                .toList();
        return repository.existsByIds(ids).stream().map(CategoryID::from).toList();
    }
}
