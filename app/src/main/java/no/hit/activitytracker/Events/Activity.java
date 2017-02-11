package no.hit.activitytracker.Events;

import java.io.Serializable;

/**
 * Created by Jo on 2/10/2017.
 */

public enum Activity implements Serializable {
    COFFEE,
    EMAIL,
    SNACK,
    MEETING,
    CALL,
    UNKNOWN;

    @Override
    public String toString() {
        return Integer.toString(valueOf());
    }

    public int valueOf() {
        switch (this) {

            case COFFEE:
                return 1;
            case EMAIL:
                return 2;
            case SNACK:
                return 3;
            case MEETING:
                return 4;
            case CALL:
                return 5;
            case UNKNOWN:
                return 0;
        }
        // bad exception type but should be dead code and greppable
        throw new IllegalArgumentException("Activity: Invalid enum state");
    }
}
