package com.zallpy.dataprocessing.repositories;

import com.zallpy.dataprocessing.entities.Target;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Repository
public class TargetRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TargetRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Target save(String target) {

        var keyHolder = new GeneratedKeyHolder();
        final var sql = "INSERT INTO targets (name) VALUES (:name)";

        var params = new MapSqlParameterSource();
        params.addValue("name", target);

        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        return new Target(requireNonNull(keyHolder.getKey()).longValue(), target);
    }

    public Optional<Target> findByName(String name) {

        final var sql = "SELECT id, name FROM targets WHERE name = :name";

        var params = new MapSqlParameterSource();
        params.addValue("name", name);

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, (resultSet, i) -> {
                var id = resultSet.getLong("id");
                var tName = resultSet.getString("name");
                return new Target(id, tName);
            }));
        }
        catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
