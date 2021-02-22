package com.zallpy.dataprocessing.configuration;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class FlywayConfiguration {

    /**
     * Configuration run when "local" spring profile is active.
     * It will drop current schema and startup a migration from scratch
     */
    @Bean
    @Profile("local")
    public FlywayMigrationStrategy cleanAndMigrateFromScratch() {

        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }
}
