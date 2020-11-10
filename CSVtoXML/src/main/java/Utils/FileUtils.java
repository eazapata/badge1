package Utils;

import java.io.Serializable;
import java.util.ArrayList;

public class FileUtils {

    public static <T extends Serializable> T compareAndReplace(String[] line){
        ArrayList<String> aux = new ArrayList<>();
        String[] fields = null;

        for (int i = 0; i < line.length ; i++) {
            if(line[i].contains(";")){
                fields = line[i].split(";");
                for (int j = 0; j < fields.length; j++) {
                    aux.add(fields[j]);
                }

            }
        }
        for (int i = 0; i < aux.size() ; i++) {
            fields[i] = aux.get(i);
        }
        return (T)fields;
    }
}
