package domain.piece;

import domain.JanggiBoard;
import domain.JanggiCoordinate;

public interface CastlePieceMove {
    default void validateCastlePieceMove(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to) {
        if (!janggiBoard.isCastleCoordinate(to)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 궁성 내부를 나갈 수 없습니다.");
        }
    }
}
