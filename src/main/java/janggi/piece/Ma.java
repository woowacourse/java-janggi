package janggi.piece;

import janggi.direction.MaDirection;
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
        boolean followRuleOfMove = checkRuleOfMove(maDirection);
        boolean existHurdleInPath = existHurdleInPath(maDirection, enemy, allies);
        boolean existAllieInDestination = existPieceInDestination(destination, allies);
        return followRuleOfMove && !existHurdleInPath && !existAllieInDestination;
    }

    private boolean checkRuleOfMove(MaDirection maDirection) {
        return maDirection != MaDirection.NONE;
    }

    private boolean existHurdleInPath(MaDirection direction, List<Piece> enemy, List<Piece> allies) {
        boolean existEnemyInPath = existPieceInPath(direction, enemy);
        boolean existAlliesInPath = existPieceInPath(direction, allies);
        return existEnemyInPath || existAlliesInPath;
    }

    private boolean existPieceInPath(MaDirection direction, List<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> direction.checkPositionInPath(getPosition(), piece.getPosition()));
    }

    private boolean existPieceInDestination(Position destination, List<Piece> pieces) {
        return pieces.stream().anyMatch(piece -> destination.equals(piece.getPosition()));
    }
}
