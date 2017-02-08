package waifushit;

import com.google.gson.Gson;
import org.asynchttpclient.*;
import org.asynchttpclient.oauth.ConsumerKey;
import org.asynchttpclient.oauth.OAuthSignatureCalculator;
import org.asynchttpclient.oauth.RequestToken;
import twitter.Config;
import twitter.ConfigParser;
import twitter.Status;
import twitter.TwitterUtils;

public class Main {
    public static void main(String[] args) {
        if (args.length <= 0) {
            System.err.println("You need to add a path to the token configuration file");
            return;
        }

        String keysPath = args[0];
        Config keysConfig;
        try {
            keysConfig = ConfigParser.parse(keysPath);
        } catch (Exception e) {
            System.err.println("Error parsing the configuration file");
            return;
        }

        AsyncHttpClientConfig config = new DefaultAsyncHttpClientConfig.Builder()
                .setRequestTimeout(-1)
                .build();
        AsyncHttpClient client = new DefaultAsyncHttpClient(config);
        ConsumerKey key = new ConsumerKey(keysConfig.consumerKey, keysConfig.consumerSecret);
        RequestToken token = new RequestToken(keysConfig.accessToken, keysConfig.accessSecret);
        OAuthSignatureCalculator calc = new OAuthSignatureCalculator(key, token);
        BoundRequestBuilder request = client
                .preparePost("https://stream.twitter.com/1.1/statuses/filter.json?track=trump")
                .setSignatureCalculator(calc);
        Gson gson = new Gson();

        TwitterUtils.streamingRequestToObservable(request)
                .filter(s -> !s.trim().isEmpty())
                .map(s -> gson.fromJson(s, Status.class))
                .filter(s -> s.text != null && !s.text.trim().isEmpty())
                .subscribe(
                        status -> System.out.println(status),
                        err -> System.err.println("Error: " + err)
                );
    }
}
