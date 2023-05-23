package com.fredsonchaves;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ComponentScan
@DataJpaTest
@ExtendWith(CleanUpExtension.class)
public @interface MySQLGatewayTest {

}
