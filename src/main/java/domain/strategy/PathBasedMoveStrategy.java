package domain.strategy;

import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class PathBasedMoveStrategy implements MoveStrategy {

    private final List<List<Direction>> paths;

    public PathBasedMoveStrategy(List<List<Direction>> paths) {
        this.paths = paths;
    }

    @Override
    public List<Position> generate(Board board, Position start, Piece piece) {
        List<Position> result = new ArrayList<>();

        for (List<Direction> path : paths) {
            Position current = start;

            for (Direction dir : path) {
                current = current.nextPosition(dir);
            }

            result.add(current);
        }

        return result;
    }
}
