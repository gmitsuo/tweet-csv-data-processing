package com.zallpy.dataprocessing.entities;

import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

public class Tweet {

    private final Long id;
    private final LocalDate date;
    private final String tweet;

    private final Insult insult;
    private final Target target;

    public Tweet(Long id, LocalDate date, Insult insult, String tweet, Target target) {
        this.id = id;
        this.date = date;
        this.insult = insult;
        this.tweet = tweet;
        this.target = target;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTweet() {
        return tweet;
    }

    public Insult getInsult() {
        return insult;
    }

    public Target getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Tweet tweet1 = (Tweet) o;
        return Objects.equals(id, tweet1.id) && Objects.equals(date, tweet1.date) && Objects.equals(insult, tweet1.insult)
                && Objects.equals(tweet, tweet1.tweet) && Objects.equals(target, tweet1.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, insult, tweet, target);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tweet.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("date=" + date)
                .add("insult='" + insult + "'")
                .add("tweet='" + tweet + "'")
                .add("target=" + target)
                .toString();
    }
}
