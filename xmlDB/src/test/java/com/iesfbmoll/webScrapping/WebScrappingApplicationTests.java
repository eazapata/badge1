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
    private static final String DEFAULT_URI = "https://www.filmaffinity.com/es/search.php?stext=joker";
    private final String fileName = String.format("%s\\.fbmoll\\", System.getProperty("user.home"));
    private final String FILM_TITLE = "joker";

    @Test
    void checkContent() {
        ArrayList<Film> films;
        HTMLParser htmlParser = new HTMLParser();
        films = htmlParser.getWebContent(DEFAULT_URI);
        Assert.notEmpty(films, "Lista vacia");
    }

    @Test
    void checkJson() {
        ArrayList<Film> films;
        HTMLParser htmlParser = new HTMLParser();
        films = htmlParser.getWebContent(DEFAULT_URI);
        File file = htmlParser.marshall2JSON(fileName, films,FILM_TITLE);
        Assert.isTrue(file.length() > 0, "File empty");
    }

    @Test
    void checkJsonContent() {
        ArrayList<Film> films;
        ArrayList<Film> jsonFilms;
        HTMLParser htmlParser = new HTMLParser();
        films = htmlParser.getWebContent(DEFAULT_URI);
        File file = htmlParser.marshall2JSON(fileName, films,FILM_TITLE);
        jsonFilms = htmlParser.unMarshallJson(file, Film.class);
        Assert.isTrue(StringUtils.equals(films.get(0).getTitle(), jsonFilms.get(0).getTitle()),
                "Primera pelicula diferente");
    }
}
