package domain.board;

import domain.piece.PieceType;

import java.util.List;

import static domain.piece.PieceType.ELEPHANT;
import static domain.piece.PieceType.HORSE;

public enum BoardSetting {
    LEFT_ELEPHANT_SET_UP(List.of(ELEPHANT, HORSE, ELEPHANT, HORSE)),
    RIGHT_ELEPHANT_SET_UP(List.of(HORSE, ELEPHANT, HORSE, ELEPHANT)),
    OUTER_ELEPHANT_SET_UP(List.of(HORSE, ELEPHANT, ELEPHANT, HORSE)),
    INNER_ELEPHANT_SET_UP(List.of(ELEPHANT, HORSE, HORSE, ELEPHANT));

    private final List<PieceType> piecesArrangement;

    BoardSetting(List<PieceType> pieceTypes) {
        this.piecesArrangement = pieceTypes;
    }

    public List<PieceType> piecesArrangement() {
        return piecesArrangement;
    }

    public static BoardSetting from(String value) {
        return switch (value.trim()) {
            case "1", "왼상차림" -> LEFT_ELEPHANT_SET_UP;
            case "2", "오른상차림" -> RIGHT_ELEPHANT_SET_UP;
            case "3", "바깥상차림" -> OUTER_ELEPHANT_SET_UP;
            case "4", "안상차림" -> INNER_ELEPHANT_SET_UP;
            default -> throw new IllegalArgumentException("상차림은 1~4 또는 이름으로 입력해야 합니다.");
        };
    }
}
