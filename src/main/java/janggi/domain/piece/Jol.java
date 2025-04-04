package janggi.domain.piece;

import janggi.domain.piece.direction.FourDirection;
import janggi.domain.piece.direction.GungDirection;
import janggi.domain.setting.CampType;
import janggi.domain.value.JanggiPosition;
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
        enemyPieces.beAttackedAt(destination);
        return new Jol(destination);
    }

    @Override
    protected boolean ableToMove(JanggiPosition destination, Pieces enemyPieces, Pieces allyPieces) {
        if (janggiPosition.isDiagonalPositionInCastle()) {
            List<JanggiPosition> gungPathPositions = GungDirection.of(janggiPosition, destination);
            return isValidMoveInCastle(gungPathPositions) && allyPieces.isNotBlockedBy(destination);
        }
        List<JanggiPosition> pathPositions = FourDirection.from(destination, janggiPosition);
        return isValidMove(pathPositions) && allyPieces.isNotBlockedBy(destination);
    }

    private boolean isValidMoveInCastle(List<JanggiPosition> pathPositions) {
        if (pathPositions.isEmpty()) {
            return false;
        }

        JanggiPosition destination = pathPositions.getLast();
        int currentX = janggiPosition.x();
        int currentY = janggiPosition.y();
        int destinationX = destination.x();
        int destinationY = destination.y();

        boolean isForward = (destinationX == currentX && destinationY == currentY - 1);
        boolean isSideways = (destinationY == currentY && Math.abs(destinationX - currentX) == 1);
        boolean isDiagonal = (Math.abs(destinationX - currentX) == 1 && destinationY == currentY - 1);

        return isForward || isSideways || isDiagonal;
    }

    private boolean isValidMove(List<JanggiPosition> pathPositions) {
        int currentX = janggiPosition.x();
        int currentY = janggiPosition.y();
        int destX = pathPositions.getLast().x();
        int destY = pathPositions.getLast().y();

        return (destX == currentX && destY == currentY - 1) || (destY == currentY && Math.abs(destX - currentX) == 1);
    }
}
