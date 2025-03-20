package janggi.piece;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;
import java.util.stream.IntStream;

public class Cha extends Piece {

    private Cha(final Position position) {
        super(PieceType.CHA, position);
    }

    public static Cha from(final Position position) {
        return new Cha(position);
    }

    public static List<Cha> generateInitialChas(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.CHA.getHeight());
        return PieceType.CHA.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Cha(new Position(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Cha move(final Position destination, final List<Piece> enemy, final List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Cha(destination);
    }

    @Override
    public boolean ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {
        if (!isRuleOfMove(destination)) {
            return false;
        }
        return isNotHurdle(destination, enemy, allies);
    }

    private boolean isRuleOfMove(Position destination) {
        return getPosition().getX() == destination.getX() || getPosition().getY() == destination.getY();
    }

    private boolean isNotHurdle(Position destination, List<Piece> enemy, List<Piece> allies) {
        List<Position> positions = calculatePositions(destination);
        for (Position position : positions) {
            if (position.equals(destination)) {
                continue;
            }
            boolean isEnemyExistence = enemy.stream()
                    .anyMatch(enemyPiece -> enemyPiece.getPosition().equals(position));
            if (isEnemyExistence) {
                return false;
            }
        }
        for (Position position : positions) {
            boolean isAlliesExistence = allies.stream()
                    .anyMatch(alliesPiece -> alliesPiece.getPosition().equals(position));
            if (isAlliesExistence) {
                return false;
            }
        }
        return true;
    }

    private List<Position> calculatePositions(Position destination) {
        if (getPosition().getX() == destination.getX()) {
            if (getPosition().getY() > destination.getY()) {
                return IntStream.rangeClosed(destination.getY(), getPosition().getY())
                        .mapToObj(y -> new Position(getPosition().getX(), y))
                        .toList();
            }
            return IntStream.rangeClosed(getPosition().getY(), destination.getY())
                    .mapToObj(y -> new Position(getPosition().getX(), y))
                    .toList();
        }
        if (getPosition().getX() > destination.getX()) {
            return IntStream.rangeClosed(destination.getX(), getPosition().getX())
                    .mapToObj(x -> new Position(x, getPosition().getY()))
                    .toList();
        }
        return IntStream.rangeClosed(getPosition().getX(), destination.getX())
                .mapToObj(x -> new Position(x, getPosition().getY()))
                .toList();
    }
}
