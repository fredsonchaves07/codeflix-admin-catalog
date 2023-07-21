package com.fredsonchaves.e2e.category;

import com.fredsonchaves.config.annotations.E2ETest;
import org.junit.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.wildfly.common.Assert.assertTrue;

@E2ETest
@Testcontainers
public class CategoryE2ETest {

    @Container
    private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer("mysql:latest")
            .withPassword("123456")
            .withUsername("root")
            .withDatabaseName("admin_videos");

    @DynamicPropertySource
    public static void setDatasourcePreperties(final DynamicPropertyRegistry registry) {
        Integer mappedPort = MY_SQL_CONTAINER.getMappedPort(3306);
        System.out.printf("Container is runninf on port %s\n", mappedPort);
        registry.add("mysql.port", () -> mappedPort);
    }

    @Test
    public void testWorks() {
        assertTrue(MY_SQL_CONTAINER.isRunning());
    }
}
