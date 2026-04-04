package movepolicy.rule;

import position.Column;
import position.Position;
import position.Row;

public class Gunsung {

    private static final Row CHO_MIN_ROW = new Row(0);
    private static final Row CHO_MAX_ROW = new Row(2);
    private static final Column CHO_MIN_COLUMN = new Column(3);
    private static final Column CHO_MAX_COLUMN = new Column(5);

    public boolean isChoRange(Position position) {
        boolean isRowInRange = position.isRowInRange(CHO_MIN_ROW, CHO_MAX_ROW);
        boolean isColumnInRange = position.isColumnInRange(CHO_MIN_COLUMN, CHO_MAX_COLUMN);
        return isRowInRange && isColumnInRange;
    }

    public boolean isHanRange(Position position) {
        Position reversedPosition = position.reverse();
        boolean isRowInRange = reversedPosition.isRowInRange(CHO_MIN_ROW, CHO_MAX_ROW);
        boolean isColumnInRange = reversedPosition.isColumnInRange(CHO_MIN_COLUMN, CHO_MAX_COLUMN);
        return isRowInRange && isColumnInRange;
    }
}
