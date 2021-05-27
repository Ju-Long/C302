package sg.edu.rp.webservices.c302_p06_sakilaclient;

import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;//to display in ListView
    }
}
