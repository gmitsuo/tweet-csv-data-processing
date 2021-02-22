package com.zallpy.dataprocessing.repositories;

import com.zallpy.dataprocessing.entities.Insult;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Repository
public class InsultRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public InsultRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Insult save(String insult) {

        var keyHolder = new GeneratedKeyHolder();
        final var sql = "INSERT INTO insults (name) VALUES (:name)";

        var params = new MapSqlParameterSource();
        params.addValue("name", insult);

        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        return new Insult(requireNonNull(keyHolder.getKey()).longValue(), insult);
    }

    public Optional<Insult> findByName(String name) {

        final var sql = "SELECT id, name FROM insults WHERE name = :name";

        var params = new MapSqlParameterSource();
        params.addValue("name", name);

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, (resultSet, i) -> {
                var id = resultSet.getLong("id");
                var iName = resultSet.getString("name");
                return new Insult(id, iName);
            }));
        }
        catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
