package com.fredsonchaves.infraestructure.genre;

import com.fredsonchaves.config.annotations.MySQLGatewayTest;
import com.fredsonchaves.infraestructure.category.CategoryMySQLGateway;
import com.fredsonchaves.infraestructure.genre.persistence.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
public class GenreMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryMySQLGateway;

    @Autowired
    private GenreMySQLGateway genreMySQLGateway;

    @Autowired
    private GenreRepository genreRepository;


}
