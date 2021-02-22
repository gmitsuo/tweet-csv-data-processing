package com.zallpy.dataprocessing.repositories;

import com.zallpy.dataprocessing.entities.Insult;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.assertj.core.api.Assertions.assertThat;

@Profile("test")
@SpringBootTest(classes = {
        DataSourceAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class,
        FlywayAutoConfiguration.class,
        InsultRepository.class
})
class InsultRepositoryTest {

    @Autowired
    InsultRepository insultRepository;

    @Autowired
    Flyway flyway;

    @BeforeEach
    void setup() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void saveInsult() {

        var name = "insult";

        insultRepository.save(name);

        assertThat(insultRepository.findByName(name))
        .isNotEmpty()
        .contains(new Insult(1L, name));
    }
}