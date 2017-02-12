package no.hit.activitytracker.RestFul;

import org.json.JSONObject;

/**
 * Created by Jo on 2/12/2017.
 */

public class JSONHelper {
    public static JSONObject createUser() {
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("user", "test@test.com");
            json.put("pw", "123123123");
            json.put("type", 0);
            return json;
        } catch (Exception e) {

        }
        return json;
    }
}
