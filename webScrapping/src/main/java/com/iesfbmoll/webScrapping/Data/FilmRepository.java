package com.iesfbmoll.webScrapping.Data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FilmRepository extends CrudRepository<Film, Long>{

    List<Film> findByTitle(String title);
    Film findById(long id);
}
