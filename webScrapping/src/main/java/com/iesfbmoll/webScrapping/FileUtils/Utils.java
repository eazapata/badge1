package com.iesfbmoll.webScrapping.FileUtils;

import java.io.File;

public class Utils {
    private static final String invalidChar = ",";
    private static final String space = "";


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
