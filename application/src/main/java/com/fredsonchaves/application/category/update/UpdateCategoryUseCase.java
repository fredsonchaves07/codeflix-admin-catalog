package com.fredsonchaves.application.category.update;

import com.fredsonchaves.application.UseCase;
import com.fredsonchaves.domain.validation.handler.Notification;
import io.vavr.control.Either;

public interface UpdateCategoryUseCase extends UseCase<UpdateCategoryInput, Either<Notification, UpdateCategoryOutput>> {
}
