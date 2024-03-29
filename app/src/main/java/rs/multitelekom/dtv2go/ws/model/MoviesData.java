package rs.multitelekom.dtv2go.ws.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "filmovi")
public class MoviesData {

    @ElementList(entry = "film", inline = true, required = false)
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
