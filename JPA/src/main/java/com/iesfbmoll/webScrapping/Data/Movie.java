package com.iesfbmoll.webScrapping.Data;

import generic.JsonToListConverter;
import generic.JsonToMapConverter;


import javax.persistence.*;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "movie")
public class Movie {
    @Id
    private Long id;
    private String link;
    private String title;
    private String year;
    private String duration;
    @Column(columnDefinition = "json")
    @Convert(converter = JsonToListConverter.class)
    private List<String> casting;
    private double filmRating;
    @Column(columnDefinition = "json")
    @Convert(converter = JsonToMapConverter.class)
    private Map<String,String> description;

    public List<String> getCasting() {
        return casting;
    }

    public void setCasting(List<String> casting) {
        this.casting = casting;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getFilmRating() {
        return filmRating;
    }

    public void setFilmRating(double filmRating) {
        this.filmRating = filmRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }
}


