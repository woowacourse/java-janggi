package janggi.domain.piece;

import janggi.domain.piece.direction.FourDirection;
import janggi.domain.piece.direction.GungDirection;
import janggi.domain.setting.CampType;
import janggi.domain.value.JanggiPosition;
import java.util.List;

public class Byeong extends Piece {

    private Byeong(final JanggiPosition janggiPosition) {
        super(PieceType.BYEONG, janggiPosition);
    }

    public static Byeong from(final JanggiPosition janggiPosition) {
        return new Byeong(janggiPosition);
    }

    public static List<Byeong> generateInitialByeongs(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.BYEONG.getHeight());
        return PieceType.BYEONG.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Byeong(new JanggiPosition(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Byeong move(final JanggiPosition destination, final Pieces enemyPieces, final Pieces allyPieces) {
        if (!ableToMove(destination, enemyPieces, allyPieces)) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        enemyPieces.beAttackedAt(destination);
        return new Byeong(destination);
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

        boolean isForward = (destinationX == currentX && destinationY == currentY + 1);
        boolean isSideways = (destinationY == currentY && Math.abs(destinationX - currentX) == 1);
        boolean isDiagonal = (Math.abs(destinationX - currentX) == 1 && destinationY == currentY + 1);

        return isForward || isSideways || isDiagonal;
    }

    private boolean isValidMove(List<JanggiPosition> pathPositions) {
        int currentX = janggiPosition.x();
        int currentY = janggiPosition.y();
        int destinationX = pathPositions.getLast().x();
        int destinationY = pathPositions.getLast().y();

        return (destinationX == currentX && destinationY == currentY + 1) || (destinationY == currentY && Math.abs(destinationX - currentX) == 1);
    }
}
