package com.iesfbmoll.webScrapping.Data;

import javax.xml.bind.annotation.*;
import java.util.List;
@XmlRootElement(name="myCatalog")
@XmlAccessorType(XmlAccessType.FIELD)
public class FilmList {
    @XmlElement(name = "name")
    private String name;
    @XmlElementWrapper(name = "products")
    @XmlElement(name = "product")
    private List<Film> games;




    public List<Film> getProducts() {
        return games;
    }

    public void setProducts(List<Film> games) {
        this.games = games;
    }
}
