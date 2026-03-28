package domain.piece.strategy;

import domain.board.PathChecker;
import domain.board.Position;
import domain.piece.Piece;

import java.util.List;

public class ElephantStrategy implements MoveStrategy {

    private static final String ELEPHANT_CANNOT_MOVE_ERROR_MESSAGE = "[ERROR] 멱이 있어 이동할 수 없습니다.";

    @Override
    public void move(Position from, Position to, PathChecker checker) {
        int dx = from.calculateDx(to);
        int dy = from.calculateDy(to);

        ElephantDirection direction = ElephantDirection.from(dx, dy);
        List<Position> path = direction.findPath(from);
        List<Piece> piecesInPath = checker.findPiecesInPath(path);

        if (!piecesInPath.isEmpty()) {
            throw new IllegalArgumentException(ELEPHANT_CANNOT_MOVE_ERROR_MESSAGE);
        }
    }
}
