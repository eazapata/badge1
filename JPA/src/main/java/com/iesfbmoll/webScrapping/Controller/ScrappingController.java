package com.iesfbmoll.webScrapping.Controller;


import com.iesfbmoll.webScrapping.Data.Movie;
import com.iesfbmoll.webScrapping.FileUtils.HTMLParser;
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


    @GetMapping("/setInfo")
    public Iterable<Movie> getGames(@RequestParam(value = "name", defaultValue = "") String name) {
        filmService.setInfo(name);
        return filmService.getRepository().findByTitleContaining(name);
    }

    @GetMapping("/getInfo")
    public List<Movie> getInfo(@RequestParam(value = "name", defaultValue = "") String name,
                               @RequestParam(value = "rating", defaultValue = "0.0") String rating) {

        return filmService.getInfo(name,rating);
    }
}
