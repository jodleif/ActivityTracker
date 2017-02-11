package no.hit.activitytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Tracker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        Bundle b = (DebugDialog.createDialogBundle("Hello world", "Y", "N"));
        DebugDialog dialogFrag = new DebugDialog();
        dialogFrag.setArguments(b);
        dialogFrag.show(getFragmentManager(), "dialog");
    }
}
