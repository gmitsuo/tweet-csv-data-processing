package com.zallpy.dataprocessing.services;

import com.zallpy.dataprocessing.entities.Tweet;
import com.zallpy.dataprocessing.entities.dto.TweetResponse;
import com.zallpy.dataprocessing.repositories.TweetRepository;
import com.zallpy.dataprocessing.util.TweetCsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TweetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TweetService.class);

    private final TargetService targetService;
    private final InsultService insultService;
    private final TweetCsvReader tweetCsvReader;
    private final TweetRepository tweetRepository;

    public TweetService(
            TargetService targetService,
            InsultService insultService,
            TweetCsvReader tweetCsvReader,
            TweetRepository tweetRepository) {

        this.targetService = targetService;
        this.insultService = insultService;
        this.tweetCsvReader = tweetCsvReader;
        this.tweetRepository = tweetRepository;
    }

    @Async
    public void loadTweetsCsv(InputStream csvInputStream) throws IOException {

        var stopWatch = new StopWatch();
        stopWatch.start();

        tweetCsvReader.read(csvInputStream)
        .forEach(csvTweetRecord -> {

            var target = targetService.save(csvTweetRecord.getTarget());
            var insult = insultService.save(csvTweetRecord.getInsult());

            var id = csvTweetRecord.getId();
            var date = csvTweetRecord.getDate();
            var tweetMsg = csvTweetRecord.getTweet();
            var tweet = new Tweet(id, date, insult, tweetMsg, target);

            tweetRepository.save(tweet);
        });

        stopWatch.stop();
        LOGGER.info("Finished loading tweets CSV. Running time: {}ms", stopWatch.getTotalTimeMillis());
    }

    public Optional<List<TweetResponse>> findTweet(Long id, LocalDate date, String target, String insult, String tweet) {
        return tweetRepository.findTweet(id, date, target, insult, tweet);
    }
}
