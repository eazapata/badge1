package com.iesfbmoll.webScrapping.FileUtils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.iesfbmoll.webScrapping.Data.Film;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class HTMLParser {

    private ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME);
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(mapper.getTypeFactory());
        mapper.setAnnotationIntrospector(introspector);
        // Don't close the output stream
        mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        // Don't include NULL properties.
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    private static final Logger log = LoggerFactory.getLogger(HTMLParser.class);
    private final String FILM_SELECTOR = "div.fa-shadow-nb.item-search";
    private final String TITLE_SELECTOR = "div.mc-title";
    private final String FILM_DATA_SELECTOR = "div.movie-card-1";
    private final String ROW_SELECTOR = "dd";
    private final String YEAR_SELECTOR = "dd[itemprop = datePublished]";
    private final String DURATION_SELECTOR = "dd[itemprop = duration]";
    private final String RATING_SELECTOR = "div#movie-rat-avg";
    private final String DESCRIPTION_SELECTOR = "dd[itemprop = description]";
    private final String CAST_SELECTOR = "span[itemprop = actor]";
    private final String CAST_SELECTOR_NULL = "dd.card-cast";
    private final String LINK_TAG = "a";
    private final String LINK_ATTR = "href";

    /**
     * Método que comprueba el estado de una página indicada y devuelve su resultando conun entero.
     *
     * @param URI String que contiene la dirección de la página a la que se quiere acceder.
     * @return Código que indica el estado de la página.
     */
    public int getStatus(String URI) {
        Connection.Response response;
        int responseCode = 400;
        try {
            response = Jsoup.connect(URI).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
            responseCode = response.statusCode();
        } catch (Exception ex) {
            log.error("No se puede acceder a la página");
        }
        return responseCode;
    }


    /**
     * Método que obtiene el contenido de una página y convierte en un documento.
     *
     * @param URI String que contiene la dirección de la página a la que se quiere acceder.
     * @return Documento que contiene la información de la página.
     */
    public Document getHtmlDocument(String URI) {
        Document doc = null;
        try {
            doc = Jsoup.connect(URI).userAgent("Mozilla/5.0").timeout(100000).get();
        } catch (Exception ex) {
            log.error("Error al obtener el contenido HTML");
        }
        return doc;
    }

    /**
     * Método que obtiene la información de una página y la almacena en un arrayList.
     *
     * @param URI String que contiene la dirección de la página a la que se quiere acceder.
     * @param <T> Objeto género al que es convertido el arrayList.
     * @return El objeto génerico para evitar castings posteriores.
     */
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T getWebContent(String URI) {
        ArrayList<Film> films = new ArrayList<>();
        T o;
        if (getStatus(URI) == 200) {
            Document document = getHtmlDocument(URI);
            Elements filmsElements = document.select(FILM_SELECTOR);
            getDataFilm(filmsElements, films);
            o = (T) films;
            return o;

        } else {
            log.error("La página solicitada no está activa actualmente.");
        }

        return null;
    }

    /**
     * Método que recibe unos elementos que son peliculas y una lista de peliculas a rellenar.
     *
     * @param filmsElements Películas obtenidas del método getWebContent.
     * @param films         List de peliculas donde almacenaremos la información.
     */
    private void getDataFilm(Elements filmsElements, List<Film> films) {
        String errorText = "Información no disponible.";
        for (Element elem : filmsElements.select(FILM_DATA_SELECTOR)) {
            Film film = new Film();
            film.setLink(elem.select(LINK_TAG).attr(LINK_ATTR));
            film.setTitle(elem.select(TITLE_SELECTOR).text());

            if (getStatus(film.getLink()) == 200) {
                Document document = getHtmlDocument(film.getLink());
                Elements dataFilm = document.select(ROW_SELECTOR);
                film.setYear(dataFilm.select(YEAR_SELECTOR).text());
                film.setDuration(dataFilm.select(DURATION_SELECTOR).text());
                film.setFilmRating(document.select(RATING_SELECTOR).text());
                film.setDescription(dataFilm.select(DESCRIPTION_SELECTOR).text());
                if (dataFilm.select(CAST_SELECTOR).size() == 0) {
                    film.setCast(getCast(dataFilm.select(CAST_SELECTOR_NULL)));
                } else {
                    film.setCast(getCast(dataFilm.select(CAST_SELECTOR)));
                }
            } else {
                film.setYear(errorText);
                film.setDuration(errorText);
            }
            films.add(film);
        }
    }

    /**
     * Método que obtiene el reparto de una película.
     *
     * @param castElements Elementos de la página que corresponden con el reparto de actores.
     * @param <T>          Objeto género al que es convertido el arrayList.
     * @return List de String donde almacenaremos la información del reparto.
     */
    @SuppressWarnings("unchecked")
    private <T extends Serializable> T getCast(Elements castElements) {
        ArrayList<String> cast = new ArrayList<>();
        T o;
        int i = 0;
        while ((i < 5)) {
            if (i == castElements.size()) {
                i = 5;
            } else {
                String castElement = castElements.get(i).text();
                cast.add(castElement);
                i++;
            }
        }
        o = (T) cast;
        return o;
    }

    /**
     * Método que convierte en un archivo JSON la información obtenida de las películas.
     *
     * @param filePath Ruta dondo tendremos el archivo.
     * @param list     List donde tenemos almacenada la información de las películas.
     * @param fileName Nombre del archivo donde se guardará la información.
     * @return archivo donde se ha almacenado la información.
     */
    public File marshall2JSON(String filePath, List<?> list, String fileName) {

        filePath = String.format("%s\\%s.json", filePath, fileName);
        File file = new File(filePath);
        if (list.size() > 0) {
            try {
                getMapper().writeValue(file, list);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;
        }
        return null;
    }

    /**
     * Método que extrae la información de un JSON.
     *
     * @param file      archivo del cual se extrae la información.
     * @param typeClass clase del objeto almacenado en el List.
     * @param <T>       Objeto género al que es convertido el arrayList.
     * @return list donde tenemos la información del archivo.
     */
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T unMarshallJson(File file, Class<?> typeClass) {
        ArrayList<Film> list = new ArrayList<>();
        try {
            list = getMapper().readValue(file, getMapper().getTypeFactory().constructCollectionType(ArrayList.class, typeClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (T) list;
    }
}

