package com.iesfbmoll.webScrapping;

import com.iesfbmoll.webScrapping.Data.Film;
import com.iesfbmoll.webScrapping.Data.FilmList;
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
        File file = htmlParser.marshall2JSON(fileName, films, FILM_TITLE);
        Assert.isTrue(file.length() > 0, "File empty");
    }

    @Test
    void checkJsonContent() {
        ArrayList<Film> films;
        ArrayList<Film> jsonFilms;
        HTMLParser htmlParser = new HTMLParser();
        films = htmlParser.getWebContent(DEFAULT_URI);
        File file = htmlParser.marshall2JSON(fileName, films, FILM_TITLE);
        jsonFilms = htmlParser.unMarshallJson(file, Film.class);
        Assert.isTrue(StringUtils.equals(films.get(0).getTitle(), jsonFilms.get(0).getTitle()),
                "Primera pelicula diferente");
    }

    @Test
    void checkXMLContent() {
        FilmList filmList = new FilmList();
        HTMLParser htmlParser = new HTMLParser();
        filmList.setFilms(htmlParser.getWebContent(DEFAULT_URI + FILM_TITLE));
        File xmlFIle = htmlParser.marshall2XML(fileName, filmList, FILM_TITLE);
        FilmList filmListXML = htmlParser.unmarshallContent(xmlFIle, filmList);
        Assert.isTrue(StringUtils.equals(filmList.getFilms().get(0).getTitle(),
                filmListXML.getFilms().get(0).getTitle()), "Las peliculas no coinciden.");
    }

    @Test
    void checkMarshalling() {
        FilmList filmList = new FilmList();
        HTMLParser htmlParser = new HTMLParser();
        filmList.setFilms(htmlParser.getWebContent(DEFAULT_URI + FILM_TITLE));

        File jsonFile = htmlParser.marshall2JSON(fileName, filmList.getFilms(), FILM_TITLE);
        FilmList jsonList = new FilmList();
        jsonList.setFilms(htmlParser.unMarshallJson(jsonFile, Film.class));

        File xmlFile = htmlParser.marshall2XML(fileName, filmList, FILM_TITLE);
        FilmList xmlList = htmlParser.unmarshallContent(xmlFile, filmList);

        Assert.isTrue(StringUtils.equals(jsonList.getFilms().get(0).getTitle(), xmlList.getFilms().get(0).getTitle())
                , "Las peliculas no coinciden.");
    }

}
