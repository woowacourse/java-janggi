package janggi.piece;

import janggi.piece.direction.DiagonalDirection;
import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;

public class Sang extends Piece {

    private Sang(final JanggiPosition janggiPosition) {
        super(PieceType.SANG, janggiPosition);
    }

    public static Sang from(final JanggiPosition janggiPosition) {
        return new Sang(janggiPosition);
    }

    public static List<Sang> generateInitialSangs(final CampType campType, final List<Integer> xPositions) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.SANG.getHeight());
        return xPositions.stream()
                .map(xPosition -> new Sang(new JanggiPosition(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Sang move(JanggiPosition destination, Pieces enemy, Pieces allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Sang(destination);
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
