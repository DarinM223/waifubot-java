package waifushit;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import twitter.Status;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class WaifuUtils {
    private static final Set<String> describeWords;
    private static final Set<String> waifuWords;
    static {
        describeWords = new HashSet<>();
        describeWords.add("my");
        describeWords.add("mai");
        describeWords.add("best");

        waifuWords = new HashSet<>();
        waifuWords.add("girl");
        waifuWords.add("waifu");
    }

    public static boolean isStatus(String text) {
        if (text.trim().isEmpty()) {
            return false;
        }

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(text);

        return element.getAsJsonObject().get("friends") == null
                && element.getAsJsonObject().get("event") == null
                && element.getAsJsonObject().get("delete") == null;
    }

    public static boolean isWaifuStatus(Status s) {
        requireNonNull(s);

        if (s.text == null || s.text.trim().isEmpty()) {
            return false;
        }

        String status = s.text.toLowerCase();

        boolean describeMatch = false;
        for (String key : describeWords) {
            if (status.contains(key)) {
                describeMatch = true;
                break;
            }
        }

        boolean waifuMatch = false;
        for (String key : waifuWords) {
            if (status.contains(key)) {
                waifuMatch = true;
                break;
            }
        }

        return describeMatch && waifuMatch;
    }
}
