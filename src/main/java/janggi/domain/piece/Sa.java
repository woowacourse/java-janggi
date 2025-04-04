package janggi.domain.piece;

import janggi.domain.piece.direction.FourDirection;
import janggi.domain.piece.direction.GungDirection;
import janggi.domain.setting.CampType;
import janggi.domain.value.JanggiPosition;
import java.util.List;

public class Sa extends Piece {

    private Sa(final JanggiPosition janggiPosition) {
        super(PieceType.SA, janggiPosition);
    }

    public static Sa from(final JanggiPosition janggiPosition) {
        return new Sa(janggiPosition);
    }

    public static List<Sa> generateInitialSas(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.SA.getHeight());
        return PieceType.SA.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Sa(new JanggiPosition(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Sa move(final JanggiPosition destination, final Pieces enemyPieces, final Pieces allyPieces) {
        if (!ableToMove(destination, enemyPieces, allyPieces)) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        enemyPieces.beAttackedAt(destination);
        return new Sa(destination);
    }

    @Override
    protected boolean ableToMove(JanggiPosition destination, Pieces enemyPieces, Pieces allyPieces) {
        if (!destination.isPositionInCastle()) {
            return false;
        }
        if (destination.isDiagonalPositionInCastle()) {
            List<JanggiPosition> gungPathPositions = GungDirection.of(janggiPosition, destination);
            return isValidMove(gungPathPositions) && allyPieces.isNotBlockedBy(destination);
        }
        List<JanggiPosition> pathPositions = FourDirection.from(destination, janggiPosition);
        return isValidMove(pathPositions) && allyPieces.isNotBlockedBy(destination);
    }

    private boolean isValidMove(List<JanggiPosition> pathPositions) {
        return pathPositions.size() == 1;
    }
}
