package com.javacourse.solvd.word_calc;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordCalculator {

    private static final Logger LOGGER = LogManager.getLogger(WordCalculator.class);


    public static final String RESOURCES_PATH = "src/main/resources/";
    public static final String ARTICLE_FILE_PATH = RESOURCES_PATH + "article.txt";
    public static final String OUT_FILE_PATH = RESOURCES_PATH +"out.txt";

    private String searchLine = null;

    public static void main(String[] args) {
        WordCalculator app = new WordCalculator();
        app.start();
    }

    private void start() {

        List<String> words = askForWords();
        if (words.isEmpty()) {
            LOGGER.info("No words were entered. Exiting...");
            return;
        }
        Map<String, Integer> statistic = processArticle(words);
        writeStatistic(statistic);

    }


    private void writeStatistic(Map<String, Integer> statistic) {
        try {
            List<String> lines = new ArrayList<>();
            lines.add("Search request:" + searchLine);
            statistic.forEach((word, count) -> lines.add(word + " - " + count));
            FileUtils.writeLines(new File(OUT_FILE_PATH), lines, true);
        } catch (IOException e) {
            LOGGER.error("Error during writing statistic to file:", e);
        }
    }




    private List<String> askForWords() {
        LOGGER.info("Welcome!\nPlease enter coma separated words to search in article.txt. Example: apple, banana, cherry");
        Scanner scanner = new Scanner(System.in);

        List<String> words = new ArrayList<>();
        if (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            searchLine = line;
            Arrays.stream(line.split(",")).forEach(word -> {
                word = word.trim();
                if (StringUtils.isNotBlank(word)) {
                    words.add(word);
                }
            });
        }
        return words;

    }

    private Map<String, Integer> processArticle(List<String> wordsToSearch) {
        Map<String, Integer> result = new LinkedHashMap<>();
        wordsToSearch.forEach(word -> result.put(word, 0));
        try {
            List<String> lines = FileUtils.readLines(new File(ARTICLE_FILE_PATH), StandardCharsets.UTF_8);
            lines.forEach(line -> {
                if (StringUtils.isNotBlank(line)) {
                    wordsToSearch.forEach(s -> {
                        int count = StringUtils.countMatches(line, s);
                        if (count > 0) {
                            result.put(s, result.get(s) + count);
                        }
                    });
                }
            });
        } catch (Exception e) {
            LOGGER.error("Error during reading article.txt:", e);
        }
        return result;
    }

}
