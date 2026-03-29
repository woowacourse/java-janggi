package domain.rule;

import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.List;

public class PathBasedBlockRule implements MoveRule {

    private final List<List<Direction>> paths;

    public PathBasedBlockRule(List<List<Direction>> paths) {
        this.paths = paths;
    }

    @Override
    public boolean isValid(Board board, Position start, Position dest, Piece piece) {
        for (List<Direction> path : paths) {
            Position current = start;

            for (int i = 0; i < path.size() - 1; i++) {
                current = current.nextPosition(path.get(i));

                if (!board.isEmpty(current)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }
}
