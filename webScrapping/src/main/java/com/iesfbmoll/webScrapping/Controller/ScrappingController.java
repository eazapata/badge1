package com.iesfbmoll.webScrapping.Controller;

import com.iesfbmoll.webScrapping.Data.Film;
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
    public List<Film> getGames(@RequestParam(value="name",defaultValue = "")String name){
        HTMLParser htmlParser = new HTMLParser();
        ArrayList<Film> arrData;
        arrData =  htmlParser.getWebContent(DEFAULT_URI +name);
        htmlParser.marshall2JSON(FILE_NAME,arrData,name);
        return arrData;
    }




}
