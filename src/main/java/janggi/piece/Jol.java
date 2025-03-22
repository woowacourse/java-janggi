package janggi.piece;

import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;

public class Jol extends Piece {

    private final CampType campType;

    private Jol(final JanggiPosition janggiPosition, final CampType campType) {
        super(PieceType.JOL, janggiPosition);
        this.campType = campType;
    }

    public static Jol from(final JanggiPosition janggiPosition, final CampType campType) {
        return new Jol(janggiPosition, campType);
    }

    public static List<Jol> generateInitialJols(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.JOL.getHeight());
        return PieceType.JOL.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Jol(new JanggiPosition(xPosition, yPosition), campType))
                .toList();
    }

    @Override
    public Jol move(final JanggiPosition destination, final List<Piece> enemy, final List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Jol(destination, campType);
    }

    @Override
    public boolean ableToMove(JanggiPosition destination, List<Piece> enemy, List<Piece> allies) {
        if (!isRuleOfMove(destination)) {
            return false;
        }
        return isNotHurdle(destination, allies);
    }

    private boolean isRuleOfMove(JanggiPosition destination) {
        if (campType == CampType.CHO) {
            return destination.equals(new JanggiPosition(getPosition().getX() - 1, getPosition().getY()))
                    || destination.equals(new JanggiPosition(getPosition().getX() + 1, getPosition().getY()))
                    || destination.equals(new JanggiPosition(getPosition().getX(), getPosition().getY() - 1));
        }
        return destination.equals(new JanggiPosition(getPosition().getX() - 1, getPosition().getY()))
                || destination.equals(new JanggiPosition(getPosition().getX() + 1, getPosition().getY()))
                || destination.equals(new JanggiPosition(getPosition().getX(), getPosition().getY() + 1));
    }

    private static boolean isNotHurdle(JanggiPosition destination, List<Piece> allies) {
        return allies.stream()
                .noneMatch(piece -> piece.getPosition().equals(destination));
    }
}
