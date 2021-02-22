package com.zallpy.dataprocessing.util;

import com.zallpy.dataprocessing.entities.dto.CsvTweetRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class TweetCsvReaderTest {

    @Value("classpath:data/tweets.csv")
    Resource resourceFile;

    private TweetCsvReader tweetCsvReader;

    @BeforeEach
    void setup() {
        this.tweetCsvReader = new TweetCsvReader();
    }

    @Test
    void readCsvFile() throws IOException {

        var tweetRecords = tweetCsvReader.read(resourceFile.getInputStream());

        assertThat(tweetRecords)
        .hasSize(9)
        .containsExactly(
            new CsvTweetRecord(1L, "10/9/2014", "thomas-frieden", "fool", "Can you believe this fool, Dr. Thomas Frieden of CDC, just stated, \"anyone with fever should be asked if they have been in West Africa\" DOPE"),
            new CsvTweetRecord(2L, "10/9/2014", "thomas-frieden", "DOPE", "Can you believe this fool, Dr. Thomas Frieden of CDC, just stated, \"anyone with fever should be asked if they have been in West Africa\" DOPE"),
            new CsvTweetRecord(3L, "6/16/2015", "politicians", "all talk and no action", "Big time in U.S. today - MAKE AMERICA GREAT AGAIN! Politicians are all talk and no action - they can never bring us back."),
            new CsvTweetRecord(4L, "6/24/2015", "ben-cardin", "It's politicians like Cardin that have destroyed Baltimore.", "Politician @SenatorCardin didn't like that I said Baltimore needs jobs & spirit. It's politicians like Cardin that have destroyed Baltimore."),
            new CsvTweetRecord(5L, "6/24/2015", "neil-young", "total hypocrite", "For the nonbeliever, here is a photo of @Neilyoung in my office and his $$ request—total hypocrite. http://t.co/Xm4BJvetIa"),
            new CsvTweetRecord(6L, "6/24/2015", "rockin-in-the-free-world", "didn't love it", ".@Neilyoung’s song, “Rockin’ In The Free World” was just one of 10 songs used as background music. Didn’t love it anyway."),
            new CsvTweetRecord(7L, "6/25/2015", "willie-geist", "uncomfortable looking", "Uncomfortable looking NBC reporter Willie Geist calls me to ask for favors and then mockingly smiles when he is told of my high poll numbers"),
            new CsvTweetRecord(8L, "6/25/2015", "jeb-bush", "will NEVER Make America Great Again", "Just out, the new nationwide @FoxNews poll has me alone in 2nd place, closely behind Jeb Bush-but Bush will NEVER Make America Great Again!"),
            new CsvTweetRecord(9L, "6/25/2015", "molly-sims", "a disaster", "The ratings for The View are really low. Nicole Wallace and Molly Sims are a disaster. Get new cast or just put it to sleep. Dead T.V.")
        );
    }
}