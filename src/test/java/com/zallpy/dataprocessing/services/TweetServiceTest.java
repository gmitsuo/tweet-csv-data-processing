package com.zallpy.dataprocessing.services;

import com.zallpy.dataprocessing.entities.Insult;
import com.zallpy.dataprocessing.entities.Target;
import com.zallpy.dataprocessing.entities.Tweet;
import com.zallpy.dataprocessing.entities.dto.CsvTweetRecord;
import com.zallpy.dataprocessing.repositories.TweetRepository;
import com.zallpy.dataprocessing.util.TweetCsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TweetServiceTest {

    @MockBean
    TargetService targetService;

    @MockBean
    InsultService insultService;

    @MockBean
    TweetCsvReader tweetCsvReader;

    @MockBean
    TweetRepository tweetRepository;

    TweetService tweetService;

    @BeforeEach
    void setup() {
        this.tweetService = new TweetService(targetService, insultService, tweetCsvReader, tweetRepository);
    }

    @Test
    void shouldCreateNewRecordForEveryRow() throws IOException {

        var csvFile = mock(InputStream.class);
        var row1 = new CsvTweetRecord(1L, "2/19/2021", "John Doe 1", "Insult 1", "Tweet msg 1");
        var row2 = new CsvTweetRecord(2L, "2/20/2021", "John Doe 2", "Insult 2", "Tweet msg 2");
        var row3 = new CsvTweetRecord(3L, "2/21/2021", "John Doe 3", "Insult 3", "Tweet msg 3");

        var records = List.of(row1, row2, row3);
        when(tweetCsvReader.read(csvFile))
        .thenReturn(records);

        for (CsvTweetRecord record : records) {

            var target = new Target(record.getId(), record.getTarget());
            when(targetService.save(record.getTarget()))
            .thenReturn(target);

            var insult = new Insult(record.getId(), record.getInsult());
            when(insultService.save(record.getInsult()))
            .thenReturn(insult);
        }

        tweetService.loadTweetsCsv(csvFile);

        verify(tweetCsvReader, times(1)).read(csvFile);

        for (CsvTweetRecord record : records) {

            verify(targetService, times(1)).save(record.getTarget());

            var id = record.getId();
            var date = record.getDate();
            var insult = new Insult(record.getId(), record.getInsult());
            var tweet = record.getTweet();
            var target = new Target(record.getId(), record.getTarget());
            verify(tweetRepository, times(1)).save(new Tweet(id, date, insult, tweet, target));
        }

        verifyNoMoreInteractions(tweetCsvReader, targetService, tweetRepository);
    }
}