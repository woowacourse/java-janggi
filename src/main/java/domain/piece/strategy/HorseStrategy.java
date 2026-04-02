package domain.piece.strategy;

import domain.board.PathChecker;
import domain.board.Position;
import domain.piece.Piece;

import java.util.List;

public class HorseStrategy extends JumpStrategy {

    private static final String HORSE_CANNOT_MOVE_ERROR_MESSAGE = "[ERROR] 멱이 있어 이동할 수 없습니다.";

    @Override
    public void move(Position from, Position to, PathChecker checker) {
        Delta delta = from.calculateDelta(to);
        List<Position> path = findPath(delta, from);
        List<Piece> piecesInPath = checker.findPiecesInPath(path);

        if (!piecesInPath.isEmpty()) {
            throw new IllegalArgumentException(HORSE_CANNOT_MOVE_ERROR_MESSAGE);
        }
    }

    @Override
    protected List<Position> findPath(Delta delta, Position from) {
        HorseDirection horseDirection = HorseDirection.from(delta);
        return horseDirection.findPath(from);
    }
}
