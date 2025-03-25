package move;

import java.util.List;
import piece.PieceType;
import piece.Pieces;
import piece.Team;
import piece.position.JanggiPosition;

public class SaMoveBehavior extends MoveBehavior {

    private static final String NOT_IMPLEMENTED_FEATURE = "아직 구현되지 않은 기능입니다.";

    @Override
    public JanggiPosition move(JanggiPosition destination, Pieces onRoutePieces, Team moveTeam) {
        throw new IllegalStateException(NOT_IMPLEMENTED_FEATURE);
    }

    @Override
    public List<JanggiPosition> calculateLegalRoute(JanggiPosition startPosition, JanggiPosition endPosition, Team team) {
        throw new IllegalStateException(NOT_IMPLEMENTED_FEATURE);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.SA;
    }
}
