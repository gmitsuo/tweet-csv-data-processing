package com.zallpy.dataprocessing.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zallpy.dataprocessing.entities.dto.TweetResponse;
import com.zallpy.dataprocessing.services.TweetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TweetsController.class)
class TweetsControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    TweetsController tweetsController;

    @Autowired ObjectMapper objectMapper;

    @MockBean
    TweetService tweetService;

    @Test
    void getTweetsWithoutFiltersShouldReturnBadResponse() throws Exception {

        mvc.perform(get("/tweets"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", equalTo("No filter has been provided. Allowed filters: id, date, target, insult and tweet")));
    }

    @Test
    void getTweetWithUnparsableIdShouldReturnBadRequest() throws Exception {

        mvc.perform(get("/tweets?id=AAA"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", equalTo("Unable to parse query param id. Reason: For input string: \"AAA\"")));
    }

    @Test
    void getTweetWithUnparsableDateShouldReturnBadRequest() throws Exception {

        mvc.perform(get("/tweets?date=123345-123-123"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", equalTo("Unable to parse query param date. Reason: Text '123345-123-123' could not be parsed at index 0. Format should follow the pattern yyyy-MM-dd.")));
    }

    @Test
    void getTweetUsingCorrectFormatShouldReturnOK() throws Exception {

        var id = "1";
        var date = "2021-02-21";
        var target = "John Doe";
        var insult = "fool";
        var tweet = "just";

        var tweetResponse = Collections.singletonList(new TweetResponse(Long.parseLong(id), LocalDate.parse(date), target, insult, tweet));

        when(tweetService.findTweet(Long.parseLong(id), LocalDate.parse(date), target, insult, tweet))
        .thenReturn(Optional.of(tweetResponse));

        var getTweetUri = format("/tweets?id=%s&date=%s&target=%s&insult=%s&tweet=%s", id, date, target, insult, tweet);

        mvc.perform(get(getTweetUri))
        .andExpect(status().isOk())
        .andExpect(content().string(objectMapper.writeValueAsString(tweetResponse)));
    }
}