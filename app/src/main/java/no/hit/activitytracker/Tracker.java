package no.hit.activitytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import no.hit.activitytracker.GUIHelpers.SimpleDialogs;

public class Tracker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
    }

    public void onCoffee(View view) {
        SimpleDialogs.showDialog(getFragmentManager(), SimpleDialogs.createDialogBundle("On Coffee", "Y", "N"));
    }

    public void onEmail(View view) {
        SimpleDialogs.showDialog(getFragmentManager(), SimpleDialogs.createDialogBundle("On Email", "Y", "N"));
    }

    public void onSnack(View view) {
        SimpleDialogs.showDialog(getFragmentManager(), SimpleDialogs.createDialogBundle("On Snack", "Y", "N"));
    }

    public void onMeeting(View view) {
        SimpleDialogs.showDialog(getFragmentManager(), SimpleDialogs.createDialogBundle("On Meeting", "Y", "N"));
    }
}
