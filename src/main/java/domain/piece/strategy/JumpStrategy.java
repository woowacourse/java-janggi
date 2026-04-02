package domain.piece.strategy;

import domain.board.PathChecker;
import domain.board.Position;
import domain.piece.Piece;

import java.util.List;

public abstract class JumpStrategy implements MoveStrategy {

    @Override
    public void move(Position from, Position to, PathChecker checker) {
        Delta delta = from.calculateDelta(to);

        List<Position> path = findPath(delta, from);

        List<Piece> piecesInPath = checker.findPiecesInPath(path);

        if (!piecesInPath.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    protected abstract List<Position> findPath(Delta delta, Position from);

}
