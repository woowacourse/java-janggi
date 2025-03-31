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

    public boolean isInside() {
        return this == INSIDE;
    }

    public boolean isOutside() {
        return this == OUTSIDE;
    }
}
