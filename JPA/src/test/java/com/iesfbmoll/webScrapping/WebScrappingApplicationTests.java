package com.iesfbmoll.webScrapping;

import com.iesfbmoll.webScrapping.Data.Movie;
import com.iesfbmoll.webScrapping.Data.FilmList;
import com.iesfbmoll.webScrapping.Data.FilmRepository;
import com.iesfbmoll.webScrapping.FileUtils.HTMLParser;
import com.iesfbmoll.webScrapping.FileUtils.Utils;
import com.iesfbmoll.webScrapping.service.FilmService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;

@SpringBootTest
class WebScrappingApplicationTests {
    private static final String DEFAULT_URI = "https://www.filmaffinity.com/es/search.php?stext=";
    private final String pathFile = String.format("%s\\.fbmoll\\", System.getProperty("user.home"));
    private final String FILM_TITLE = "batman";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FilmRepository filmRepository;

    @Test
    void checkRepository() {
        FilmService filmService = new FilmService(filmRepository);
        String uri = String.format("%s%s", DEFAULT_URI, FILM_TITLE);
        this.filmRepository.deleteAll();
        filmService.setInfo(uri);
        Assert.isTrue(filmRepository.findByTitleContaining(FILM_TITLE) != null,
                "Repository null.");
    }

    @Test
    void checkTime() {
        FilmService filmService = new FilmService(filmRepository);
        String uri = String.format("%s%s", DEFAULT_URI, FILM_TITLE);
        Long time = filmService.setInfo(uri);
        logger.info(String.format("Peticion web scrapping realizada en %s ms", time));
        Assert.isTrue(filmRepository.findByTitleContaining(FILM_TITLE) != null,
                "Repository not updated.");
    }

    @Test
    void checkJsonContent() {
        String uri = String.format("%s%s", DEFAULT_URI, FILM_TITLE);
        ArrayList<Movie> films;
        ArrayList<Movie> jsonFilms;
        HTMLParser htmlParser = new HTMLParser();
        films = htmlParser.getWebContent(uri);
        File file = htmlParser.marshall2JSON(pathFile, films, FILM_TITLE);
        jsonFilms = htmlParser.unMarshallJson(file, Movie.class);
        Assert.isTrue(StringUtils.equals(films.get(0).getTitle(), jsonFilms.get(0).getTitle()),
                "Films not match.");
    }

    @Test
    void checkXMLContent() {
        String uri = String.format("%s%s", DEFAULT_URI, FILM_TITLE);
        FilmList filmList = new FilmList();
        HTMLParser htmlParser = new HTMLParser();
        filmList.setFilms(htmlParser.getWebContent(uri));
        File xmlFIle = htmlParser.marshall2XML(pathFile, filmList, FILM_TITLE);
        FilmList filmListXML = htmlParser.unmarshallContent(xmlFIle, filmList);
        Assert.isTrue(StringUtils.equals(filmList.getFilms().get(0).getTitle(),
                filmListXML.getFilms().get(0).getTitle()), "Las peliculas no coinciden.");
    }

    @Test
    void checkMarshalling() {
        String uri = String.format("%s%s", DEFAULT_URI, FILM_TITLE);
        FilmList filmList = new FilmList();
        HTMLParser htmlParser = new HTMLParser();
        filmList.setFilms(htmlParser.getWebContent(uri));

        File jsonFile = htmlParser.marshall2JSON(pathFile, filmList.getFilms(), FILM_TITLE);
        FilmList jsonList = new FilmList();
        jsonList.setFilms(htmlParser.unMarshallJson(jsonFile, Movie.class));

        File xmlFile = htmlParser.marshall2XML(pathFile, filmList, FILM_TITLE);
        FilmList xmlList = htmlParser.unmarshallContent(xmlFile, filmList);

        Assert.isTrue(StringUtils.equals(jsonList.getFilms().get(0).getTitle(), xmlList.getFilms().get(0).getTitle())
                , "Las peliculas no coinciden.");
    }

    @Test
    void checkRating() {
        String RATING = "8";
        String uri = String.format("%s%s", DEFAULT_URI, FILM_TITLE);
        FilmList filmList = new FilmList();
        HTMLParser htmlParser = new HTMLParser();
        ArrayList<Movie> films = htmlParser.getWebContent(uri);
        filmList.setFilms(htmlParser.getFilmsByRating(films, RATING));
        boolean correctRating = Utils.checkRating(filmList.getFilms(), RATING);
        Assert.isTrue(correctRating, "Rating wrong.");
    }

}
