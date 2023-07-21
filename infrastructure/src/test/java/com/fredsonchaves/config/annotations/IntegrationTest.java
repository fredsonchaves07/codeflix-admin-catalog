package com.fredsonchaves.config.annotations;

import com.fredsonchaves.config.MysqlCleanUpExtension;
import com.fredsonchaves.infraestructure.configuration.WebServerConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SpringBootTest(classes = WebServerConfig.class)
@ExtendWith(MysqlCleanUpExtension.class)
public @interface IntegrationTest {

}
