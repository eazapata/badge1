package com.iesfbmoll.webScrapping.Data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilmRepository extends CrudRepository<Film, Long> {
    List<Film> findByTitle(String lastName);


}
