package com.zallpy.dataprocessing.entities.dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

public class TweetResponse {

    private final Long id;
    private final LocalDate localDate;
    private final String target;
    private final String insult;
    private final String tweet;

    public TweetResponse(Long id, LocalDate localDate, String target, String insult, String tweet) {
        this.id = id;
        this.localDate = localDate;
        this.target = target;
        this.insult = insult;
        this.tweet = tweet;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getLocalDate() {
        return localDate;
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
        TweetResponse that = (TweetResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(localDate, that.localDate) && Objects.equals(target, that.target)
                && Objects.equals(insult, that.insult) && Objects.equals(tweet, that.tweet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localDate, target, insult, tweet);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TweetResponse.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("localDate=" + localDate)
                .add("target='" + target + "'")
                .add("insult='" + insult + "'")
                .add("tweet='" + tweet + "'")
                .toString();
    }
}
