package domain;

import java.util.List;

import static domain.PieceType.ELEPHANT;
import static domain.PieceType.HORSE;

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
}
