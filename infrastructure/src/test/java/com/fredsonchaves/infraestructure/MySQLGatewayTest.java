package com.fredsonchaves.infraestructure;

import com.fredsonchaves.infraestructure.category.CategoryMySQLGatewayTest;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;
import java.util.Collection;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@DataJpaTest
@ComponentScan
@ExtendWith(MySQLGatewayTest.CleanUpExtensions.class)
public @interface MySQLGatewayTest {

     class CleanUpExtensions implements BeforeAllCallback {

        @Override
        public void beforeAll(ExtensionContext context) throws Exception {
            final Collection<CrudRepository> repositories = SpringExtension
                    .getApplicationContext(context)
                    .getBeansOfType(CrudRepository.class)
                    .values();
            cleanUp(repositories);
        }

        private void cleanUp(Collection<CrudRepository> repositories) {
            repositories.forEach(CrudRepository::deleteAll);
        }
    }
}
