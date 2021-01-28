package com.iesfbmoll.webScrapping.Data;

import com.iesfbmoll.webScrapping.FileUtils.HashMapConverter;
import netscape.javascript.JSObject;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Map;

@Entity
public class Film {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String link;
    private String title;
    private String year;
    private String duration;
  //  @Transient
  // @Convert(converter = HashMapConverter.class)
    //private JSObject cast;
   // private ArrayList<String> cast;
    private double filmRating;
    private String description;

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

  /* public ArrayList<String> getCast() {
        return cast;
    }

    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }
*/
    public double getFilmRating() {
        return filmRating;
    }

    public void setFilmRating(double filmRating) {
        this.filmRating = filmRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
