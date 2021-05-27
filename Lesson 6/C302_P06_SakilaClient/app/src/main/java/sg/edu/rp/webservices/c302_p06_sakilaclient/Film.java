package sg.edu.rp.webservices.c302_p06_sakilaclient;

import java.io.Serializable;

public class Film implements Serializable {
    private String title;
    private String description;
    private int release_year;
    private String rating;

    public Film(String title, String description, int release_year, String rating) {
        this.title = title;
        this.description = description;
        this.release_year = release_year;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getRelease_year() {
        return release_year;
    }

    public String getRating() {
        return rating;
    }
}
