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
    public Sa move(final JanggiPosition destination, final List<Piece> enemy, final List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Sa(destination);
    }

    @Override
    public boolean ableToMove(JanggiPosition destination, List<Piece> enemy, List<Piece> allies) {
        if (!isRuleOfMove(destination)) {
            return false;
        }
        return isNotHurdle(destination, allies);
    }

    private boolean isRuleOfMove(JanggiPosition destination) {
        return destination.equals(new JanggiPosition(getPosition().getX() - 1, getPosition().getY()))
                || destination.equals(new JanggiPosition(getPosition().getX() + 1, getPosition().getY()))
                || destination.equals(new JanggiPosition(getPosition().getX(), getPosition().getY() - 1))
                || destination.equals(new JanggiPosition(getPosition().getX(), getPosition().getY() + 1));
    }

    private static boolean isNotHurdle(JanggiPosition destination, List<Piece> allies) {
        return allies.stream()
                .noneMatch(piece -> piece.getPosition().equals(destination));
    }
}
