package no.hit.activitytracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import net.danlew.android.joda.JodaTimeAndroid;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import no.hit.activitytracker.EventQueue.EventQueue;
import no.hit.activitytracker.Events.Activity;
import no.hit.activitytracker.Events.ActivityEvent;
import no.hit.activitytracker.Events.AggregatedEventView;
import no.hit.activitytracker.GUIHelpers.SimpleDialogs;
import no.hit.activitytracker.GUIHelpers.TaggedTextView;
import no.hit.activitytracker.RestFul.JSONHelper;
import no.hit.activitytracker.RestFul.JsonStringRequest;
import no.hit.activitytracker.RestFul.VolleySingleton;

/**
 * This class describes the main view / activity in the application
 */

public class Tracker extends AppCompatActivity {

    private final static String EVENTQUEUE_STATE_ID = "EventQueueState";
    private final EventQueue eventQueue = new EventQueue();
    private final static String userId = "TEMPLATE_USER_ID";
    private final HashMap<Activity, TextView> labelMapping = new HashMap<>();
    private AggregatedEventView eventView = new AggregatedEventView();
    private static final String ENDPOINT_URL = "https://rest.jooivind.com/commit";
    private VolleySingleton vs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.vs = VolleySingleton.getInstance(getApplicationContext());
        JodaTimeAndroid.init(this); // must be done to use joda time
        setContentView(R.layout.activity_tracker);
        refreshViewBasedOnState();
        mapTextViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try (FileOutputStream fos = getApplicationContext().openFileOutput(EVENTQUEUE_STATE_ID, Context.MODE_PRIVATE);
             ObjectOutputStream os = new ObjectOutputStream(fos)) {
            os.writeObject(eventQueue);
        } catch (Exception e) {
            SimpleDialogs.showDialog(getFragmentManager(),
                    SimpleDialogs.createDialogBundle(String.format("Error saving state, %s", e.getMessage()), "Y", "N"));
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        this.vs = VolleySingleton.getInstance(getApplicationContext());
        try (FileInputStream fis = getApplicationContext().openFileInput(EVENTQUEUE_STATE_ID);
             ObjectInputStream is = new ObjectInputStream(fis)) {
            eventQueue.restore((EventQueue) is.readObject());
        } catch (Exception e) {
            /*SimpleDialogs.showDialog(getFragmentManager(),
                    SimpleDialogs.createDialogBundle(String.format("Error loading state, %s", e.getMessage()), "Y", "N"));*/
        }
        refreshViewBasedOnState();
    }

    private void mapTextViews() {
        ArrayList<TaggedTextView> tvs = new ArrayList<>();
        tvs.add(new TaggedTextView(Activity.COFFEE, (TextView) findViewById(R.id.coffeeTimes)));
        tvs.add(new TaggedTextView(Activity.EMAIL, (TextView) findViewById(R.id.mailTimes)));
        tvs.add(new TaggedTextView(Activity.SNACK, (TextView) findViewById(R.id.snackTimes)));
        tvs.add(new TaggedTextView(Activity.MEETING, (TextView) findViewById(R.id.meetTimes)));
        tvs.add(new TaggedTextView(Activity.CALL, (TextView) findViewById(R.id.callTimes)));
        labelMapping.clear();
        labelMapping.putAll(TaggedTextView.createMap(tvs));
    }

    private void refreshViewBasedOnState() {
        eventView = eventQueue.getCurrentAggregatedView();
        for (Map.Entry<Activity, TextView> e : labelMapping.entrySet()) {
            int val = eventView.get(e.getKey());
            e.getValue().setText(Integer.toString(val));
        }
    }

    public void onCoffee(View view) {
        eventQueue.push(new ActivityEvent(Activity.COFFEE, userId));
        refreshViewBasedOnState();
    }

    public void onEmail(View view) {
        eventQueue.push(new ActivityEvent(Activity.EMAIL, userId));
        refreshViewBasedOnState();
    }

    public void onSnack(View view) {
        eventQueue.push(new ActivityEvent(Activity.SNACK, userId));
        refreshViewBasedOnState();
    }

    public void onMeeting(View view) {
        eventQueue.push(new ActivityEvent(Activity.MEETING, userId));
        refreshViewBasedOnState();
    }

    public void onCall(View view) {
        eventQueue.push(new ActivityEvent(Activity.CALL, userId));
        refreshViewBasedOnState();
    }

    public void onCreateUser(View view) throws Exception {
        if (eventQueue.getPendingEvents().size() > 0) {
            JSONObject postEvents = JSONHelper.submitEvents(eventQueue.getPendingEvents());
            JsonStringRequest jsonRequest = new JsonStringRequest(Request.Method.POST,
                    ENDPOINT_URL, postEvents.toString(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // If success - move events from pending to committed
                    eventQueue.commit();
                    System.out.println(String.format("Events successfully committed\n %s", response));
                    SimpleDialogs.createDialogBundle(String.format("Events successfully committed\n %s", response), "Yes", "No");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                    SimpleDialogs.createDialogBundle(error.getMessage(), "Yes", "No");
                }
            });
            vs.addToRequestQueue(jsonRequest);
        }
    }
}
