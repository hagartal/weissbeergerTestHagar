package weissbeerger.utils;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import weissbeerger.entity.Movie;
import weissbeerger.entity.Search;
import weissbeerger.entity.TypeToSend;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OmdbClient {

    final static Logger logger = Logger.getLogger(OmdbClient.class);

    private final static String URL_OMDB = "http://www.omdbapi.com/?apikey=d9a1a503&";
    private final static String URL_OMDB_I = "i=";
    private final static String URL_OMDB_T = "t=";
    private final static String URL_OMDB_S = "s=";
    private final static String OMDB_ERROR = "{\"Response\":\"False\",\"Error\":\"Movie not found!\"}";
    private RestTemplate restTemplate = new RestTemplate();
    Gson gson = new Gson();

    @Autowired
    Environment env;

    @Autowired
    CreateXml createXml;


    public ResponseEntity<String> getMovieFromSiteAndCreateXml(String name, TypeToSend typeToSend, String type, String y, String plot, String callback, String v, String page) {
        //Building the url
        String urlStr = createUrl(name, typeToSend, type, y, plot, callback, v, page);
        return getMovieFromSiteAndCreateXml(name, typeToSend, urlStr);
    }


    private String createUrl(String name, TypeToSend typeToSend, String type, String y, String plot, String callback, String v, String page) {
        logger.info("creating URL to send to OMDB");
        String typeToSendStr = typeToSend == TypeToSend.t ? URL_OMDB_T : (typeToSend == TypeToSend.i ? URL_OMDB_I : URL_OMDB_S);
        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(URL_OMDB);
        urlBuffer.append(typeToSendStr);
        urlBuffer.append(name);
        if (type != null) {
            urlBuffer.append("&type=");
            urlBuffer.append(type);
        }
        if (y != null) {
            urlBuffer.append("&y=");
            urlBuffer.append(y);
        }
        if (plot != null) {
            urlBuffer.append("&plot=");
            urlBuffer.append(plot);
        }
        if (callback != null) {
            urlBuffer.append("&callback=");
            urlBuffer.append(callback);
        }
        if (callback != null) {
            urlBuffer.append("&callback=");
            urlBuffer.append(callback);
        }
        if (typeToSend.equals("s") && page != null) {
            urlBuffer.append("&page=");
            urlBuffer.append(page);
        }
        logger.info(urlBuffer.toString() + " the url we will use to invoke OMDB API.");
        return urlBuffer.toString();
    }


    private ResponseEntity<String> getMovieFromSiteAndCreateXml(String name, TypeToSend typeToSend, String urlStr) {
        logger.info("Send request to OMDB API");
        String movieStr = restTemplate.getForObject(urlStr, String.class);
        try {
            if (movieStr.equals(OMDB_ERROR)) {
                parseAndCreateEmptyFile(name);
            }
            else if (typeToSend == TypeToSend.s) {
                parseAndCreateSerchXml(movieStr);
            } else {
                parseAndCreateXml(movieStr);
            }
        } catch (JAXBException | NullPointerException e) {
            logger.error("error occurred while trying to create a new xml, the error: " + e.getMessage());
            return new ResponseEntity<String>("Some error creating xml", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Finished the call to OMDB API.");
        return movieStr.contains(OMDB_ERROR) ? new ResponseEntity<String>("OMDB Error", HttpStatus.CREATED) : new ResponseEntity<String>("Success with new call to OMDB", HttpStatus.CREATED);
    }

    private void parseAndCreateEmptyFile(String name) throws JAXBException {
        Movie movieRequest = new Movie();
        movieRequest.setTitle(name);
        createXml.createXml(movieRequest);
    }

    private void parseAndCreateXml(String movieStr) throws JAXBException {
        Movie movieRequest = new Movie();
        movieRequest = gson.fromJson(movieStr, Movie.class);
        createXml.createXml(movieRequest);
    }

    private void parseAndCreateSerchXml(String movieStr) throws JAXBException {
        Search search = new Search();
        search = gson.fromJson(movieStr, search.getClass());
        createXml.createXml(search);
    }


    public boolean inputValidation(String name, String typeToSend, String type, String y, String plot, String callback, String v, String page) {
        logger.info("Input validation.");
        if (name == null) {
            return false;
        }
        if (!typeToSend.equals("s") && !typeToSend.equals("t") && !typeToSend.equals("i")) {
            return false;
        }
        if (type != null && !(type.equals("movie") || type.equals("series") || type.equals("episode"))) {
            return false;
        }
        if (y != null && !isInteger(y)) {
            return false;
        }
        if (plot != null && !(plot.equals("short") || plot.equals("full"))) {
            return false;
        }
        if (v != null && !isInteger(v)) {
            return false;
        }
        if (page != null && !isInteger(page)) {
            return false;
        }
        return true;
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

}
