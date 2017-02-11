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
    public static final String MY_SCREEN_NAME = "WaifuShitBot";

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
                .prepareGet("https://userstream.twitter.com/1.1/user.json")
                .setSignatureCalculator(calc);
        Gson gson = new Gson();

        TwitterUtils.streamingRequestToObservable(request)
                .filter(s -> WaifuUtils.isStatus(s))
                .map(s -> gson.fromJson(s, Status.class))
                .filter(s -> !s.user.screenName.equals(MY_SCREEN_NAME) || s.replyStatusIdString == null) // filters out the bot's own reply tweets.
                .flatMap(status -> {
                    if (WaifuUtils.isWaifuStatus(status)) {
                        return status.reply(client, calc, "your waifu is shit! ( >д<)");
                    }
                    return status.reply(client, calc, "Please tweet me something about your waifu (´～`).");
                })
                .subscribe(
                        status -> System.out.println("Replied to sender!"),
                        err -> System.err.println("Error: " + err)
                );
    }
}
