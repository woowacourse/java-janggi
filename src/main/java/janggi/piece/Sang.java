package janggi.piece;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;

public class Sang implements Piece {

    private static final int score = 3;
    private static final int height = 0;

    private final PieceType pieceType;
    private final Position position;

    private Sang(final Position position) {
        this.pieceType = PieceType.SANG;
        this.position = position;
    }

    public static Sang from(final Position position) {
        return new Sang(position);
    }

    public static List<Sang> generateInitialSangs(final CampType campType, final List<Integer> xPositions) {
        int yPosition = Math.abs(campType.getStartYPosition() - height);
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
        SangDirection sangDirection = SangDirection.of(position, destination);
        if (sangDirection == SangDirection.NONE) {
            return false;
        }
        boolean isEnemyExist = enemy.stream()
                .anyMatch(enemyPiece -> sangDirection.isRoute(position, enemyPiece.getPosition()));
        if (isEnemyExist) {
            return false;
        }
        boolean isAlliesExist = allies.stream()
                .anyMatch(alliesPiece -> sangDirection.isRoute(position, alliesPiece.getPosition()));
        if (isAlliesExist) {
            return false;
        }
        return allies.stream().noneMatch(alliesPiece -> destination.equals(alliesPiece.getPosition()));
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public Position getPosition() {
        return position;
    }

}
