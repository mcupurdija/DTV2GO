package rs.multitelekom.dtv2go.ws.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "film")
public class Movie {

    @Element(name = "naslov")
    private String title;

    @Element(name = "opis", required = false)
    private String description;

    @Element(name = "trajanje", required = false)
    private String duration;

    @Element(name = "zanr", required = false)
    private String genre;

    @Element(name = "poster", required = false)
    private String poster;

    @Element(name = "url")
    private String url;

    @Element(name = "subtitle", required = false)
    private String subtitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                ", genre='" + genre + '\'' +
                ", poster='" + poster + '\'' +
                ", url='" + url + '\'' +
                ", subtitle='" + subtitle + '\'' +
                '}';
    }
}
