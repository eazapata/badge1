package Data;

import java.io.Serializable;
import java.util.HashMap;

public class CsvContent implements Serializable {

    private HashMap<Integer, String> atributes = new HashMap<Integer, String>();

    public String getProperty(Integer key) {
        return atributes.get(key);
    }

    public void setProperty(Integer key, String value) {
        atributes.put(key, value);
    }

}
