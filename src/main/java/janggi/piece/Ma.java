package janggi.piece;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;

public class Ma extends Piece {

    private Ma(final Position position) {
        super(PieceType.MA, position);
    }

    public static Ma from(final Position position) {
        return new Ma(position);
    }

    public static List<Ma> generateInitialMas(final CampType campType, final List<Integer> xPositions) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.MA.getHeight());
        return xPositions.stream()
                .map(xPosition -> new Ma(new Position(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Ma move(Position destination, List<Piece> enemy, List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Ma(destination);
    }

    @Override
    public boolean ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {
        MaDirection maDirection = MaDirection.of(getPosition(), destination);
        if (maDirection == MaDirection.NONE) {
            return false;
        }
        boolean isEnemyExist = isEnemyExistInRoute(enemy, maDirection);
        boolean isAlliesExist = isAlliesExistInRoute(allies, maDirection);
        if (isEnemyExist || isAlliesExist) {
            return false;
        }
        return allies.stream().noneMatch(alliesPiece -> destination.equals(alliesPiece.getPosition()));
    }

    private boolean isEnemyExistInRoute(List<Piece> enemy, MaDirection direction) {
        return enemy.stream()
                .anyMatch(enemyPiece -> direction.isDirectRoute(getPosition(), enemyPiece.getPosition()));
    }

    private boolean isAlliesExistInRoute(List<Piece> allies, MaDirection direction) {
        return allies.stream()
                .anyMatch(alliesPiece -> direction.isDirectRoute(getPosition(), alliesPiece.getPosition()));
    }
}
