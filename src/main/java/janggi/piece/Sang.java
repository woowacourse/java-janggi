package janggi.piece;

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
    public Sang move(JanggiPosition destination, List<Piece> enemy, List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Sang(destination);
    }

    @Override
    public boolean ableToMove(JanggiPosition destination, List<Piece> enemy, List<Piece> allies) {
        SangDirection sangDirection = SangDirection.of(getPosition(), destination);
        if (sangDirection == SangDirection.NONE) {
            return false;
        }
        if (isPieceExistInRoute(enemy, sangDirection) || isPieceExistInRoute(allies, sangDirection)) {
            return false;
        }
        return allies.stream().noneMatch(alliesPiece -> destination.equals(alliesPiece.getPosition()));
    }

    private boolean isPieceExistInRoute(List<Piece> pieces, SangDirection direction) {
        return pieces.stream()
                .anyMatch(piece -> direction.isRoute(getPosition(), piece.getPosition()));
    }
}
