package com.iesfbmoll.webScrapping.service;

import com.iesfbmoll.webScrapping.Data.Movie;
import com.iesfbmoll.webScrapping.Data.FilmList;
import com.iesfbmoll.webScrapping.Data.FilmRepository;
import com.iesfbmoll.webScrapping.FileUtils.HTMLParser;
import com.iesfbmoll.webScrapping.FileUtils.Utils;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class FilmService {


    private static final String DEFAULT_URI = "https://www.filmaffinity.com/es/search.php?stype=title&stext=";
    private final String FILE_NAME = String.format("%s\\.fbmoll\\", System.getProperty("user.home"));

    private final FilmRepository repository;

    public FilmService(FilmRepository repository) {
        this.repository = repository;
    }

    public Long setInfo(String name) {
        String uri = String.format("%s%s", DEFAULT_URI, name);
        HTMLParser htmlParser = new HTMLParser();
        FilmList filmList = new FilmList();
        filmList.setFilms(htmlParser.getWebContent(uri));
        htmlParser.marshall2XML(FILE_NAME, filmList, name);
        htmlParser.marshall2JSON(FILE_NAME, filmList.getFilms(), name);
        Long now = System.currentTimeMillis();
        for (int i = 0; i < filmList.getFilms().size(); i++) {
            if (!this.repository.findById(filmList.getFilms().get(i).getId()).isPresent()) {
                this.repository.save(filmList.getFilms().get(i));
            } else {
                Movie film = this.repository.findOne(filmList.getFilms().get(i).getId());
                film.setFilmRating(filmList.getFilms().get(i).getFilmRating());
                this.repository.save(film);
            }
        }
        Long time = System.currentTimeMillis() - now;
        return time;
    }

    public List<Movie> getInfo(String name, String rating) {
        FilmList filmList = new FilmList();
        HTMLParser htmlParser = new HTMLParser();
        filmList.setFilms(this.repository.findByRating(name, Utils.replace(rating)));
        htmlParser.marshall2JSON(FILE_NAME, filmList.getFilms(), name);
        htmlParser.marshall2XML(FILE_NAME, filmList, name);
        return this.repository.findByRating(name, Utils.replace(rating));
}

    public FilmRepository getRepository() {
        return this.repository;
    }
}
