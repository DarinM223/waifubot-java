package waifushit;

import twitter.Status;
import twitter.User;

import static org.junit.Assert.*;

public class WaifuUtilsTest {
    @org.junit.Test
    public void isWaifuStatus() throws Exception {
        assertEquals(WaifuUtils.isWaifuStatus(new Status("", 0, "Rem is my waifu <3", new User(0, "", ""))), true);
        assertEquals(WaifuUtils.isWaifuStatus(new Status("", 0, "Emilia is mai waifu <3", new User(0, "", ""))), true);
        assertEquals(WaifuUtils.isWaifuStatus(new Status("", 0, "Megumin is best waifu <3", new User(0, "", ""))), true);
        assertEquals(WaifuUtils.isWaifuStatus(new Status("", 0, "Megumin is your waifu :(", new User(0, "", ""))), false);
        assertEquals(WaifuUtils.isWaifuStatus(new Status("", 0, "Aqua is trash waifu", new User(0, "", ""))), false);
        assertEquals(WaifuUtils.isWaifuStatus(new Status("", 0, "Who is your favorite waifu?", new User(0, "", ""))), false);
    }
}