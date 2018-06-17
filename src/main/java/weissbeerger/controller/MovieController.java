package weissbeerger.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import weissbeerger.entity.TypeToSend;
import weissbeerger.utils.CreateXml;
import weissbeerger.entity.Movie;
import weissbeerger.entity.Search;
import weissbeerger.utils.OmdbClient;

import javax.xml.bind.JAXBException;
import java.io.File;

@Controller
public class MovieController {

    @Autowired
    Environment env;

    @Autowired
    OmdbClient omdbClient;


    @GetMapping(value = "/getMovie")
    public ResponseEntity<String> getMovie(@RequestParam(name = "name") String name
            , @RequestParam(name = "typeToSend", required = false, defaultValue = "s") String typeToSend
            , @RequestParam(name = "type", required = false) String type
            , @RequestParam(name = "y", required = false) String y
            , @RequestParam(name = "plot", required = false) String plot
            , @RequestParam(name = "callback", required = false) String callback
            , @RequestParam(name = "v", required = false) String v
            , @RequestParam(name = "page", required = false) String page) {

        if(!omdbClient.inputValidation(name, typeToSend, type, y, plot, callback, v, page)){
            return new ResponseEntity<String>("Invalid input", HttpStatus.BAD_REQUEST);
        }
        String fileName = env.getProperty("path.for.file") + name + ".xml";
        File file = new File(fileName);
        if (file.exists() && !file.isDirectory()) {
            return new ResponseEntity<String>("Success", HttpStatus.CREATED);
        } else {
            return omdbClient.getMovieFromSite(name, fileName, TypeToSend.valueOf(typeToSend), type, y, plot, callback, v, page);

        }
    }





}
