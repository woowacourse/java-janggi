package janggi.piece;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;

public class Ma implements Piece {

    private static final int score = 5;
    private static final int height = 0;

    private final PieceType pieceType;
    private final Position position;

    private Ma(final Position position) {
        this.pieceType = PieceType.MA;
        this.position = position;
    }

    public static Ma from(final Position position) {
        return new Ma(position);
    }

    public static List<Ma> generateInitialMas(final CampType campType, final List<Integer> xPositions) {
        int yPosition = Math.abs(campType.getStartYPosition() - height);
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
        MaDirection maDirection = MaDirection.of(position, destination);
        if (maDirection == MaDirection.NONE) {
            return false;
        }
        boolean isEnemyExist = enemy.stream()
                .anyMatch(enemyPiece -> maDirection.isDirectRoute(position, enemyPiece.getPosition()));
        if (isEnemyExist) {
            return false;
        }
        boolean isAlliesExist = allies.stream()
                .anyMatch(alliesPiece -> maDirection.isDirectRoute(position, alliesPiece.getPosition()));
        if (isAlliesExist) {
            return false;
        }
        return allies.stream().noneMatch(alliesPiece -> destination.equals(alliesPiece.getPosition()));
    }

    @Override
    public boolean checkPieceType(PieceType pieceType) {
        return this.pieceType == pieceType;
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
