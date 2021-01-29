package com.iesfbmoll.webScrapping.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iesfbmoll.webScrapping.Data.Film;
import com.iesfbmoll.webScrapping.FileUtils.Utils;
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
        filmService.setInfo(name);
        return filmService.getRepository().findByTitleContaining(name);
    }

   /* @GetMapping("/getBestFilms")
    public List<Film> getBestFilms(@RequestParam(value = "name", defaultValue = "") String name,
                                   @RequestParam(value = "rating", defaultValue = "5.0") String rating) {

       return filmService.getRepository().finbest(name,Utils.replace(rating));
    }*/
}
