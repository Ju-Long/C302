package sg.edu.rp.webservices.c302_p06_sakilaclient;

import java.io.Serializable;

public class CategorySummary implements Serializable {
    private String name;
    private int count;

    public CategorySummary(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
