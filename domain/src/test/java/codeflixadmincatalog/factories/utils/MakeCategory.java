package codeflixadmincatalog.factories.utils;

import codeflixadmincatalog.domain.entities.category.Category;
import com.github.javafaker.Faker;

import java.util.Locale;

public class MakeCategory {

    private static final Faker faker = new Faker(Locale.of("pt-BR"));

    public static Category makeCategory() {
        return Category.create(
                faker.lorem().characters(7),
                faker.lorem().characters(50),
                faker.bool().bool()
        );
    }

    public static Category makeCategoryWithEmptyDescription() {
        return Category.create(
                faker.lorem().characters(50),
                "",
                faker.bool().bool()
        );
    }

    public static Category makeCategoryWithInactive() {
        return Category.create(
                faker.lorem().characters(7),
                faker.lorem().characters(50),
                false
        );
    }
}
