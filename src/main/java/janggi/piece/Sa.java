package janggi.piece;

import janggi.setting.CampType;
import janggi.value.JanggiPosition;
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
    public Sa move(final JanggiPosition destination, final List<Piece> enemyPieces, final List<Piece> allyPieces) {
        if (!ableToMove(destination, enemyPieces, allyPieces)) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Sa(destination);
    }

    @Override
    protected boolean ableToMove(JanggiPosition destination, List<Piece> enemyPieces, List<Piece> allyPieces) {
        return isValidMove(destination) && isNotBlockedByAlly(destination, allyPieces);
    }

    private boolean isValidMove(JanggiPosition destination) {
        int currentX = getPosition().getX();
        int currentY = getPosition().getY();
        int destX = destination.getX();
        int destY = destination.getY();

        return Math.abs(currentX - destX) + Math.abs(currentY - destY) == 1;
    }

    private boolean isNotBlockedByAlly(JanggiPosition destination, List<Piece> allyPieces) {
        return allyPieces.stream()
                .noneMatch(piece -> piece.getPosition().equals(destination));
    }
}