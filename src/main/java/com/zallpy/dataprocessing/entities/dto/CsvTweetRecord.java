package com.zallpy.dataprocessing.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.StringJoiner;

public class CsvTweetRecord {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

    private final Long id;
    private final LocalDate date;
    private final String target;
    private final String insult;
    private final String tweet;

    public CsvTweetRecord(
            @JsonProperty("i") Long id,
            @JsonProperty("date") String date,
            @JsonProperty("target") String target,
            @JsonProperty("insult") String insult,
            @JsonProperty("tweet") String tweet) {

        this.id = id;
        this.date = LocalDate.parse(date, dateTimeFormatter);;
        this.target = target;
        this.insult = insult;
        this.tweet = tweet;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTarget() {
        return target;
    }

    public String getInsult() {
        return insult;
    }

    public String getTweet() {
        return tweet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CsvTweetRecord csvTweetRecord1 = (CsvTweetRecord) o;
        return Objects.equals(id, csvTweetRecord1.id) && Objects.equals(date, csvTweetRecord1.date) && Objects.equals(target, csvTweetRecord1.target)
                && Objects.equals(insult, csvTweetRecord1.insult) && Objects.equals(tweet, csvTweetRecord1.tweet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, target, insult, tweet);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CsvTweetRecord.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("date='" + date + "'")
                .add("target='" + target + "'")
                .add("insult='" + insult + "'")
                .add("tweet='" + tweet + "'")
                .toString();
    }

}
