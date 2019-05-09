package com.example.realmapp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Contact extends RealmObject {
    @PrimaryKey
    private long id;

//    @Required
//    private String name;

    @Required
    private String nameNumber;

//    @Required
//    private String numero;
    private int edad;
    private boolean genero;

    public Contact(){}
    public Contact(long idExt, String nameExt, String numberExt){
        id = idExt;
//        name = nameExt;
//        numero = numberExt;
    }
    public Contact(long idExt, String nameExt, int edadExt, boolean generoExt){
        id = idExt;
        nameNumber = nameExt;
//        numero = numberExt;
        edad = edadExt;
        genero = generoExt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getNumero() {
//        return numero;
//    }
//
//    public void setNumero(String numero) {
//        this.numero = numero;
//    }

    public String getNameNumber() {
        return nameNumber;
    }

    public void setNameNumber(String nameNumber) {
        this.nameNumber = nameNumber;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isGenero() {
        return genero;
    }

    public void setGenero(boolean genero) {
        this.genero = genero;
    }
}

