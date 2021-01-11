package com.iesfbmoll.webScrapping.FileUtils;

import java.io.File;

public class Utils {
    private static final String invalidChar = ",";
    private static final String correctChar = ".";
    private static final String space = "";

    /**
     * Recibe el string que contiene la puntuación de la película, en caso de no tener le asigna un 0, de lo contrario
     * le hace un casting a double para posteriores operaciones.
     * @param string puntuación como string.
     * @return Puntuación convertida a double.
     */
    public static double replace(String string){
        double numberParsed;
        if(string.contains(invalidChar)){
            string = string.replace(invalidChar,correctChar);
            numberParsed = Double.parseDouble(string);
        }else{
            numberParsed = 0;
        }
        return numberParsed;
    }

    /**
     * Comprueba si existe la ruta y si no crea el directorio.
     * @param path ruta que se comprobará.
     */
    public static void checkDirectory(String path) {
        File file = new File(path);
        if ((!file.exists() || (!file.isDirectory()))) {
            file.mkdirs();
        }
    }

    /**
     * Método para limpiar los actores de caracteres que no nos interesan
     * @param string nombre del actor que queremos formatar.
     * @return nombre formatado.
     */
    public static String deleteChar(String string){
        if(string.contains(invalidChar)){
            string = string.replace(invalidChar,space);
        }
        return string;
    }
}
