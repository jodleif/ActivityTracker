package no.hit.activitytracker.FunctionalUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Jo on 2/11/2017.
 */

public class Utils {

    public static <T> void removeIf(Pred<T> predicate, Collection<T> coll) {
        ArrayList<T> toRemove = new ArrayList<>();
        for (T e : coll) {
            if (predicate.apply(e)) {
                toRemove.add(e);
            }
        }
        coll.removeAll(toRemove);
    }

}
