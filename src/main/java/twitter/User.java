package twitter;

import com.google.gson.annotations.SerializedName;

import static java.util.Objects.requireNonNull;

public class User {
    @SerializedName("id") public final long id;
    @SerializedName("name") public final String name;
    @SerializedName("screen_name") public final String screenName;

    public User(long id, String name, String screenName) {
        this.id = id;
        this.name = requireNonNull(name);
        this.screenName = screenName;
    }

    @Override
    public String toString() {
        return "User { id: " + id + ", name: " + screenName + " }";
    }
}
