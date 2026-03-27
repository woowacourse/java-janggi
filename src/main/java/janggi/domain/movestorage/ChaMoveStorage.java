package janggi.domain.movestorage;

import janggi.domain.BoardState;
import janggi.domain.Column;
import janggi.domain.Position;
import janggi.domain.Row;

import java.util.List;

public class ChaMoveStorage implements MoveStorage{
    // TODO: if문을 어떻게 줄일까? (2026. 3. 27.)
    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        List<Integer> fromPosition = from.getPosition();
        List<Integer> toPosition = to.getPosition();

        int fromX = fromPosition.getFirst();
        int fromY = fromPosition.getLast();

        int toX = toPosition.getFirst();
        int toY = toPosition.getLast();

        if (fromX == toX) {
            if (fromY < toY) {
                int min = fromY;
                int max = toY;
                for (int i = min + 1; i < max; i++) {
                    Position position = Position.of(Row.of(fromX), Column.of(i));
                    if (boardState.hasPieceAt(position)) {
                        return false;
                    }
                }
            }
            if (fromY > toY) {
                int min = toY;
                int max = fromY;
                for (int i = min + 1; i < max; i++) {
                    Position position = Position.of(Row.of(fromX), Column.of(i));
                    if (boardState.hasPieceAt(position)) {
                        return false;
                    }
                }
            }
        }

        if (fromY == toY) {
            if (fromX < toX) {
                int min = fromX;
                int max = toX;
                for (int i = min + 1; i < max; i++) {
                    Position position = Position.of(Row.of(i), Column.of(fromY));
                    if (boardState.hasPieceAt(position)) {
                        return false;
                    }
                }
            }
            if (fromX > toX) {
                int min = toX;
                int max = fromX;
                for (int i = min + 1; i < max; i++) {
                    Position position = Position.of(Row.of(i), Column.of(fromY));
                    if (boardState.hasPieceAt(position)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
