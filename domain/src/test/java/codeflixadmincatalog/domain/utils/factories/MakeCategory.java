package codeflixadmincatalog.domain.utils.factories;

import codeflixadmincatalog.domain.category.entity.Category;
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
}
