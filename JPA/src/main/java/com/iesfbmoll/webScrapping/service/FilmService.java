package com.iesfbmoll.webScrapping.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iesfbmoll.webScrapping.Data.Film;
import com.iesfbmoll.webScrapping.Data.FilmList;
import com.iesfbmoll.webScrapping.Data.FilmRepository;
import com.iesfbmoll.webScrapping.FileUtils.HTMLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {


    private static final String DEFAULT_URI = "https://www.filmaffinity.com/es/search.php?stype=title&stext=";
    private final String FILE_NAME = String.format("%s\\.fbmoll\\", System.getProperty("user.home"));

    private final FilmRepository repository;
    private Logger log = LoggerFactory.getLogger(getClass());

    public FilmService(FilmRepository repository) {
        this.repository = repository;
    }

    public void setInfo(String name) {
        String uri = String.format("%s%s", DEFAULT_URI, name);
        HTMLParser htmlParser = new HTMLParser();
        FilmList filmList = new FilmList();
        filmList.setFilms(htmlParser.getWebContent(uri));
     //   htmlParser.marshall2XML(FILE_NAME, filmList, name);
       // htmlParser.marshall2JSON(FILE_NAME, filmList.getFilms(), name);
        for (int i = 0; i < filmList.getFilms().size(); i++) {
            if (!this.repository.findById(filmList.getFilms().get(i).getId()).isPresent()) {
                this.repository.save(filmList.getFilms().get(i));
            }
        }
    }

    public List<Film> getBestFilms(String name, String rating) throws JsonProcessingException {
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

    public FilmRepository getRepository() {
        return this.repository;
    }
}
