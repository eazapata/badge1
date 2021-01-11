package com.iesfbmoll.webScrapping.Controller;

import com.iesfbmoll.webScrapping.Data.Film;
import com.iesfbmoll.webScrapping.Data.FilmList;
import com.iesfbmoll.webScrapping.FileUtils.HTMLParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ScrappingController {
    private static final String DEFAULT_URI = "https://www.filmaffinity.com/es/search.php?stype=title&stext=";
    private final String FILE_NAME = String.format("%s\\.fbmoll\\", System.getProperty("user.home"));

    @GetMapping("/getfilms")
    public List<Film> getGames(@RequestParam(value = "name", defaultValue = "") String name) {
        String uri = String.format("%s%s", DEFAULT_URI, name);
        HTMLParser htmlParser = new HTMLParser();
        FilmList filmList = new FilmList();
        filmList.setFilms(htmlParser.getWebContent(uri));
        htmlParser.marshall2XML(FILE_NAME, filmList, name);
        htmlParser.marshall2JSON(FILE_NAME, filmList.getFilms(), name);
        return filmList.getFilms();
    }

    @GetMapping("/getBestFilms")
    public List<Film> getBestFilms(@RequestParam(value = "name", defaultValue = "") String name,
                                   @RequestParam(value = "rating", defaultValue = "5.0") String rating) {
        String uri = String.format("%s%s", DEFAULT_URI, name);
        HTMLParser htmlParser = new HTMLParser();
        FilmList filmList = new FilmList();
        ArrayList<Film> films;
        films = htmlParser.getWebContent(uri);
        filmList.setFilms(htmlParser.getFilmsByRating(films, rating));
        htmlParser.marshall2XML(FILE_NAME, filmList, name);
        htmlParser.marshall2JSON(FILE_NAME, filmList.getFilms(), name);
        return filmList.getFilms();
    }


}
