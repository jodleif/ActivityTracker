package no.hit.activitytracker.Events;

import no.hit.activitytracker.BuildConfig;

/**
 * Created by Jo on 2/11/2017.
 */

public class AggregatedEventView {
    int[] aggregatedView;

    public AggregatedEventView() {
        aggregatedView = new int[Activity.values().length];
    }

    public void add(Activity act) {
        if (BuildConfig.DEBUG && act.valueOf() >= aggregatedView.length) throw new AssertionError();
        aggregatedView[act.valueOf()]++;
    }

    public int get(Activity act) {
        if (BuildConfig.DEBUG && act.valueOf() >= aggregatedView.length) throw new AssertionError();
        return aggregatedView[act.valueOf()];
    }
}
