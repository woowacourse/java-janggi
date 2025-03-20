package janggi.piece;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;

public class Sang extends Piece {

    private Sang(final Position position) {
        super(PieceType.SANG, position);
    }

    public static Sang from(final Position position) {
        return new Sang(position);
    }

    public static List<Sang> generateInitialSangs(final CampType campType, final List<Integer> xPositions) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.SANG.getHeight());
        return xPositions.stream()
                .map(xPosition -> new Sang(new Position(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Sang move(Position destination, List<Piece> enemy, List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Sang(destination);
    }

    @Override
    public boolean ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {
        SangDirection sangDirection = SangDirection.of(getPosition(), destination);
        if (sangDirection == SangDirection.NONE) {
            return false;
        }
        boolean isEnemyExist = isEnemyExistInRoute(enemy, sangDirection);
        boolean isAlliesExist = isAlliesExistInRoute(allies, sangDirection);
        if (isEnemyExist || isAlliesExist) {
            return false;
        }
        return allies.stream().noneMatch(alliesPiece -> destination.equals(alliesPiece.getPosition()));
    }

    private boolean isEnemyExistInRoute(List<Piece> enemy, SangDirection direction) {
        return enemy.stream()
                .anyMatch(enemyPiece -> direction.isRoute(getPosition(), enemyPiece.getPosition()));
    }

    private boolean isAlliesExistInRoute(List<Piece> allies, SangDirection direction) {
        return allies.stream()
                .anyMatch(alliesPiece -> direction.isRoute(getPosition(), alliesPiece.getPosition()));
    }
}
