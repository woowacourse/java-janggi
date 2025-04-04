package janggi.domain.piece;

import janggi.domain.piece.direction.FourDirection;
import janggi.domain.piece.direction.GungDirection;
import janggi.domain.setting.CampType;
import janggi.domain.value.JanggiPosition;
import java.util.List;

public class Cha extends Piece {

    private Cha(final JanggiPosition janggiPosition) {
        super(PieceType.CHA, janggiPosition);
    }

    public static Cha from(final JanggiPosition janggiPosition) {
        return new Cha(janggiPosition);
    }

    public static List<Cha> generateInitialChas(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.CHA.getHeight());
        return PieceType.CHA.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Cha(new JanggiPosition(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Cha move(final JanggiPosition destination, final Pieces enemyPieces, final Pieces allyPieces) {
        if (!ableToMove(destination, enemyPieces, allyPieces)) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        enemyPieces.beAttackedAt(destination);
        return new Cha(destination);
    }

    @Override
    protected boolean ableToMove(JanggiPosition destination, Pieces enemyPieces, Pieces allyPieces) {
        if (janggiPosition.isDiagonalPositionInCastle() && destination.isDiagonalPositionInCastle()) {
            List<JanggiPosition> gungPathPositions = GungDirection.of(janggiPosition, destination);
            return isValidMoveInCastle(gungPathPositions)
                    && allyPieces.isPathBlockedBy(gungPathPositions)
                    && enemyPieces.isPathBlockedBy(gungPathPositions);
        }
        if (!isValidMove(destination)) {
            return false;
        }
        List<JanggiPosition> pathPositions = FourDirection.from(destination, janggiPosition);
        if (!enemyPieces.isNotBlockedBy(destination)) {
            return allyPieces.isPathBlockedBy(pathPositions);
        }
        return allyPieces.isPathBlockedBy(pathPositions)
                && enemyPieces.isPathBlockedBy(pathPositions);
    }

    private boolean isValidMoveInCastle(List<JanggiPosition> pathPositions) {
        if (pathPositions.isEmpty()) {
            return false;
        }
        JanggiPosition start = pathPositions.getFirst();
        JanggiPosition end = pathPositions.getLast();

        int dx = Math.abs(end.x() - start.x());
        int dy = Math.abs(end.y() - start.y());

        boolean isOneStepStraight = (dx == 0 && dy == 0);
        boolean isTwoStepStraight = (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
        boolean isTwoStepDiagonal = (dx == 1 && dy == 1);

        return isOneStepStraight || isTwoStepStraight || isTwoStepDiagonal;
    }

    private boolean isValidMove(JanggiPosition destination) {
        return janggiPosition.x() == destination.x() || janggiPosition.y() == destination.y();
    }
}
