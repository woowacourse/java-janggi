package domain.rule;

import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.List;

public class MiddlePathBlockRule implements MoveRule {

    private final List<List<Direction>> paths;

    public MiddlePathBlockRule(List<List<Direction>> paths) {
        this.paths = paths;
    }

    @Override
    public boolean isValid(Board board, Position start, Position dest, Piece piece) {
        for (List<Direction> path : paths) {
            Position current = start;
            boolean blocked = false;

            for (int i = 0; i < path.size() - 1; i++) {
                current = current.nextPosition(path.get(i));

                if (!board.isEmpty(current)) {
                    blocked = true;
                    break;
                }
            }

            if (!blocked && current.nextPosition(path.getLast()).equals(dest)) {
                return true;
            }
        }

        return false;
    }
}
