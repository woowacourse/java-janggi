package janggi.piece;

import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;

public class Gung extends Piece {

    private Gung(final JanggiPosition janggiPosition) {
        super(PieceType.GUNG, janggiPosition);
    }

    public static Gung from(final JanggiPosition janggiPosition) {
        return new Gung(janggiPosition);
    }

    public static List<Gung> generateInitialGung(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.GUNG.getHeight());
        return PieceType.GUNG.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Gung(new JanggiPosition(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Gung move(final JanggiPosition destination, final List<Piece> enemy, final List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Gung(destination);
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

    private boolean isNotHurdle(JanggiPosition destination, List<Piece> allies) {
        return allies.stream()
                .noneMatch(piece -> piece.getPosition().equals(destination));
    }
}
