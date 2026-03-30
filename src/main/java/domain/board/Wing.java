package domain.board;

import domain.game.Side;
import domain.piece.Piece;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public abstract class Wing {

    private static final int WING_SIZE = 2;

    protected final Piece first;
    protected final Piece second;

    public Wing(List<Piece> pieces) {
        validatePieces(pieces);

        this.first = pieces.get(0);
        this.second = pieces.get(1);
    }

    private void validatePieces(List<Piece> pieces) {
        validateSize(pieces);
        validatePiecesUnique(pieces);
        validatePiecesEligibility(pieces);
    }

    private void validateSize(List<Piece> pieces) {
        if (pieces.size() != WING_SIZE) {
            throw new IllegalArgumentException("진의 기물 수는 " + WING_SIZE + "개여야 합니다(현재 기물 수: " + pieces.size() + "개).");
        }
    }

    private void validatePiecesEligibility(Collection<Piece> pieces) {
        boolean allLegalPieces = pieces.stream()
                .allMatch(Piece::canBelongToWing);

        if (!allLegalPieces) {
            throw new IllegalArgumentException("진에 소속될 수 없는 기물이 포함되어 있습니다. (현재 기물: " + pieces + ")");
        }
    }

    private void validatePiecesUnique(Collection<Piece> pieces) {
        HashSet<Piece> distinctPieces = new HashSet<>(pieces);

        if (distinctPieces.size() != pieces.size()) {
            throw new IllegalArgumentException("하나의 진에는 중복되지 않은 기물들만 포함될 수 있습니다. (현재 기물: " + pieces + ")");
        }
    }

    public abstract Map<Intersection, Piece> setUpPieces(Side side);
}
