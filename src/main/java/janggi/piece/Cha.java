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
        boolean followRuleOfMove = checkRuleOfMove(destination);
        boolean existHurdleInPath = existHurdleInPath(destination, enemy, allies);
        boolean existAlliesInDestination = existPieceInPosition(destination, allies);
        return followRuleOfMove && !existHurdleInPath && !existAlliesInDestination;
    }

    private boolean checkRuleOfMove(Position destination) {
        return getPosition().getX() == destination.getX() || getPosition().getY() == destination.getY();
    }

    private boolean existHurdleInPath(Position destination, List<Piece> enemy, List<Piece> allies) {
        List<Position> positions = calculatePositionsInPath(destination);
        boolean isEnemyExistence = existPieceInPath(positions, enemy);
        boolean isAlliesExistence = existPieceInPath(positions, allies);
        return isEnemyExistence || isAlliesExistence;
    }

    private List<Position> calculatePositionsInPath(Position destination) {
        BeelineDirection direction = BeelineDirection.parse(getPosition(), destination);
        return direction.calculatePositionsInPath(getPosition(), destination);
    }

    private boolean existPieceInPath(List<Position> positions, List<Piece> pieces) {
        return positions.stream()
                .anyMatch(position -> existPieceInPosition(position, pieces));
    }

    private boolean existPieceInPosition(Position position, List<Piece> pieces) {
        return pieces.stream().anyMatch(piece -> piece.getPosition().equals(position));
    }
}
