package space.panasovsky.easytranslate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.io.IOException;


public class TranslateConnector {

    private static final FileHandler f = new FileHandler();
    private static final TranslateConnector t = new TranslateConnector();
    private static final Logger LOG = LoggerFactory.getLogger(RunApplication.class);

    protected static String getTranslatedString(final String text) {

        String result = "";
        try {
            result = f.parseResponse(t.translate(text));
        } catch (IOException | InterruptedException e) {
            LOG.error("ERROR: ", e);
        }
        return result;
    }

    private String translate(final String text) throws IOException, InterruptedException {

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://microsoft-translator-text.p.rapidapi.com/translate?api-version=3.0&to%5B0%5D=ru&suggestedFrom=en&textType=plain&profanityAction=NoAction"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Host", "microsoft-translator-text.p.rapidapi.com")
                .header("X-RapidAPI-Key", "185f850fe2msh03d7cedb7815596p1050dbjsne3ab833d8867")
                .method("POST", HttpRequest.BodyPublishers.ofString("[{\"Text\": \"" + text + "\"}]"))
                .build();

        final HttpResponse<String> response = HttpClient
                .newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

}