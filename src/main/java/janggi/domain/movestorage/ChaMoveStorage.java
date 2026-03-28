package janggi.domain.movestorage;

import janggi.domain.BoardState;
import janggi.domain.Column;
import janggi.domain.Position;
import janggi.domain.Row;

import java.util.List;

public class ChaMoveStorage implements MoveStorage {

    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        List<Integer> fromPosition = from.getPosition();
        List<Integer> toPosition = to.getPosition();

        int fromX = fromPosition.getFirst();
        int fromY = fromPosition.getLast();
        int toX = toPosition.getFirst();
        int toY = toPosition.getLast();

        if (fromX != toX && fromY != toY) {
            return false;
        }

        if (fromX == toX) {
            int start = Math.min(fromY, toY) + 1;
            int end = Math.max(fromY, toY);

            for (int i = start; i < end; i++) {
                Position position = Position.of(Row.of(fromX), Column.of(i));
                if (boardState.hasPieceAt(position)) {
                    return false;
                }
            }
        }

        if (fromY == toY) {
            int start = Math.min(fromX, toX) + 1;
            int end = Math.max(fromX, toX);

            for (int i = start; i < end; i++) {
                Position position = Position.of(Row.of(i), Column.of(fromY));
                if (boardState.hasPieceAt(position)) {
                    return false;
                }
            }
        }
        return true;
    }
}
