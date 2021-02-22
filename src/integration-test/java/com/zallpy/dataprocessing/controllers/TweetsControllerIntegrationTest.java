package com.zallpy.dataprocessing.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zallpy.dataprocessing.entities.dto.TweetResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collections;

import static java.lang.String.format;
import static java.time.Duration.ofMillis;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.awaitility.Awaitility.setDefaultPollInterval;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Profile("test")
@AutoConfigureMockMvc
@TestInstance(PER_CLASS)
public class TweetsControllerIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Value("classpath:data/tweets.csv")
    Resource resourceFile;

    @BeforeAll
    void loadCsvFile() throws Exception {

        var csvMultiPartFile = new MockMultipartFile("data", "tweets.csv", "text/csv", resourceFile.getInputStream());

        mvc.perform(multipart("/tweets")
        .file(csvMultiPartFile)
        .content(csvMultiPartFile.getBytes()))
        .andExpect(status().isAccepted());


        setDefaultPollInterval(ofMillis(50));
        //Await loading csv
        await().until(() -> {
            var lastTweetId = 9L;
            return mvc.perform(get(format("/tweets?id=%d", lastTweetId)))
            .andReturn().getResponse().getStatus() == HttpStatus.OK.value();
        });
    }

    @Test
    void findTweet() throws Exception {

        var id = "6";
        var date = "2015-06-24";
        var target = "rockin-in-the-free-world";
        var insult = "love";
        var tweet = "background music";

        var getTweetUri = format("/tweets?id=%s&date=%s&target=%s&insult=%s&tweet=%s", id, date, target, insult, tweet);

        var tweetResponse = Collections.singletonList(new TweetResponse(
            6L,
            LocalDate.of(2015, 6, 24),
            "rockin-in-the-free-world",
            "didn't love it",
            ".@Neilyoung’s song, “Rockin’ In The Free World” was just one of 10 songs used as background music. Didn’t love it anyway."
        ));

        var result = mvc.perform(get(getTweetUri))
        .andExpect(status().isOk())
        .andReturn().getResponse()
        .getContentAsString(StandardCharsets.UTF_8);

        assertThat(result)
        .isEqualTo(mapper.writeValueAsString(tweetResponse));
    }


}
