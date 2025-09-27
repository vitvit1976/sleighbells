package com.elfstack.toys.taskmanagement.service;

import com.elfstack.toys.taskmanagement.domain.Country;
import com.elfstack.toys.taskmanagement.domain.SimpleEntity;
import com.elfstack.toys.taskmanagement.domain.SimpleField;
import com.elfstack.toys.taskmanagement.domain.TYPE_FIELDS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.*;
import java.util.*;
import java.util.stream.Stream;

public class DataService {
    private static List<Country> countries = null;

    private static Map<String, List<SimpleEntity>> stringListMap = new HashMap<>();

    public static List<Country> getCountries() {
        if (countries == null) loadFromFile();
        return countries;
    }

    private static TYPE_FIELDS getRandomType(Boolean hasChild) {
        try {
            int ind = (int) (Math.random() * 100) % TYPE_FIELDS.values().length;
            return Arrays.asList(TYPE_FIELDS.values()).get(ind);
        } catch (Exception e){
            return TYPE_FIELDS.TFTextField;
        }
    }

    private static List<SimpleField> getFields(Boolean hasChild) {
        int max = (int) (Math.random() * 5) + 5;

        return Stream.iterate(2, x -> x + 3)
                .limit(max + 1)
                .map(x -> "field_" + x.toString())
                .map(x -> {
                    TYPE_FIELDS typeField = getRandomType(hasChild);
                    return new SimpleField(x, typeField.getCompClass(), typeField.name() + x.toUpperCase(),
                            typeField, hasChild && typeField.getHasChild()? getItems(getRandom(10), false) : null);
                }).toList();
    }

    public static String getRandom(int length) {
        char ch = 'A';
        char[] arr = new char[length];
        for (int x = 0; x < length; x = x + 1)
            arr[x] = (char) (Math.random() * 20 + (int) ch);
        return String.valueOf(arr);
    }

    public static Map<Integer, String> getRandomMap(TYPE_FIELDS t) {
        if (!t.getCompClass().getName().endsWith("Map")) return null;
        int cnt = (int) (Math.random() * 15) + 5;
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < cnt; i++)
            map.put(i * i, getRandom(cnt));
        return map;
    }

    public static Object getRandomValue(TYPE_FIELDS t) {
        Double aDouble = (Math.random() * 10000) + 1000;
        switch (t.getCompClass().getName()) {
            case "java.lang.Integer":
                return (int) Math.round(aDouble);
            case "java.lang.String":
                return getRandom((int) (Math.random() * 15) + 5);
            case "java.lang.Double":
                return aDouble;
            case "java.lang.Boolean":
                return aDouble > 5000;
            case "java.util.Date":
                return new Date(Math.round(aDouble) * 60 * 24 * 365 * 100);
            case "java.time.LocalDateTime":
                return LocalDateTime.of(LocalDate.of((int) (1980 + Math.random() * 100), (int) (1 + Math.random() * 10), (int) (1 + Math.random() * 20)),
                        LocalTime.of((int) (1 + Math.random() * 10), (int) (1 + Math.random() * 20)));
            case "java.time.LocalDate":
                return LocalDate.of((int) (1980 + Math.random() * 100), (int) (1 + Math.random() * 10), (int) (1 + Math.random() * 20));
            case "java.time.LocalTime":
                return LocalTime.of((int) (1 + Math.random() * 10), (int) (1 + Math.random() * 20));
        }
        return null;
    }

    private static List<SimpleEntity> getItems(String nmData, Boolean hasChild) {
        List<SimpleField> fields = getFields(hasChild);
        List<SimpleEntity> simpleEntities = new ArrayList<>();
        int cnt = (int) (Math.random() * 100) + 10;
        for (int x = 0; x < cnt; x++) {
            // for(SimpleField f: fields) f.setFieldValue(nmData +"_f" + x);
            Map<SimpleField, Object> addFields = new HashMap<>();
            fields.forEach(z -> addFields.put(z,
                    getRandomValue(z.getTypeField())));
            simpleEntities.add(new SimpleEntity(addFields));
        }
        return simpleEntities;
    }

    public static List<SimpleEntity> getSimpleEntities(String nmData, Boolean hasChild) {
        if (!stringListMap.containsKey(nmData))
            stringListMap.put(nmData, getItems(nmData, hasChild));
        return stringListMap.get(nmData);
    }

    private static void addCountry(String s) {
        if (s.isEmpty()) return;
        Country c = null;
        List<String> argCoun = new ArrayList<>();
        for (String a : s.split("│"))
            if (!a.trim().isEmpty()) argCoun.add(a.trim());
        if (countries == null) countries = new ArrayList<>();
        countries.add(new Country(argCoun));
    }

    private static void loadFromFile() {
        String fmame = "C:\\git_project\\sleighbells-app\\src\\main\\resources\\data";
        try (BufferedReader br = new BufferedReader(new FileReader(fmame))) {
            String line = br.readLine();
            while (line != null) {
                addCountry(line.trim());
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addEntity(SimpleEntity entity, String route) {
        stringListMap.get(route).add(entity);
    }

    public static void delEntity(SimpleEntity entity, String route) {
        stringListMap.get(route).remove(entity);
    }

    public static void updEntity(SimpleEntity oldEntity, SimpleEntity newEntity, String route) {
        //stringListMap.get(route).get(entity)
        oldEntity.updValue(newEntity);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) System.out.println(getRandom((int) (Math.random() * 5) + 5));
    }

//    private static final Logger log = LoggerFactory.getLogger(DataService.class);
}