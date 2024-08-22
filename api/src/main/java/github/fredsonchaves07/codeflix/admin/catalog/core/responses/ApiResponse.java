package github.fredsonchaves07.codeflix.admin.catalog.core.responses;

import github.fredsonchaves07.codeflix.admin.catalog.core.errors.ApiError;

import java.util.List;

public record ApiResponse<T> (String message, int statusCode, List<T> data) {

    public static<T> ApiResponse<T> create(int statusCode, String message, T data) {
        if (data instanceof NotContent)
            return new ApiResponse<>(message, statusCode, List.of());
        return new ApiResponse<>(message, statusCode, List.of(data));
    }

    public static <T> ApiResponse<T> create(ApiError error) {
        return new ApiResponse<>(error.message(), error.statusCode(), List.of());
    }
}
