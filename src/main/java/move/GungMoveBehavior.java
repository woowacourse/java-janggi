package move;

import java.util.List;
import piece.PieceType;
import piece.Pieces;
import piece.Team;
import piece.position.JanggiPosition;

public class GungMoveBehavior extends JanggiMoveBehavior {


    @Override
    public List<JanggiPosition> calculateLegalRoute(JanggiPosition startPosition, JanggiPosition endPosition,
                                                    Team team) {
        if (!isInsideGungsungCase(startPosition, endPosition)) {
            throw new InvalidMovePosition();
        }
        return null;
    }

    @Override
    public JanggiPosition move(JanggiPosition destination, Pieces onRoutePieces, Team moveTeam) {
        return null;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.GUNG;
    }
}
