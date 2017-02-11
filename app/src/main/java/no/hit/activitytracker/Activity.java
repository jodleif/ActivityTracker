package no.hit.activitytracker;

/**
 * Created by Jo on 2/10/2017.
 */

public enum Activity {
    COFFEE,
    EMAIL,
    SNACK,
    MEETING,
    UNKNOWN;

    @Override
    public String toString() {
        switch (this) {
            case COFFEE:
                return "1";
            case EMAIL:
                return "2";
            case SNACK:
                return "3";
            case MEETING:
                return "4";
            case UNKNOWN:
                return Integer.toString(Integer.MAX_VALUE);
        }
        // this should be dead code.
        throw new IllegalArgumentException("ENUM:Activity - Memory corruption?");
    }
}
