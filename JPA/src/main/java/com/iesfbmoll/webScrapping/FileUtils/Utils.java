package com.iesfbmoll.webScrapping.FileUtils;


import com.iesfbmoll.webScrapping.Data.Film;

import java.io.File;
import java.util.List;

public class Utils {
    private static final String invalidComma = ",";
    private static final String invalidChar = ")";
    private static final String correctChar = ".";
    private static final String space = "";

    /**
     * Comprueba si existe la ruta y si no crea el archivo.
     *
     * @param path ruta a comprobar.
     */
    public static void checkDirectory(String path) {
        File file = new File(path);
        if ((!file.exists() || (!file.isDirectory()))) {
            file.mkdirs();
        }
    }

    /**
     * Recibe el string que contiene la puntuación de la película, en caso de no tener le asigna un 0, de lo contrario
     * le hace un casting a double para posteriores operaciones.
     *
     * @param string puntuación como string.
     * @return Puntuación convertida a double.
     */
    public static double replace(String string) {
        double numberParsed;
        if (string.contains(invalidComma)) {
            string = string.replace(invalidComma, correctChar);
            numberParsed = Double.parseDouble(string);
        } else if (string.contains(correctChar)) {
            numberParsed = Double.parseDouble(string);
        } else {
            numberParsed = 0;
        }
        return numberParsed;
    }

    /**
     * Método para limpiar los actores de caracteres que no nos interesan
     *
     * @param string nombre del actor que queremos formatar.
     * @return nombre formatado.
     */
    public static String deleteChar(String string) {

        //(X '[^,\)]*')
        CharSequence[] invalidsChars = {"'", "[", "^", ",", ")", "*","("};
        for (int i = 0; i < string.length(); i++) {
            for (CharSequence invalidsChar : invalidsChars) {
                if (string.contains(invalidsChar)) {
                    string = string.replace(invalidsChar, "");
                }
            }
        }
        return string;
/*
        if (string.contains(invalidComma)) {
            string = string.replace(invalidComma, space);
        } else if(string.contains(invalidChar)){
            string = string.replace(invalidChar, space);
        }
        return string;*/
    }

    public static String[] compareAndReplace(String[] line) {

        return line;
    }

    /**
     * Comprueba la puntuación de cada película de la lista
     *
     * @param films  lista de peliculas.
     * @param rating puntuacion a comprobrar.
     * @return Si hay una pelicula con una puntuación mayor false, de lo contrario true.
     */
    public static boolean checkRating(List<Film> films, String rating) {
        for (Film film : films) {
            if (film.getFilmRating() > Double.parseDouble(rating)) {
                return false;
            }
        }
        return true;
    }

   /* public static void updateFilm (Optional<Film> film, List<Film> filmList, int position){

        film.setId(filmList.get(position).getId());
        film.setLink(filmList.get(position).getLink());
        film.setTitle(filmList.get(position).getTitle());
        film.setYear(filmList.get(position).getYear());
        film.setDuration(filmList.get(position).getDuration());
        film.setFilmRating(filmList.get(position).getFilmRating());


    }*/
}
