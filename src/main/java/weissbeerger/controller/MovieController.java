package weissbeerger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import weissbeerger.entity.TypeToSend;
import weissbeerger.utils.CreateXml;
import weissbeerger.utils.OmdbClient;

import java.io.File;

@Controller
public class MovieController {

    @Autowired
    Environment env;

    @Autowired
    OmdbClient omdbClient;

    @Autowired
    CreateXml createXml;


    @GetMapping(value = "/getMovie")
    public ResponseEntity<String> getMovie(@RequestParam(name = "name") String name
            , @RequestParam(name = "typeToSend", required = false, defaultValue = "s") String typeToSend
            , @RequestParam(name = "type", required = false) String type
            , @RequestParam(name = "y", required = false) String y
            , @RequestParam(name = "plot", required = false) String plot
            , @RequestParam(name = "callback", required = false) String callback
            , @RequestParam(name = "v", required = false) String v
            , @RequestParam(name = "page", required = false) String page) {

        if (!omdbClient.inputValidation(name, typeToSend, type, y, plot, callback, v, page)) {
            return new ResponseEntity<String>("Invalid input", HttpStatus.BAD_REQUEST);
        }
        String fileName = env.getProperty("path.for.file") + name + ".xml";
        if (!typeToSend.equals("s") && createXml.fileExists(fileName)) {
            return new ResponseEntity<String>("Success - we have this movie allready in the file system", HttpStatus.CREATED);
        }
        //we don't have this movie in the file system - we will search it in omdb API.
        return omdbClient.getMovieFromSiteAndCreateXml(name, TypeToSend.valueOf(typeToSend), type, y, plot, callback, v, page);
    }

}
