package twitter;

import com.google.gson.annotations.SerializedName;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.Response;
import org.asynchttpclient.extras.rxjava.AsyncHttpObservable;
import org.asynchttpclient.oauth.OAuthSignatureCalculator;
import rx.Observable;

import static java.util.Objects.requireNonNull;

public class Status {
    @SerializedName("created_at") public final String createdAt;
    @SerializedName("id") public final long id;
    @SerializedName("text") public final String text;
    @SerializedName("user") public final User user;
    @SerializedName("in_reply_to_status_id_str") public final String replyStatusIdString;

    public Status(String createdAt, int id, String text, User user, String replyStatusIdString) {
        this.createdAt = requireNonNull(createdAt);
        this.id = id;
        this.text = requireNonNull(text);
        this.user = requireNonNull(user);
        this.replyStatusIdString = replyStatusIdString;
    }

    public Observable<Response> reply(AsyncHttpClient client, OAuthSignatureCalculator oauth, String message) {
        String replyText = "@" + this.user.screenName + " " + message;

        BoundRequestBuilder request = client
                .preparePost("https://api.twitter.com/1.1/statuses/update.json")
                .addQueryParam("status", replyText)
                .addQueryParam("in_reply_to_status_id", (new Long(this.id)).toString())
                .setSignatureCalculator(oauth);

        return AsyncHttpObservable.toObservable(() -> request);
    }

    @Override
    public String toString() {
        return "Status: { id: " + this.id + ", user: " + this.user + ", text: " + this.text + " }";
    }
}
