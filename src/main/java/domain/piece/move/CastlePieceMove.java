package domain.piece.move;

import domain.Direction;
import domain.JanggiBoard;
import domain.JanggiCoordinate;

public interface CastlePieceMove {
    default void validateCastlePieceMove(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to) {
        if (from.isSameCol(to) || from.isSameRow(to)) {
            return;
        }

        Direction diagonalDirection = Direction.getDiagonalDirection(from, to);
        if (!janggiBoard.castleCoordinateHasDirection(from, diagonalDirection)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 해당 위치로 이동할 수 없습니다.");
        }
    }

    default void validateCastleCoordinate(JanggiBoard board, JanggiCoordinate to) {
        if (!board.isCastleCoordinate(to)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 궁성 내부를 나갈 수 없습니다.");
        }
    }
}
