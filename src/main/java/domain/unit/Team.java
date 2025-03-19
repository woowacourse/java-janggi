package domain.unit;

public enum Team {
    HAN,
    CHO,
    ;

    public boolean isFront(Point before, Point after) {
        if (this == HAN) {
            return before.getY() < after.getY();
        }
        return before.getY() > after.getY();
    }
}
