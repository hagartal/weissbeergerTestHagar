package weissbeerger;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.File;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;


    @Autowired
    Environment env;

    MockRestServiceServer mockServer;

    RestTemplate restTemplate = new RestTemplate();
    @Before
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }
    @Test
    public void checkValidUrlName() throws Exception {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"+port+"/getMovie/",
                String.class);
        assertThat(response.getBody(), containsString("Required String parameter 'name' is not present"));
    }

    @Test
    public void checkValidUrlTypeToSend() throws Exception {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"+port+"/getMovie?name=stam&typeToSend=h",
                String.class);
        assertThat(response.getBody(), equalTo("Invalid input"));
    }

    @Test
    public void checkValidUrlType() throws Exception {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"+port+"/getMovie?name=stam&type=test",
                String.class);
        assertThat(response.getBody(), equalTo("Invalid input"));
    }

    @Test
    public void checkValidUrlYear() throws Exception {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"+port+"/getMovie?name=stam&y=test",
                String.class);
        assertThat(response.getBody(), equalTo("Invalid input"));
    }

    @Test
    public void checkValidUrlPlot() throws Exception {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"+port+"/getMovie?name=stam&plot=test",
                String.class);
        assertThat(response.getBody(), equalTo("Invalid input"));
    }

    @Test
    public void checkValidUrlVersion() throws Exception {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"+port+"/getMovie?name=stam&v=test",
                String.class);
        assertThat(response.getBody(), equalTo("Invalid input"));
    }

    @Test
    public void checkValidUrlPage() throws Exception {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"+port+"/getMovie?name=stam&page=test",
                String.class);
        assertThat(response.getBody(), equalTo("Invalid input"));
    }

    @Test
    public void checkErrorMovieNotFound() throws Exception {
        mockServer.expect(requestTo("http://www.omdbapi.com/?apikey=d9a1a503&s=stam"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"Response\":\"False\",\"Error\":\"Movie not found!\"}", MediaType.APPLICATION_JSON));
        ResponseEntity<String> response = template.getForEntity("http://localhost:"+port+"/getMovie?name=stam",
                String.class);
        assertThat(response.getBody(), equalTo("OMDB Error"));
        String fileName = env.getProperty("path.for.file") + "stam.xml";
        File file = new File(fileName);
        assertFalse (!file.exists() && file.isDirectory());
        file.delete();
    }

    @Test
    public void checkFileIsExists() throws Exception {
        mockServer.expect(requestTo("http://www.omdbapi.com/?apikey=d9a1a503&t=stam"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"Response\":\"False\",\"Error\":\"Movie not found!\"}", MediaType.APPLICATION_JSON));
        ResponseEntity<String> response = template.getForEntity("http://localhost:"+port+"/getMovie?name=stam&typeToSend=t",
                String.class);
        assertThat(response.getBody(), equalTo("OMDB Error"));
        String fileName = env.getProperty("path.for.file") + "stam.xml";
        File file = new File(fileName);
        assertFalse (!file.exists() && file.isDirectory());
        response = template.getForEntity("http://localhost:"+port+"/getMovie?name=stam&typeToSend=t",
                String.class);
        assertThat(response.getBody(), equalTo("Success - we have this movie allready in the file system"));
        file.delete();
    }







}
