package domain.piece.strategy;

import domain.board.BoardChecker;
import domain.board.Position;
import domain.piece.Piece;
import java.util.List;

public class ChariotStrategy implements MoveStrategy {
    @Override
    public void validateMove(Position from, Position to, BoardChecker checker) {
        List<Position> path = checker.findMovePath(from, to)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 차의 이동 방향이 올바르지 않습니다."));

        List<Piece> piecesInPath = checker.findPiecesInPath(path);

        if (!piecesInPath.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 차 이동 경로 상에 기물이 존재하여 움직일 수 없습니다.");
        }
    }
}
