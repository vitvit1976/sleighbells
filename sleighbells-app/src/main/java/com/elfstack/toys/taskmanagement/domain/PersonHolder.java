package com.elfstack.toys.taskmanagement.domain;

import java.util.ArrayList;
import java.util.List;

public class PersonHolder {
    private List<Person> persons;

    private static PersonHolder inst;

    private PersonHolder() {
        persons = new ArrayList<>();
    }

    public static PersonHolder getInst() {
        if (inst == null) inst = new PersonHolder();
        return inst;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void addPerson(Person p){
        persons.add(p);
    }
}
