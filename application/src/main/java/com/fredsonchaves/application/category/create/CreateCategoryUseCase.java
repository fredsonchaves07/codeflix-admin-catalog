package com.fredsonchaves.application.category.create;

import com.fredsonchaves.application.UseCase;
import com.fredsonchaves.domain.validation.handler.Notification;
import io.vavr.control.Either;

public interface CreateCategoryUseCase extends UseCase<CreateCategoryInput, Either<Notification, CreateCategoryOutput>>{
}
