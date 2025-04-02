package domain.position;

import domain.direction.Direction;
import domain.direction.Vector;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Palace {

    public static final int MIN_ROW = 4;
    public static final int MAX_ROW = 6;

    public static final int HAN_MIN_COLUMN = 1;
    public static final int HAN_MAX_COLUMN = 3;
    public static final int CHO_MIN_COLUMN = 8;
    public static final int CHO_MAX_COLUMN = 10;

    public static Set<Direction> getMovableDirectionInPalace(final Position position) {
        int row = position.getRow();
        int column = position.getColumn();
        if (row == MAX_ROW && (column == HAN_MIN_COLUMN || column == CHO_MIN_COLUMN)) {
            return Set.of(new Direction(List.of(Vector.DOWN_LEFT)));
        }
        if (row == MIN_ROW && (column == HAN_MIN_COLUMN || column == CHO_MIN_COLUMN)) {
            return Set.of(new Direction(List.of(Vector.DOWN_RIGHT)));
        }
        if (row == MAX_ROW && (column == HAN_MAX_COLUMN || column == CHO_MAX_COLUMN)) {
            return Set.of(new Direction(List.of(Vector.UP_LEFT)));
        }
        if (row == MIN_ROW && (column == HAN_MAX_COLUMN || column == CHO_MAX_COLUMN)) {
            return Set.of(new Direction(List.of(Vector.UP_RIGHT)));
        }
        if (row == (MAX_ROW - 1) && (column == (HAN_MAX_COLUMN - 1) || column == (CHO_MAX_COLUMN - 1))) {
            return Vector.getDiagonals().stream()
                    .map(vector -> new Direction(List.of(vector)))
                    .collect(Collectors.toSet());
        }
        return new HashSet<>(); // 이 중에서도 없으면 아무것도 추가 안해도 됨
    }

    public static boolean isInPalace(final Position position) {
        int row = position.getRow();
        int column = position.getColumn();
        return (Palace.MIN_ROW <= row && row <= Palace.MAX_ROW) &&
                ((Palace.HAN_MIN_COLUMN <= column && column <= Palace.HAN_MAX_COLUMN) ||
                        (Palace.CHO_MIN_COLUMN <= column && column <= Palace.CHO_MAX_COLUMN));
    }
}
