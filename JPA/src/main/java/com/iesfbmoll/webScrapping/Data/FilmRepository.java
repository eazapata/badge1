package com.iesfbmoll.webScrapping.Data;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface FilmRepository extends CrudRepository<Film, Long> {


   /* @Modifying
    @Query("insert into movie (id,link,title,year,duration,casting,filmRating,description) values")
    void insert (@Param("id") long id,
                                @Param("link") String link,
                                @Param("title") String title,
                                @Param("year") String year,
                                @Param("duration") String duration,
                                @Param("casting")Map casting,
                                @Param("filmRating") double filmRating,
                                @Param("description") String description);*/

    List<Film> findByTitleContaining(String title);

   /* @Query("SELECT m FROM movie m WHERE m.rating > ?1")
    List<Film> finbest(String title, double filmRating);*/


}
