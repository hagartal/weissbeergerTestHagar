package weissbeerger.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import weissbeerger.entity.Movie;
import weissbeerger.entity.Search;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

@Component
public class CreateXml {

    private final static Logger logger = Logger.getLogger(CreateXml.class);
    @Autowired
    private Environment env;

    public void createXml(Movie movieRequest) throws JAXBException, NullPointerException {
        if(movieRequest == null || movieRequest.getTitle() == null){
            logger.error("Movie object is null - can not create xml.");
            throw new NullPointerException();
        }
        String fileName = env.getProperty("path.for.file") + movieRequest.getTitle() + ".xml";
        logger.info("start to create xml: " +fileName);
        if (!fileExists(fileName)) {
            File file = new File(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Movie.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(movieRequest, file);
        }
        logger.info(fileName + " file was created.");
    }

    public void createXml(Search movieSearchRequest) throws JAXBException {
        movieSearchRequest.getSearch().forEach(movie -> {
            try {
                createXml(movie);
            } catch (JAXBException e) {
                logger.error("error occurred while trying to create a new xml: "+ movie.getTitle() + " the error: "+ e.getMessage());
            }
        });
    }

    public boolean fileExists(String fileName) {
        File file = new File(fileName);
        if (file.exists() && !file.isDirectory()) {
            return true;
        }
        return false;
    }


}
