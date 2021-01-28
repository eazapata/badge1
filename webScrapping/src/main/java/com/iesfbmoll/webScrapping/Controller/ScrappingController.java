package com.iesfbmoll.webScrapping.Controller;

import com.iesfbmoll.webScrapping.Data.Film;
import com.iesfbmoll.webScrapping.Data.FilmRepository;
import com.iesfbmoll.webScrapping.FileUtils.HTMLParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ScrappingController {
    private final String DEFAULT_URI = "https://www.filmaffinity.com/es/search.php?stype=title&stext=";
    private final String FILE_PATH = String.format("%s\\.fbmoll\\", System.getProperty("user.home"));

    private final FilmRepository filmRepository;
    public ScrappingController(FilmRepository filmRepository) {this.filmRepository = filmRepository;}

    @GetMapping("/getfilms")
    public List<Film> getGames(@RequestParam(value = "name", defaultValue = "") String name) {
        String uri = String.format("%s%s", DEFAULT_URI, name);
        HTMLParser htmlParser = new HTMLParser();
        ArrayList<Film> arrData;
        arrData = htmlParser.getWebContent(uri);
        htmlParser.marshall2JSON(FILE_PATH, arrData, name);
        filmRepository.saveAll(arrData);
        return (List<Film>) filmRepository.findByTitle(name);
    }
}
