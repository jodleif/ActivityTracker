package no.hit.activitytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;

import no.hit.activitytracker.EventQueue.EventQueue;
import no.hit.activitytracker.Events.Activity;
import no.hit.activitytracker.Events.ActivityEvent;
import no.hit.activitytracker.GUIHelpers.SimpleDialogs;

public class Tracker extends AppCompatActivity {

    private final EventQueue eventQueue = new EventQueue();
    private final static String userId = "TEMPLATE_USER_ID";
    private ArrayList<ActivityEvent> currentView = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this); // must be done to use joda time
        setContentView(R.layout.activity_tracker);
    }

    private void refreshViewBasedOnState() {
        currentView = eventQueue.getCurrentView();
    }

    public void onCoffee(View view) {
        SimpleDialogs.showDialog(getFragmentManager(), SimpleDialogs.createDialogBundle("On Coffee", "Y", "N"));
        eventQueue.push(new ActivityEvent(Activity.COFFEE, userId));
    }

    public void onEmail(View view) {
        SimpleDialogs.showDialog(getFragmentManager(), SimpleDialogs.createDialogBundle("On Email", "Y", "N"));
        eventQueue.push(new ActivityEvent(Activity.COFFEE, userId));
    }

    public void onSnack(View view) {
        SimpleDialogs.showDialog(getFragmentManager(), SimpleDialogs.createDialogBundle("On Snack", "Y", "N"));
        eventQueue.push(new ActivityEvent(Activity.COFFEE, userId));
    }

    public void onMeeting(View view) {
        SimpleDialogs.showDialog(getFragmentManager(), SimpleDialogs.createDialogBundle("On Meeting", "Y", "N"));
        eventQueue.push(new ActivityEvent(Activity.COFFEE, userId));
    }
}
