package com.iesfbmoll.webScrapping.Controller;

import com.iesfbmoll.webScrapping.Data.Film;
import com.iesfbmoll.webScrapping.service.FilmService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScrappingController {

    private final FilmService filmService;

    public ScrappingController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping("/getfilms")
    public Iterable<Film> getGames(@RequestParam(value = "name", defaultValue = "") String name) {
        filmService.getFilmList(name);
        return filmService.getRepository().findAll();
    }

    @GetMapping("/getBestFilms")
    public List<Film> getBestFilms(@RequestParam(value = "name", defaultValue = "") String name,
                                   @RequestParam(value = "rating", defaultValue = "5.0") String rating) {

       return filmService.getBestFilms(name,rating);
    }
}
