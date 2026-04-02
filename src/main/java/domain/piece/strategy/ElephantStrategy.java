package domain.piece.strategy;

import domain.board.PathChecker;
import domain.board.Position;
import domain.piece.Piece;

import java.util.List;

public class ElephantStrategy extends JumpStrategy {

    private static final String ELEPHANT_CANNOT_MOVE_ERROR_MESSAGE = "[ERROR] 멱이 있어 이동할 수 없습니다.";

    @Override
    public void move(Position from, Position to, PathChecker checker) {
        Delta delta = from.calculateDelta(to);
        List<Position> path = findPath(delta, from);
        List<Piece> piecesInPath = checker.findPiecesInPath(path);

        if (!piecesInPath.isEmpty()) {
            throw new IllegalArgumentException(ELEPHANT_CANNOT_MOVE_ERROR_MESSAGE);
        }
    }

    @Override
    protected List<Position> findPath(Delta delta, Position from) {
        ElephantDirection elephantDirection = ElephantDirection.from(delta);
        return elephantDirection.findPath(from);
    }
}
