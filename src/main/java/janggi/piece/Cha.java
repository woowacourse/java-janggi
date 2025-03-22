package janggi.piece;

import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;
import java.util.stream.IntStream;

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
    public Cha move(final JanggiPosition destination, final List<Piece> enemy, final List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Cha(destination);
    }

    @Override
    public boolean ableToMove(JanggiPosition destination, List<Piece> enemy, List<Piece> allies) {
        if (!isRuleOfMove(destination)) {
            return false;
        }
        return isNotHurdle(destination, enemy, allies);
    }

    private boolean isRuleOfMove(JanggiPosition destination) {
        return getPosition().getX() == destination.getX() || getPosition().getY() == destination.getY();
    }

    private boolean isNotHurdle(JanggiPosition destination, List<Piece> enemy, List<Piece> allies) {
        List<JanggiPosition> janggiPositions = calculatePositions(destination);
        for (JanggiPosition janggiPosition : janggiPositions) {
            if (janggiPosition.equals(destination)) {
                continue;
            }
            boolean isEnemyExistence = enemy.stream()
                    .anyMatch(enemyPiece -> enemyPiece.getPosition().equals(janggiPosition));
            if (isEnemyExistence) {
                return false;
            }
        }
        for (JanggiPosition janggiPosition : janggiPositions) {
            boolean isAlliesExistence = allies.stream()
                    .anyMatch(alliesPiece -> alliesPiece.getPosition().equals(janggiPosition));
            if (isAlliesExistence) {
                return false;
            }
        }
        return true;
    }

    private List<JanggiPosition> calculatePositions(JanggiPosition destination) {
        if (getPosition().getX() == destination.getX()) {
            if (getPosition().getY() > destination.getY()) {
                return IntStream.rangeClosed(destination.getY(), getPosition().getY())
                        .mapToObj(y -> new JanggiPosition(getPosition().getX(), y))
                        .toList();
            }
            return IntStream.rangeClosed(getPosition().getY(), destination.getY())
                    .mapToObj(y -> new JanggiPosition(getPosition().getX(), y))
                    .toList();
        }
        if (getPosition().getX() > destination.getX()) {
            return IntStream.rangeClosed(destination.getX(), getPosition().getX())
                    .mapToObj(x -> new JanggiPosition(x, getPosition().getY()))
                    .toList();
        }
        return IntStream.rangeClosed(getPosition().getX(), destination.getX())
                .mapToObj(x -> new JanggiPosition(x, getPosition().getY()))
                .toList();
    }
}
