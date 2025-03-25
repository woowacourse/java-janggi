package janggi.piece;

import janggi.piece.direction.FourDirection;
import janggi.setting.CampType;
import janggi.value.JanggiPosition;
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
        return new Gung(destination);
    }

    @Override
    protected boolean ableToMove(JanggiPosition destination, Pieces enemyPieces, Pieces allyPieces) {
        List<JanggiPosition> pathPositions = FourDirection.from(destination, getPosition());
        return isValidMove(pathPositions) && allyPieces.isNotBlockedBy(destination);
    }

    private boolean isValidMove(List<JanggiPosition> pathPositions) {
        return pathPositions.size() == 1;
    }
}
