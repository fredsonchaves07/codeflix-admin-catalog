package com.fredsonchaves.infraestructure;

import com.fredsonchaves.infraestructure.category.CategoryMySQLGateway;
import com.fredsonchaves.infraestructure.category.CategoryMySQLGatewayTest;
import com.fredsonchaves.infraestructure.category.persistence.CategoryRepository;
import io.vavr.collection.List;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;
import java.util.Collection;
import java.util.Collections;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@DataJpaTest
@ComponentScan
@ExtendWith(MySQLGatewayTest.CleanUpExtensions.class)
public @interface MySQLGatewayTest {

     class CleanUpExtensions implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) {
            final var appContext = SpringExtension.getApplicationContext(context);
            cleanUp(Collections.singletonList(appContext.getBean(CategoryRepository.class)));
        }

         private void cleanUp(final Collection<CrudRepository> repositories) {
             repositories.forEach(CrudRepository::deleteAll);
         }
    }
}
