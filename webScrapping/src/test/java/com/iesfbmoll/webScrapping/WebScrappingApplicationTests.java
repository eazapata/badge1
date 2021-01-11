package com.iesfbmoll.webScrapping;

import com.iesfbmoll.webScrapping.Data.Film;
import com.iesfbmoll.webScrapping.FileUtils.HTMLParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;

@SpringBootTest
class WebScrappingApplicationTests {
    private static final String DEFAULT_URI = "https://www.filmaffinity.com/es/search.php?stext=";
    private final String pathFile = String.format("%s\\.fbmoll\\", System.getProperty("user.home"));
    private final String FILM_TITLE = "joker";

    @Test
    void checkContent() {
        String uri = String.format("%s%s",DEFAULT_URI,FILM_TITLE);
        ArrayList<Film> films;
        HTMLParser htmlParser = new HTMLParser();
        films = htmlParser.getWebContent(uri);
        Assert.notEmpty(films, "List empty");
    }

    @Test
    void checkJson() {
        String uri = String.format("%s%s",DEFAULT_URI,FILM_TITLE);
        ArrayList<Film> films;
        HTMLParser htmlParser = new HTMLParser();
        films = htmlParser.getWebContent(uri);
        File file = htmlParser.marshall2JSON(pathFile, films, FILM_TITLE);
        Assert.isTrue(file.length() > 0, "File empty");
    }

    @Test
    void checkJsonContent() {
        String uri = String.format("%s%s",DEFAULT_URI,FILM_TITLE);
        ArrayList<Film> films;
        ArrayList<Film> jsonFilms;
        HTMLParser htmlParser = new HTMLParser();
        films = htmlParser.getWebContent(uri);
        File file = htmlParser.marshall2JSON(pathFile, films, FILM_TITLE);
        jsonFilms = htmlParser.unMarshallJson(file, Film.class);
        Assert.isTrue(StringUtils.equals(films.get(0).getTitle(), jsonFilms.get(0).getTitle()),
                "Films not match.");
    }
}
