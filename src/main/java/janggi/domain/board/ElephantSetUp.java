package janggi.domain.board;

import static janggi.domain.piece.PieceStrategy.ELEPHANT;
import static janggi.domain.piece.PieceStrategy.HORSE;

import janggi.domain.piece.PieceStrategy;
import java.util.List;

public enum ElephantSetUp {

    LEFT_ELEPHANT(List.of(ELEPHANT, HORSE, ELEPHANT, HORSE)),
    RIGHT_ELEPHANT(List.of(HORSE, ELEPHANT, HORSE, ELEPHANT)),
    INNER_ELEPHANT(List.of(HORSE, ELEPHANT, ELEPHANT, HORSE)),
    OUTER_ELEPHANT(List.of(ELEPHANT, HORSE, HORSE, ELEPHANT));

    private final List<PieceStrategy> pieceStrategies;

    ElephantSetUp(List<PieceStrategy> pieceStrategies) {
        this.pieceStrategies = pieceStrategies;
    }

    public List<PieceStrategy> getPieceRules() {
        return pieceStrategies;
    }
}
