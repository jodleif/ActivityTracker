package no.hit.activitytracker.FunctionalUtils;

/**
 * Created by Jo on 2/11/2017.
 *
 * This interface is used to describe a predicate. Simplified version of java 8's perdicate
 */
public interface Pred<T> {
    boolean apply(T t);
}
