package academy.io;

import static academy.utils.constants.Constants.SUCCESS_CODE;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpModule implements InputInterface {
    private static final Logger logger = LogManager.getLogger(HttpModule.class);

    @Override
    public List<String> getFile(String input) throws IOException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request =
                    HttpRequest.newBuilder().uri(URI.create(input)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != SUCCESS_CODE) {
                logger.error("HTTP error {} for URL: {}", response.statusCode(), input);
                throw new IOException("HTTP error " + response.statusCode());
            }
            return Arrays.asList(response.body().split("\\R"));
        } catch (Exception e) {
            logger.error("Failed to download from URL: {}", input, e);
            throw new IOException("Failed to download: " + input, e);
        }
    }

    @Override
    public List<String> getFileNames(String input) throws IOException {
        return List.of(input);
    }
}
