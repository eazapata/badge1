package com.iesfbmoll.webScrapping.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "myFilms")
@XmlAccessorType(XmlAccessType.FIELD)
public class FilmList implements Serializable {
    @XmlElement(name = "name")
    private String name;

    @XmlElementWrapper(name = "films")
    @XmlElement(name = "film")
    private List<Film> films;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public List<Film> getFilms() {
        return films;
    }

}
