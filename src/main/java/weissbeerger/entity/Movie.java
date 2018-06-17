package weissbeerger.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class Movie implements Serializable{

    private String Title;
    private String Year;
    private String imdbID;
    private String Type;
    private String Poster;
    private String Rated;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Writer;
    private String Actors;
    private String Plot;
    private String Language;
    private String Country;
    private String Awards;
    private List<Rating> Ratings;
    private String Metascore;
    private String imdbRating;
    private String imdbVotes;
    private String DVD;
    private String BoxOffice;
    private String Production;
    private String Website;
    private String Response;


    public String getRated() {
        return Rated;
    }

    @XmlElement
    public void setRated(String rated) {
        this.Rated = rated;
    }

    public String getReleased() {
        return Released;
    }

    @XmlElement
    public void setReleased(String released) {
        this.Released = released;
    }

    public String getRuntime() {
        return Runtime;
    }

    public String getTitle() {
        return Title;
    }
    @XmlElement
    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }
    @XmlElement
    public void setYear(String year) {
        Year = year;
    }

    public String getImdbID() {
        return imdbID;
    }
    @XmlElement
    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return Type;
    }
    @XmlElement
    public void setType(String type) {
        Type = type;
    }

    public String getPoster() {
        return Poster;
    }
    @XmlElement
    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getDVD() {
        return DVD;
    }
    @XmlElement
    public void setDVD(String DVD) {
        this.DVD = DVD;
    }

    public String getResponse() {
        return Response;
    }

    @XmlElement
    public void setRuntime(String runtime) {
        this.Runtime = runtime;
    }

    public String getGenre() {
        return Genre;
    }

    @XmlElement
    public void setGenre(String genre) {
        this.Genre = genre;
    }

    public String getDirector() {
        return Director;
    }

    @XmlElement
    public void setDirector(String director) {
        this.Director = director;
    }

    public String getWriter() {
        return Writer;
    }

    @XmlElement
    public void setWriter(String writer) {
        this.Writer = writer;
    }

    public String getActors() {
        return Actors;
    }

    @XmlElement
    public void setActors(String actors) {
        this.Actors = actors;
    }

    public String getPlot() {
        return Plot;
    }

    @XmlElement
    public void setPlot(String plot) {
        this.Plot = plot;
    }

    public String getLanguage() {
        return Language;
    }

    @XmlElement
    public void setLanguage(String language) {
        this.Language = language;
    }

    public String getCountry() {
        return Country;
    }

    @XmlElement
    public void setCountry(String country) {
        this.Country = country;
    }

    public String getAwards() {
        return Awards;
    }

    @XmlElement
    public void setAwards(String awards) {
        this.Awards = awards;
    }

    public List<Rating> getRatings() {
        return Ratings;
    }

    @XmlElementWrapper(name = "Ratings")
    @XmlElement(name = "Rating")
    public void setRatings(List<Rating> ratings) {
        this.Ratings = ratings;
    }

    public String getMetascore() {
        return Metascore;
    }

    @XmlElement
    public void setMetascore(String metascore) {
        this.Metascore = metascore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    @XmlElement
    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    @XmlElement
    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }


    public String getDvd() {
        return DVD;
    }

    @XmlElement
    public void setDvd(String dvd) {
        this.DVD = dvd;
    }

    public String getBoxOffice() {
        return BoxOffice;
    }

    @XmlElement
    public void setBoxOffice(String boxOffice) {
        this.BoxOffice = boxOffice;
    }

    public String getProduction() {
        return Production;
    }

    @XmlElement
    public void setProduction(String production) {
        this.Production = production;
    }

    public String getWebsite() {
        return Website;
    }

    @XmlElement
    public void setWebsite(String website) {
        this.Website = website;
    }

    public String isResponse() {
        return Response;
    }

    @XmlElement
    public void setResponse(String response) {
        this.Response = response;
    }
}
