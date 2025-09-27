package com.elfstack.toys.taskmanagement.domain;

public enum TYPE_ANY_TASK {
    INBOX("Входящие"), SENT("Отправленные"), DELETE("Удаленные");
    private String desc;
    TYPE_ANY_TASK(String desc) {
        this.desc = desc;
    }
}
