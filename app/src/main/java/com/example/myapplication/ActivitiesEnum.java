package com.example.myapplication;

public enum ActivitiesEnum {
    HOME(1), CATALOGO(2), CHAPTERLIST(3), LEGGILIBRO(4),
    COMMENTI(5), FORMCOMMENTO(6), MYPROFILE(7), PROFILOAUTORE(8),
    PASSWORDDIMENTICATA(9), REPORT(10);

    final int value;

    ActivitiesEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
