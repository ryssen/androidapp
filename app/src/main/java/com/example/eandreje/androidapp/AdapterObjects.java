package com.example.eandreje.androidapp;

public class AdapterObjects {
    private Person person;
    private ColumnContent cellData;

    public void setCellData(ColumnContent cellData) {
        this.cellData = cellData;
    }

    public void setPerson(Person person) {

        this.person = person;
    }

    public ColumnContent getCellData() {

        return cellData;
    }

    public Person getPerson() {

        return person;
    }
}
