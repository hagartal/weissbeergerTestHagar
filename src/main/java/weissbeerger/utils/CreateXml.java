package weissbeerger.utils;

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

    @Autowired
    Environment env;

    public void createXml(Movie movieRequest) throws JAXBException {
        String fileName = env.getProperty("path.for.file") + movieRequest.getTitle() + ".xml";
        if (!fileExists(fileName)) {
            File file = new File(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Movie.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(movieRequest, file);
        }
    }

    public void createXml(Search movieSearchRequest) throws JAXBException {
        movieSearchRequest.getSearch().forEach(movie -> {
            try {
                createXml(movie);
            } catch (JAXBException e) {
                e.printStackTrace();
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
