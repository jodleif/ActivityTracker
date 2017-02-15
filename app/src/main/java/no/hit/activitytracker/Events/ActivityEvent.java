package no.hit.activitytracker.Events;


import android.support.annotation.NonNull;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Jo on 2/11/2017.
 *
 * This is class describes the events that the user registers in the app
 */

public class ActivityEvent implements Serializable {
    public final Activity act;
    public final long timestamp;
    final String userId;

    public ActivityEvent(@NonNull Activity act, @NonNull String userId) {
        this.act = act;
        this.userId = userId;
        this.timestamp = unixTimestamp();
    }

    private static long unixTimestamp() {
        return System.currentTimeMillis() / 1_000;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityEvent that = (ActivityEvent) o;

        if (timestamp != that.timestamp) return false;
        if (act != that.act) return false;
        return userId.equals(that.userId);

    }

    public JSONObject toJSON() {
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("act", act.valueOf()); // activity (format json-number)
            json.put("ts", String.valueOf(timestamp)); // timestamp (format string)
        } catch (Exception e) {
            // should never be here
            System.out.println(String.format("Exception in serializing activity event\n%s", e.getMessage()));
        }
        return json;
    }

    @Override
    public int hashCode() {
        int result = act.hashCode();
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + userId.hashCode();
        return result;
    }
}
