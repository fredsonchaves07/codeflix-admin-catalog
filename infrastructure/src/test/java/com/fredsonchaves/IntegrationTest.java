package com.fredsonchaves;

import com.fredsonchaves.infraestructure.configuration.WebServerConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SpringBootTest(classes = WebServerConfig.class)
@ExtendWith(CleanUpExtension.class)
public @interface IntegrationTest {

}
