package janggi.domain.board;

import static janggi.domain.piece.PieceRule.ELEPHANT;
import static janggi.domain.piece.PieceRule.HORSE;

import janggi.domain.piece.PieceRule;
import java.util.List;

public enum ElephantSetUp {

    LEFT_ELEPHANT(List.of(ELEPHANT, HORSE, ELEPHANT, HORSE)),
    RIGHT_ELEPHANT(List.of(HORSE, ELEPHANT, HORSE, ELEPHANT)),
    INNER_ELEPHANT(List.of(HORSE, ELEPHANT, ELEPHANT, HORSE)),
    OUTER_ELEPHANT(List.of(ELEPHANT, HORSE, HORSE, ELEPHANT));

    private final List<PieceRule> pieceStrategies;

    ElephantSetUp(List<PieceRule> pieceStrategies) {
        this.pieceStrategies = pieceStrategies;
    }

    public List<PieceRule> getPieceRules() {
        return pieceStrategies;
    }
}
