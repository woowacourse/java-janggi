package janggi.piece;

import janggi.direction.BeelineDirection;
import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;
import java.util.function.BiFunction;

public class Cha extends Piece {

    public Cha(final Position position) {
        super(PieceType.CHA, position);
    }

    public static List<Cha> generateInitialChas(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.CHA.getHeight());
        return PieceType.CHA.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Cha(new Position(xPosition, yPosition)))
                .toList();
    }

    @Override
    protected Cha makeMovedPiece(Position position) {
        return new Cha(position);
    }

    @Override
    public boolean ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {
        BeelineDirection direction = BeelineDirection.parse(getPosition(), destination);
        List<Position> positionsInPath = calculatePositionsInPath(direction, destination);

        boolean followRuleOfMove = checkRuleOfMove(direction);
        boolean existHurdleInPath = existHurdleInPath(positionsInPath, enemy, allies);
        boolean existAlliesInDestination = existPieceInPosition(destination, allies);
        return followRuleOfMove && !existHurdleInPath && !existAlliesInDestination;
    }

    private List<Position> calculatePositionsInPath(BeelineDirection direction, Position destination) {
        BiFunction<Position, Position, List<Position>> calculationMethod = direction.getCalculatePositionsInPath();
        return calculationMethod.apply(getPosition(), destination);
    }

    private boolean checkRuleOfMove(BeelineDirection direction) {
        return direction != BeelineDirection.NONE;
    }

    private boolean existHurdleInPath(List<Position> positionsInPath, List<Piece> enemy, List<Piece> allies) {
        boolean existEnemyInPath = existPieceInPath(positionsInPath, enemy);
        boolean existAlliesInPath = existPieceInPath(positionsInPath, allies);
        return existEnemyInPath || existAlliesInPath;
    }

    private boolean existPieceInPath(List<Position> positions, List<Piece> pieces) {
        return positions.stream()
                .anyMatch(position -> existPieceInPosition(position, pieces));
    }

    private boolean existPieceInPosition(Position position, List<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> position.equals(piece.getPosition()));
    }
}
