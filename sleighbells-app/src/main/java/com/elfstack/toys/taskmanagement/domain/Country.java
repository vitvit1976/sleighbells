package com.elfstack.toys.taskmanagement.domain;

import java.util.List;

public class Country {
    private String name;
    private String shortName;
    private String mnemo;

    public Country(List<String> argCoun) {
        int i = 0;
        for (String s : argCoun) {
            i++;
            switch (i) {
                case 1:
                    name = s;
                case 2:
                    shortName = s;
                case 3:
                    mnemo = s;
            }
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "\nCountry{" +
                "name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", mnemo='" + mnemo + '\'' +
                '}';
    }
}
