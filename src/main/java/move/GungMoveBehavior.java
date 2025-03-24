package move;

import piece.PieceType;
import piece.Pieces;
import piece.Position;
import piece.Route;
import piece.Team;

public class GungMoveBehavior extends MoveBehavior {

    private static final String NOT_IMPLEMENTED_FEATURE = "아직 구현되지 않은 기능입니다.";

    @Override
    public Route calculateLegalRoute(Position startPosition, Position endPosition, Team team) {
        throw new IllegalStateException(NOT_IMPLEMENTED_FEATURE);
    }

    @Override
    public Position move(Position destination, Pieces onRoutePieces, Team moveTeam) {
        throw new IllegalStateException(NOT_IMPLEMENTED_FEATURE);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.GUNG;
    }
}
