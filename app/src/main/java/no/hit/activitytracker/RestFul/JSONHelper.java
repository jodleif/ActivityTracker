package no.hit.activitytracker.RestFul;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import no.hit.activitytracker.Events.ActivityEvent;

/**
 * Created by Jo on 2/12/2017.
 *
 * Create events to submit to the endpoint
 * TODO: Add username / password handling, just using a test-user for now.
 */

public class JSONHelper {

    // TODO: Implement proper username / password management
    // (encrypt on phone)
    private static final String userName = "test-user@test.com";
    private static final String password = "testpassword123";
    public static JSONObject createUser() {
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("user", userName);
            json.put("pw", password);
            json.put("type", 0);
            return json;
        } catch (Exception e) {

        }
        return json;
    }

    public static JSONObject prepareEventsForSubmit(ArrayList<ActivityEvent> pendingEvents) {
        JSONObject json = null;
        JSONArray jsonArray = null;
        ArrayList<JSONObject> objects = new ArrayList<>();
        for (ActivityEvent ev : pendingEvents) objects.add(ev.toJSON());
        try {
            jsonArray = new JSONArray(objects);
            json = new JSONObject();
            json.put("user", userName);
            json.put("pw", password);
            json.put("type", 1);
            json.put("events", jsonArray);
            return json;
        } catch (Exception e) {

        }
        return json;
    }
}
