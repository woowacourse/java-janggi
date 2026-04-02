package model.board;

import java.util.List;
import java.util.Map;
import model.position.Position;

public class Palace {
    private static final int HAN_MIN_ROW = 1;
    private static final int HAN_MAX_ROW = 3;
    private static final int CHO_MIN_ROW = 8;
    private static final int CHO_MAX_ROW = 10;
    private static final int MIN_COL = 4;
    private static final int MAX_COL = 6;
    private static final Map<Position, List<Position>> PALACE = Map.of(
            pos(1, 4), List.of(pos(2, 5), pos(3, 6)),
            pos(1, 6), List.of(pos(2, 5), pos(3, 4)),

            pos(3, 4), List.of(pos(2, 5), pos(1, 6)),
            pos(3, 6), List.of(pos(2, 5), pos(1, 4)),

            pos(2, 5), List.of(
                    pos(1, 4), pos(1, 6),
                    pos(3, 4), pos(3, 6)
            ),

            pos(8, 4), List.of(pos(9, 5), pos(10, 6)),
            pos(8, 6), List.of(pos(9, 5), pos(10, 4)),

            pos(10, 4), List.of(pos(9, 5), pos(8, 6)),
            pos(10, 6), List.of(pos(9, 5), pos(8, 4)),

            pos(9, 5), List.of(
                    pos(8, 4), pos(8, 6),
                    pos(10, 4), pos(10, 6)
            )
    );

    public static boolean isPalaceBound(Position pos) {
        if (pos.column().value() < MIN_COL || pos.column().value() > MAX_COL) {
            return false;
        }
        return ((pos.row().value() >= HAN_MIN_ROW && pos.row().value() <= HAN_MAX_ROW)
                || (pos.row().value() >= CHO_MIN_ROW && pos.row().value() <= CHO_MAX_ROW));
    }

    public static boolean canMove(Position from, Position to) {
        List<Position> diagonal = PALACE.get(from);
        if (diagonal == null) {
            return false;
        }
        return diagonal.contains(to);
    }

    private static Position pos(int x, int y) {
        return Position.of(x, y);
    }
}
