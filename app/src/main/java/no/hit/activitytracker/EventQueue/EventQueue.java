package no.hit.activitytracker.EventQueue;

import android.support.annotation.NonNull;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;

import no.hit.activitytracker.Events.ActivityEvent;
import no.hit.activitytracker.Events.AggregatedEventView;
import no.hit.activitytracker.FunctionalUtils.Pred;
import no.hit.activitytracker.FunctionalUtils.Utils;

/**
 * Created by Jo on 2/11/2017.
 */

public class EventQueue implements Serializable {
    private final ArrayList<ActivityEvent> pendingEvents = new ArrayList<>();
    private final ArrayList<ActivityEvent> committedEvents = new ArrayList<>();

    public EventQueue() {

    }

    public void push(@NonNull ActivityEvent ev) {
        pendingEvents.add(ev);
    }

    /**
     * Remove committed events from yesterday.
     */
    private void cleanup() {
        NotSameDay predicate = new NotSameDay();
        Utils.removeIf(predicate, committedEvents);
    }

    /**
     * Unsafe! Moves events from pending to commited state
     */
    public void commit() {
        for (ActivityEvent e : pendingEvents) committedEvents.add(e);
        pendingEvents.clear();
        cleanup(); // Remove data from yesterday
    }

    /**
     * @return Returns events not yet committed
     */
    public ArrayList<ActivityEvent> getPendingEvents() {
        return pendingEvents;
    }

    /**
     * This is the current state of our activities.
     *
     * @return both committed and uncommitted events from today
     */
    public ArrayList<ActivityEvent> getCurrentView() {
        ArrayList<ActivityEvent> currentView = new ArrayList<>();
        currentView.addAll(pendingEvents);
        currentView.addAll(committedEvents);
        // If we have uncommitted events from yesterday, they are not a part of the view
        Utils.removeIf(new NotSameDay(), currentView);
        return currentView;
    }

    public AggregatedEventView getCurrentAggregatedView() {
        ArrayList<ActivityEvent> aggregate = getCurrentView();
        AggregatedEventView eventView = new AggregatedEventView();
        for (ActivityEvent e : aggregate) eventView.add(e.act);
        return eventView;
    }

    public boolean restore(EventQueue ev) {
        if (pendingEvents.size() != 0 && committedEvents.size() != 0) return false;
        this.pendingEvents.addAll(ev.pendingEvents);
        this.committedEvents.addAll(ev.committedEvents);
        cleanup();
        return true;
    }

    class NotSameDay implements Pred<ActivityEvent> {
        private final DateTime now;

        public NotSameDay() {
            this.now = DateTime.now();
        }

        @Override
        public boolean apply(ActivityEvent activityEvent) {
            DateTime ts = new DateTime(activityEvent.timestamp * 1_000); // Java time in ms
            int day = now.getDayOfMonth();
            int tsDay = ts.getDayOfMonth();
            return day != tsDay;
        }
    }


}
