package janggi.domain.piece;

import janggi.domain.piece.direction.FourDirection;
import janggi.domain.piece.direction.GungDirection;
import janggi.domain.setting.CampType;
import janggi.domain.value.JanggiPosition;
import java.util.List;

public class Gung extends Piece {

    private Gung(final JanggiPosition janggiPosition) {
        super(PieceType.GUNG, janggiPosition);
    }

    public static Gung from(final JanggiPosition janggiPosition) {
        return new Gung(janggiPosition);
    }

    public static List<Gung> generateInitialGung(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.GUNG.getHeight());
        return PieceType.GUNG.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Gung(new JanggiPosition(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Gung move(final JanggiPosition destination, final Pieces enemyPieces, final Pieces allyPieces) {
        if (!ableToMove(destination, enemyPieces, allyPieces)) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        enemyPieces.beAttackedAt(destination);
        return new Gung(destination);
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
