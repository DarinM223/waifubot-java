package waifushit;

import twitter.Status;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class WaifuUtils {
    private static final Set<String> dict;
    static {
        dict = new HashSet<>();
        dict.add("my");
        dict.add("mai");
        dict.add("best");
    }

    public static boolean isWaifuStatus(Status s) {
        requireNonNull(s);

        if (s.text == null || s.text.trim().isEmpty()) {
            return false;
        }

        boolean oneMatch = false;
        for (String key : dict) {
            if (s.text.contains(key)) {
                oneMatch = true;
                break;
            }
        }

        return oneMatch;
    }
}
