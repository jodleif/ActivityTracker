package no.hit.activitytracker.GUIHelpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Jo on 2/10/2017.
 *
 * This is a class to display yes/no dialogboxes. Mostly for debugging purposes.
 */

public class DebugDialog extends DialogFragment {
    private String dialogMessage;
    private String yesText;
    private String noText;

    private void restoreState(Bundle state) {
        dialogMessage = (String) state.get("Message");
        if (dialogMessage == null || dialogMessage.length() == 0) {
            dialogMessage = "Default message";
        }
        yesText = (String) state.get("yesText");
        if (yesText == null || yesText.length() == 0) {
            yesText = "Yes";
        }
        noText = (String) state.get("noText");
        if (noText == null || noText.length() == 0) {
            noText = "Yes";
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle b = getArguments();
        restoreState(b);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(dialogMessage)
                .setPositiveButton(yesText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).setNegativeButton(noText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

}
