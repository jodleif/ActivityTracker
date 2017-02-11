package no.hit.activitytracker.GUIHelpers;

import android.support.annotation.NonNull;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import no.hit.activitytracker.Events.Activity;

/**
 * Created by Jo on 2/11/2017.
 *
 * This class is a helper class to keep track of text-views that are related to specific activities.
 */

public class TaggedTextView {
    TextView tv;
    Activity activity;

    public TaggedTextView(@NonNull Activity activity, @NonNull TextView tv) {
        this.tv = tv;
        this.activity = activity;
    }

    public static HashMap<Activity, TextView> createMap(ArrayList<TaggedTextView> coll) {
        HashMap<Activity, TextView> view = new HashMap<>();
        for (TaggedTextView tv : coll) view.put(tv.activity, tv.tv);
        return view;
    }
}
