package de.dhbw.project.nls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataStorage {
    private HashMap<String, List<String>> data = new HashMap<String, List<String>>();

    public void add(String key, String value) {
        List<String> list;
        if (!data.containsKey(key)) {
            list = new ArrayList<String>();
            data.put(key, list);
        } else {
            list = data.get(key);
        }

        list.add(value);
    }

    public List<String> get(String key) {
        return data.get(key);
    }
}
