package janggi.piece;

import janggi.piece.direction.DiagonalDirection;
import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;

public class Ma extends Piece {

    private Ma(final JanggiPosition janggiPosition) {
        super(PieceType.MA, janggiPosition);
    }

    public static Ma from(final JanggiPosition janggiPosition) {
        return new Ma(janggiPosition);
    }

    public static List<Ma> generateInitialMas(final CampType campType, final List<Integer> xPositions) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.MA.getHeight());
        return xPositions.stream()
                .map(xPosition -> new Ma(new JanggiPosition(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Ma move(JanggiPosition destination, Pieces enemy, Pieces allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Ma(destination);
    }

    @Override
    protected boolean ableToMove(JanggiPosition destination, Pieces enemy, Pieces allies) {
        DiagonalDirection diagonalDirection = DiagonalDirection.of(getPosition(), destination);

        if (enemy.isPieceExistInRoute(diagonalDirection, getPosition()) ||
                allies.isPieceExistInRoute(diagonalDirection, getPosition())) {
            return false;
        }
        return allies.isNotBlockedBy(destination);
    }
}
