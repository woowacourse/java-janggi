package domain.piece.strategy;

import domain.board.BoardChecker;
import domain.board.Position;
import domain.piece.Piece;

import java.util.List;

public class HorseStrategy implements MoveStrategy {
    @Override
    public void validateMove(Position from, Position to, BoardChecker checker) {
        int dx = from.calculateDx(to);
        int dy = from.calculateDy(to);

        HorseDirection direction = HorseDirection.from(dx, dy);
        List<Position> path = direction.findPath(from);
        List<Piece> piecesInPath = checker.findPiecesInPath(path);

        if (!piecesInPath.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 멱이 있어 이동할 수 없습니다.");
        }
    }
}
