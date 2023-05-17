package com.fredsonchaves.infraestructure.category;

import com.fredsonchaves.infraestructure.MySQLGatewayTest;
import com.fredsonchaves.infraestructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@MySQLGatewayTest
public class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway gateway;

    @Autowired
    private CategoryRepository repository;

    @Test
    public void testInjectedDependencies() {
        assertNotNull(gateway);
        assertNotNull(repository);
    }
}
