package twitter;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static java.util.Objects.requireNonNull;

/**
 * Configuration object that contains the OAuth authentication keys.
 */
public class Config {
    public final String consumerKey;
    public final String consumerSecret;
    public final String accessToken;
    public final String accessSecret;

    public Config(String consumerKey, String consumerSecret, String accessToken, String accessSecret) {
        this.consumerKey = requireNonNull(consumerKey);
        this.consumerSecret = requireNonNull(consumerSecret);
        this.accessToken = requireNonNull(accessToken);
        this.accessSecret = requireNonNull(accessSecret);
    }

    /**
     * Parses a configuration xml file.
     * @param path the absolute path that references the configuration file
     * @return the parsed configuration
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static Config parse(String path) throws ParserConfigurationException, SAXException, IOException {
        File keysFile = new File(path);
        Document document;
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = builder.parse(keysFile);
        document.getDocumentElement().normalize();

        String consumerKey = document.getElementsByTagName("consumer-key").item(0).getTextContent();
        String consumerSecret = document.getElementsByTagName("consumer-secret").item(0).getTextContent();
        String accessToken = document.getElementsByTagName("access-token").item(0).getTextContent();
        String accessSecret = document.getElementsByTagName("access-secret").item(0).getTextContent();

        return new Config(consumerKey, consumerSecret, accessToken, accessSecret);
    }
}
