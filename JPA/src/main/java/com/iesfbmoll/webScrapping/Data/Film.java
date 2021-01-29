package com.iesfbmoll.webScrapping.Data;


import com.iesfbmoll.webScrapping.generic.JSONObjectConverter;
import org.hibernate.annotations.Type;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


//@Table(name = "movie")
//@TypeDef(name = "json", typeClass = JsonStringType.class)
@Entity
@Table(name = "movie")
public class Film  {
    @Id
    private Long id;
    private String link;
    private String title;
    private String year;
    private String duration;
    @Type( type = "json" )
    @Column( columnDefinition = "json" )
    private String[] jsonValue;
   // private Map<String, Object> metaData = new HashMap<>();
  /*  @Column(columnDefinition = "TEXT")
    @Convert(converter= JSONObjectConverter.class)
    private JSONObject jsonData;*/
    private double filmRating;
    private String description;

    public String[] getJsonValue() {
        return jsonValue;
    }

    public void setJsonValue(String[] jsonValue) {
        this.jsonValue = jsonValue;
    }

    /* public Map<String, Object> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, Object> metaData) {
        this.metaData = metaData;
    }

    /* public JSONObject getJsonData() {
        return jsonData;
    }

    public void setJsonData(JSONObject jsonData) {
        this.jsonData = jsonData;
    }*/

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}


