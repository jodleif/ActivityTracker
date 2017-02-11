package no.hit.activitytracker.Events;


import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Jo on 2/11/2017.
 */

public class ActivityEvent implements Serializable {
    final Activity act;
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
}
