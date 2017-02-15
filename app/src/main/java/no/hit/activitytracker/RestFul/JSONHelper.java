package no.hit.activitytracker.RestFul;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import no.hit.activitytracker.Events.ActivityEvent;

/**
 * Created by Jo on 2/12/2017.
 *
 * Create events to submit to the endpoint
 * TODO: Add username / password, just using a test-user for now.
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

    public static JSONObject submitEvents(ArrayList<ActivityEvent> pendingEvents) {
        JSONObject json = null;
        JSONArray jsonArray = null;
        ArrayList<JSONObject> objects = new ArrayList<>();
        for (ActivityEvent ev : pendingEvents) objects.add(ev.toJSON());
        try {
            jsonArray = new JSONArray(objects);
            json = new JSONObject();
            json.put("user", "test@test.com");
            json.put("pw", "123123123");
            json.put("type", 1);
            json.put("events", jsonArray);
            return json;
        } catch (Exception e) {

        }
        return json;
    }
}
