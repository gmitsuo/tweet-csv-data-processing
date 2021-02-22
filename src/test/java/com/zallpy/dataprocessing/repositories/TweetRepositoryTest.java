package com.zallpy.dataprocessing.repositories;

import com.zallpy.dataprocessing.entities.dto.TweetResponse;
import com.zallpy.dataprocessing.services.TweetService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static java.time.Duration.ofMillis;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.awaitility.Awaitility.setDefaultPollInterval;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Profile("test")
@SpringBootTest
@TestInstance(PER_CLASS)
class TweetRepositoryTest {

    @Autowired
    TweetService tweetService;

    @Autowired
    TweetRepository tweetRepository;

    @Value("classpath:data/tweets.csv")
    Resource resourceFile;

    @BeforeAll
    void setup() throws IOException {
        tweetService.loadTweetsCsv(resourceFile.getInputStream());

        setDefaultPollInterval(ofMillis(50));
        //Await loading csv
        await().until(() -> {
            var lastTweetId = 9L;
            return tweetRepository
            .findTweet(lastTweetId, null, null, null, null)
            .isPresent();
        });
    }

    @Test
    void findTweetById() {

        var tweet = tweetRepository.findTweet(1L, null, null, null, null);
        var tweets = Collections.singletonList(new TweetResponse(
            1L,
            LocalDate.of(2014, 10, 9),
            "thomas-frieden",
            "fool",
            "Can you believe this fool, Dr. Thomas Frieden of CDC, just stated, \"anyone with fever should be asked if they have been in West Africa\" DOPE"
        ));

        assertThat(tweet)
        .isNotEmpty()
        .contains(tweets);
    }

    @Test
    void findTweetByDate() {

        var date = LocalDate.of(2015, 6, 24);
        var tweet = tweetRepository.findTweet(null, date, null, null, null);
        var tweets = List.of(
            new TweetResponse(
                4L,
                date,
                "ben-cardin",
                "It's politicians like Cardin that have destroyed Baltimore.",
                "Politician @SenatorCardin didn't like that I said Baltimore needs jobs & spirit. It's politicians like Cardin that have destroyed Baltimore."),
            new TweetResponse(
                5L,
                date,
                "neil-young",
                "total hypocrite",
                "For the nonbeliever, here is a photo of @Neilyoung in my office and his $$ request—total hypocrite. http://t.co/Xm4BJvetIa"),
            new TweetResponse(
                6L,
                date,
                "rockin-in-the-free-world",
                "didn't love it",
                ".@Neilyoung’s song, “Rockin’ In The Free World” was just one of 10 songs used as background music. Didn’t love it anyway.")
        );

        assertThat(tweet)
        .isNotEmpty()
        .contains(tweets);
    }

    @Test
    void findTweetByTarget() {

        var tweet = tweetRepository.findTweet(null, null, "thomas-frieden", null, null);
        var tweets = List.of(
            new TweetResponse(
                1L,
                LocalDate.of(2014, 10, 9),
                "thomas-frieden",
                "fool",
                "Can you believe this fool, Dr. Thomas Frieden of CDC, just stated, \"anyone with fever should be asked if they have been in West Africa\" DOPE"),
            new TweetResponse(
                2L,
                LocalDate.of(2014, 10, 9),
                "thomas-frieden",
                "DOPE",
                "Can you believe this fool, Dr. Thomas Frieden of CDC, just stated, \"anyone with fever should be asked if they have been in West Africa\" DOPE")
        );

        assertThat(tweet)
        .isNotEmpty()
        .contains(tweets);
    }

    @Test
    void findTweetByInsultShouldUseLike() {

        var tweet = tweetRepository.findTweet(null, null, null, "action", null);
        var tweets = Collections.singletonList(new TweetResponse(
            3L,
            LocalDate.of(2015, 6, 16),
            "politicians",
            "all talk and no action",
            "Big time in U.S. today - MAKE AMERICA GREAT AGAIN! Politicians are all talk and no action - they can never bring us back."
        ));

        assertThat(tweet)
        .isNotEmpty()
        .contains(tweets);
    }

    @Test
    void findTweetByTweetShouldUseLike() {
        var tweet = tweetRepository.findTweet(null, null, null, null, "just");
        var tweets = List.of(
            new TweetResponse(
                1L,
                LocalDate.of(2014, 10, 9),
                "thomas-frieden",
                "fool",
                "Can you believe this fool, Dr. Thomas Frieden of CDC, just stated, \"anyone with fever should be asked if they have been in West Africa\" DOPE"),
            new TweetResponse(
                2L,
                LocalDate.of(2014, 10, 9),
                "thomas-frieden",
                "DOPE",
                "Can you believe this fool, Dr. Thomas Frieden of CDC, just stated, \"anyone with fever should be asked if they have been in West Africa\" DOPE"),
            new TweetResponse(
                6L,
                LocalDate.of(2015, 6, 24),
                "rockin-in-the-free-world",
                "didn't love it",
                ".@Neilyoung’s song, “Rockin’ In The Free World” was just one of 10 songs used as background music. Didn’t love it anyway."),
            new TweetResponse(
                8L,
                LocalDate.of(2015, 6, 25),
                "jeb-bush",
                "will NEVER Make America Great Again",
                "Just out, the new nationwide @FoxNews poll has me alone in 2nd place, closely behind Jeb Bush-but Bush will NEVER Make America Great Again!"),
            new TweetResponse(
                9L,
                LocalDate.of(2015, 6, 25),
                "molly-sims",
                "a disaster",
                "The ratings for The View are really low. Nicole Wallace and Molly Sims are a disaster. Get new cast or just put it to sleep. Dead T.V.")
        );

        assertThat(tweet)
        .isNotEmpty()
        .contains(tweets);
    }

    @Test
    void findTweetUsingAllFilters() {

        var id = 1L;
        var dateFilter = LocalDate.of(2014, 10, 9);
        var target = "thomas-frieden";
        var insult = "fool";
        var tweetMsg = "Can you believe this fool, Dr. Thomas Frieden of CDC, just stated, \"anyone with fever should be asked if they have been in West Africa\" DOPE";

        var tweet = tweetRepository.findTweet(id, dateFilter, target, "FO", "JUST STATED");
        System.out.println(tweet);

        var tweets = Collections.singletonList(new TweetResponse(id, dateFilter, target, insult, tweetMsg));

        assertThat(tweet)
        .isNotEmpty()
        .contains(tweets);
    }

    @Test
    void findTweetByTargetAndTweetMsg() {

        var target = "thomas-frieden";
        var tweetMsg = "Can you believe this fool, Dr. Thomas Frieden of CDC, just stated, \"anyone with fever should be asked if they have been in West Africa\" DOPE";

        var tweet = tweetRepository.findTweet(null, null, target, null, "FEVER");

        var tweets = List.of(
            new TweetResponse(1L, LocalDate.of(2014, 10, 9), target, "fool", tweetMsg),
            new TweetResponse(2L, LocalDate.of(2014, 10, 9), target, "DOPE", tweetMsg)
        );

        assertThat(tweet)
        .isNotEmpty()
        .contains(tweets);
    }
}