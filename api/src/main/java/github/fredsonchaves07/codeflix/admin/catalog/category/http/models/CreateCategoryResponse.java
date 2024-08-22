package github.fredsonchaves07.codeflix.admin.catalog.category.http.models;

public record CreateCategoryResponse(String name, String description, boolean isActive) {

    public static CreateCategoryResponse with(String name, String description,  boolean isActive) {
        return new CreateCategoryResponse(name, description, isActive);
    }
}
