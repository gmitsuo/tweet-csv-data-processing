package com.zallpy.dataprocessing.util;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.zallpy.dataprocessing.entities.dto.CsvTweetRecord;
import com.zallpy.dataprocessing.services.TweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class TweetCsvReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(TweetService.class);

    public List<CsvTweetRecord> read(InputStream csv) throws IOException {

        var csvSchema = CsvSchema.emptySchema()
        .withColumnSeparator(',')
        .withHeader();

        return new CsvMapper()
        .readerFor(CsvTweetRecord.class).with(csvSchema)
        .readValues(csv)
        .readAll().stream()
        .peek(csvTweetRecord -> LOGGER.debug("Tweet record: {}", csvTweetRecord))
        .map(csvTweetRecord -> (CsvTweetRecord) csvTweetRecord)
        .collect(toList());
    }
}
