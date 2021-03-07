package Service;

import Data.Movie;
import Data.MovieList;
import FileUtils.HtmlParser;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;

@QuarkusTest
public class ScrappingTest {
    private static final String DEFAULT_URI = "https://www.filmaffinity.com/es/search.php?stext=";
    private final String pathFile = String.format("%s\\.fbmoll\\", System.getProperty("user.home"));
    private final String FILM_TITLE = "patata";

    @Inject
    Service service;

    @Test
    public void testWebScrapping(){
        Assertions.assertNotNull(service.setInfo(""));
    }

    @Test
    public void testGetInfo(){
        Assertions.assertNotNull(service.getInfo(FILM_TITLE,"5"));
    }

    @Test
    void checkJsonContent() {
        String uri = String.format("%s%s", DEFAULT_URI, FILM_TITLE);
        ArrayList<Movie> films;
        ArrayList<Movie> jsonFilms;
        HtmlParser htmlParser = new HtmlParser();
        films = htmlParser.getWebContent(uri);
        File file = htmlParser.marshall2JSON(pathFile, films, FILM_TITLE);
        jsonFilms = htmlParser.unMarshallJson(file, Movie.class);
        Assert.isTrue(StringUtils.equals(films.get(0).getTitle(), jsonFilms.get(0).getTitle()),
                "Films not match.");
    }

    @Test
    void checkXMLContent() {
        String uri = String.format("%s%s", DEFAULT_URI, FILM_TITLE);
        MovieList filmList = new MovieList();
        HtmlParser htmlParser = new HtmlParser();
        filmList.setFilms(htmlParser.getWebContent(uri));
        File xmlFIle = htmlParser.marshall2XML(pathFile, filmList, FILM_TITLE);
        MovieList filmListXML = htmlParser.unmarshallContent(xmlFIle, filmList);
        Assert.isTrue(StringUtils.equals(filmList.getFilms().get(0).getTitle(),
                filmListXML.getFilms().get(0).getTitle()), "Las peliculas no coinciden.");
    }

    @Test
    void checkMarshalling() {
        String uri = String.format("%s%s", DEFAULT_URI, FILM_TITLE);
        MovieList movieList = new MovieList();
        HtmlParser htmlParser = new HtmlParser();
        movieList.setFilms(htmlParser.getWebContent(uri));

        File jsonFile = htmlParser.marshall2JSON(pathFile, movieList.getFilms(), FILM_TITLE);
        MovieList jsonList = new MovieList();
        jsonList.setFilms(htmlParser.unMarshallJson(jsonFile, Movie.class));

        File xmlFile = htmlParser.marshall2XML(pathFile, movieList, FILM_TITLE);
        MovieList xmlList = htmlParser.unmarshallContent(xmlFile, movieList);

        Assert.isTrue(StringUtils.equals(jsonList.getFilms().get(0).getTitle(), xmlList.getFilms().get(0).getTitle())
                , "Las peliculas no coinciden.");
    }
}