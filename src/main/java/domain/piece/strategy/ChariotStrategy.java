package domain.piece.strategy;

import domain.board.BoardChecker;
import domain.board.Position;
import domain.piece.Piece;

import java.util.List;

public class ChariotStrategy implements MoveStrategy {

    public static final String CHARIOT_CANNOT_MOVE_ERROR_MESSAGE = "[ERROR] 차 이동 경로 상에 기물이 존재하여 움직일 수 없습니다.";

    @Override
    public void move(Position from, Position to, BoardChecker checker) {
        List<Position> path = from.findPath(to);
        List<Piece> piecesInPath = checker.findPiecesInPath(path);

        if (!piecesInPath.isEmpty()) {
            throw new IllegalArgumentException(CHARIOT_CANNOT_MOVE_ERROR_MESSAGE);
        }
    }
}
