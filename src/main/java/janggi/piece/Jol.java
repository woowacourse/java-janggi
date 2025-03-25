package janggi.piece;

import janggi.piece.direction.FourDirection;
import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;

public class Jol extends Piece {

    private Jol(final JanggiPosition janggiPosition) {
        super(PieceType.JOL, janggiPosition);
    }

    public static Jol from(final JanggiPosition janggiPosition) {
        return new Jol(janggiPosition);
    }

    public static List<Jol> generateInitialJols(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.JOL.getHeight());
        return PieceType.JOL.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Jol(new JanggiPosition(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Jol move(final JanggiPosition destination, final Pieces enemyPieces, final Pieces allyPieces) {
        if (!ableToMove(destination, enemyPieces, allyPieces)) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Jol(destination);
    }

    @Override
    protected boolean ableToMove(JanggiPosition destination, Pieces enemyPieces, Pieces allyPieces) {
        List<JanggiPosition> pathPositions = FourDirection.from(destination, getPosition());
        return isValidMove(pathPositions) && allyPieces.isNotBlockedBy(destination);
    }

    private boolean isValidMove(List<JanggiPosition> pathPositions) {
        int currentX = getPosition().x();
        int currentY = getPosition().y();
        int destX = pathPositions.getLast().x();
        int destY = pathPositions.getLast().y();

        return (destX == currentX && destY == currentY - 1) || (destY == currentY && Math.abs(destX - currentX) == 1);
    }
}
