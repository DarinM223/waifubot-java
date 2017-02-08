package twitter;

import com.google.gson.annotations.SerializedName;
import static java.util.Objects.requireNonNull;

public class Status {
    @SerializedName("created_at") public final String createdAt;
    @SerializedName("id") public final long id;
    @SerializedName("text") public final String text;

    public Status(String createdAt, int id, String text) {
        this.createdAt = requireNonNull(createdAt);
        this.id = id;
        this.text = requireNonNull(text);
    }

    @Override
    public String toString() {
        return "Status: { id: " + this.id + ", text: " + this.text + "}";
    }
}
