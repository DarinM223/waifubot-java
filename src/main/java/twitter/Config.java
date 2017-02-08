package twitter;

import static java.util.Objects.requireNonNull;

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
}
