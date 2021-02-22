package com.zallpy.dataprocessing.controllers;

import com.zallpy.dataprocessing.entities.dto.ErrorResponse;
import com.zallpy.dataprocessing.services.TweetService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

@RestController
@RequestMapping("/tweets")
public class TweetsController {

    private final TweetService tweetService;

    public TweetsController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> loadTweets(@RequestParam("data") MultipartFile file) throws IOException {
        tweetService.loadTweetsCsv(file.getInputStream());
        return ResponseEntity.accepted().build();
    }

    @GetMapping()
    public ResponseEntity<?> findTweet(@RequestParam Map<String, String> queryParams) {

        if (queryParams.isEmpty())
            return ResponseEntity.badRequest()
            .body(new ErrorResponse("No filter has been provided. Allowed filters: id, date, target, insult and tweet"));

        Long id = null;
        LocalDate date = null;

        try {
            id = queryParams.get("id") != null ? Long.parseLong(queryParams.get("id")) : null;
            date = queryParams.get("date") != null ? LocalDate.parse(queryParams.get("date")) : null;
        }
        catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
            .body(new ErrorResponse("Unable to parse query param id. Reason: " + e.getMessage()));
        }
        catch (DateTimeParseException e) {
            return ResponseEntity.badRequest()
            .body(new ErrorResponse("Unable to parse query param date. Reason: " + e.getMessage() + ". Format should follow the pattern yyyy-MM-dd."));
        }

        var target = queryParams.get("target");
        var insult = queryParams.get("insult");
        var tweet = queryParams.get("tweet");

        return ResponseEntity.of(tweetService.findTweet(id, date, target, insult, tweet));
    }
}
