package weissbeerger.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Search {

    private List<Movie> Search;

    public List<Movie> getSearch() {
        return Search;
    }

    @XmlElementWrapper(name = "MovieList")
    @XmlElement(name = "Movie")
    public void setSearch(List<Movie> search) {
        Search = search;
    }
}
