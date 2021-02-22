package com.zallpy.dataprocessing.repositories;

import com.zallpy.dataprocessing.entities.Tweet;
import com.zallpy.dataprocessing.entities.dto.TweetResponse;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TweetRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TweetRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Tweet tweet) {

        final var sql =

        "INSERT INTO tweets (id, date, target_id, insult_id, tweet) " +
        "VALUES (:id, :date, :target, :insult, :tweet) " +
        "ON CONFLICT DO NOTHING ";

        var params = new MapSqlParameterSource();
        params.addValue("id", tweet.getId());
        params.addValue("date", tweet.getDate());
        params.addValue("target", tweet.getTarget().getId());
        params.addValue("insult", tweet.getInsult().getId());
        params.addValue("tweet", tweet.getTweet());

        jdbcTemplate.update(sql, params);
    }

    public Optional<List<TweetResponse>> findTweet(Long id, LocalDate date, String target, String insult, String tweet) {

        var sql =

        "SELECT tw.id as id, tw.date as date, ta.name as name, ins.name as insult, tw.tweet as tweet " +
        "FROM tweets tw " +
        "INNER JOIN targets ta ON ta.id = tw.target_id " +
        "INNER JOIN insults ins ON ins.id = tw.insult_id ";

        var params = new MapSqlParameterSource();
        var filter = new ArrayList<>();

        if (id != null) {
            filter.add("tw.id = :id ");
            params.addValue("id", id);
        }

        if (date != null) {
            filter.add("tw.date = :date ");
            params.addValue("date", date);
        }

        if (target != null) {
            filter.add("ta.name = :target ");
            params.addValue("target", target);
        }

        if (insult != null) {
            filter.add("ins.name ilike :insult ");
            params.addValue("insult", "%"+insult+"%");
        }

        if (tweet != null) {
            filter.add("tw.tweet ilike :tweet ");
            params.addValue("tweet", "%" + tweet + "%");
        }

        if (!filter.isEmpty())
            sql += "WHERE ";

        var filterIt = filter.iterator();
        while (filterIt.hasNext()) {
            sql += filterIt.next();
            if (filterIt.hasNext())
                sql += " AND ";
        }

        var result = jdbcTemplate.query(sql, params, (resultSet, i) -> {

            var tweetId = resultSet.getLong("id");
            var tweetDate = resultSet.getDate("date").toLocalDate();
            var tweetTarget = resultSet.getString("name");
            var tweetInsult = resultSet.getString("insult");
            var tweetMsg = resultSet.getString("tweet");

            return new TweetResponse(tweetId, tweetDate, tweetTarget, tweetInsult, tweetMsg);
        });

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }
}
