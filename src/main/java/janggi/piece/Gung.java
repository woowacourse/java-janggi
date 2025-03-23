package janggi.piece;

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
        return isValidMove(destination) && allyPieces.isNotBlockedByAlly(destination);
    }

    private boolean isValidMove(JanggiPosition destination) {
        int currentX = getPosition().getX();
        int currentY = getPosition().getY();
        int destX = destination.getX();
        int destY = destination.getY();

        return Math.abs(currentX - destX) + Math.abs(currentY - destY) == 1;
    }

}