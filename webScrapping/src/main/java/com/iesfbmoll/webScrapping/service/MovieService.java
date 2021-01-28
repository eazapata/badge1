package com.iesfbmoll.webScrapping.service;

import com.iesfbmoll.webScrapping.Data.Film;
import com.iesfbmoll.webScrapping.Data.FilmRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final FilmRepository repository;

    public MovieService(FilmRepository repository) {
        this.repository = repository;
    }

    public void initDB(List<Film> films) {
        try {
/*            String empty = null;
            empty.equals("nulaquen");
            repository.save(new Customer("Jack", "Bauer"));
            repository.save(new Customer("Chloe", "O'Brian"));
            repository.save(new Customer("Kim", "Bauer"));
            repository.save(new Customer("David", "Palmer"));
            repository.save(new Customer("Michelle", "Dessler"));*/
        } catch (Exception e) {
        }
    }

    public FilmRepository getRepository() {
        return repository;
    }
}
