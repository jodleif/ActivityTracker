package no.hit.activitytracker.GUIHelpers;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by Jo on 2/11/2017.
 */

public class SimpleDialogs {

    public static Bundle createDialogBundle(String dialogMessage, String yesText, String noText) {
        Bundle b = new Bundle(3);
        b.putString("Message", dialogMessage);
        b.putString("yesText", yesText);
        b.putString("noText", noText);
        return b;
    }

    public static Bundle createDefaultBundle() {
        return createDialogBundle("Default message", "Yes", "No");
    }

    public static void showDialog(@NonNull FragmentManager fm, Bundle dialogOpts) {
        DebugDialog fragment = new DebugDialog();
        if (dialogOpts == null) {
            dialogOpts = createDefaultBundle();
        }
        fragment.setArguments(dialogOpts);
        fragment.show(fm, UUID.randomUUID().toString());
    }
}
