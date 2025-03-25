package janggi.palace;

public enum PalaceArea {
    INSIDE(true),
    OUTSIDE(false);

    PalaceArea(boolean ignoredValue) {
    }

    public static PalaceArea from(boolean value) {
        if (value) {
            return INSIDE;
        }
        return OUTSIDE;
    }
}
