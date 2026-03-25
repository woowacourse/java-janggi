package domain.movestrategy;

import domain.board.Board;
import domain.piece.Position;
import java.util.ArrayList;
import java.util.List;

public class CannonMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> calculateMovablePositions(final Position from) {
        List<Position> movable = new ArrayList<>();

        for (int newColumn = Board.MIN_COLUMN_RANGE; newColumn <= Board.MAX_COLUMN_RANGE; newColumn++) {
            if (newColumn == from.column()) {
                continue;
            }
            movable.add(Position.of(newColumn, from.row()));
        }

        for (int newRow = Board.MIN_ROW_RANGE; newRow <= Board.MAX_ROW_RANGE; newRow++) {
            if (newRow == from.row()) {
                continue;
            }
            movable.add(Position.of(from.column(), newRow));
        }

        return movable;
    }
}
