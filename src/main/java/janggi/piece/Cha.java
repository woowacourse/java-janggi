package janggi.piece;

import janggi.direction.BeelineDirection;
import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;

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
        BeelineDirection direction = BeelineDirection.parse(getPosition(), destination);
        List<Position> positionsInPath = direction.calculatePositionsInPath(getPosition(), destination);

        boolean followRuleOfMove = checkRuleOfMove(direction);
        boolean existHurdleInPath = existHurdleInPath(positionsInPath, enemy, allies);
        boolean existAlliesInDestination = existPieceInPosition(destination, allies);
        return followRuleOfMove && !existHurdleInPath && !existAlliesInDestination;
    }

    private boolean checkRuleOfMove(BeelineDirection direction) {
        return direction != BeelineDirection.NONE;
    }

    private boolean existHurdleInPath(List<Position> positionsInPath, List<Piece> enemy, List<Piece> allies) {
        boolean isEnemyExistence = existPieceInPath(positionsInPath, enemy);
        boolean isAlliesExistence = existPieceInPath(positionsInPath, allies);
        return isEnemyExistence || isAlliesExistence;
    }

    private boolean existPieceInPath(List<Position> positions, List<Piece> pieces) {
        return positions.stream()
                .anyMatch(position -> existPieceInPosition(position, pieces));
    }

    private boolean existPieceInPosition(Position position, List<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> piece.getPosition().equals(position));
    }
}
