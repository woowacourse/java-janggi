package janggi.piece;

import janggi.direction.SangDirection;
import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;

public class Sang extends Piece {

    public Sang(final Position position) {
        super(PieceType.SANG, position);
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
        SangDirection direction = SangDirection.of(getPosition(), destination);

        boolean followRuleOfMove = checkRuleOfMove(direction);
        boolean existHurdleInPath = existHurdleInPath(direction, enemy, allies);
        boolean existAllieInDestination = existPieceInDestination(destination, allies);
        return followRuleOfMove && !existHurdleInPath && !existAllieInDestination;
    }

    private boolean checkRuleOfMove(SangDirection direction) {
        return direction != SangDirection.NONE;
    }

    private boolean existHurdleInPath(SangDirection direction, List<Piece> enemy, List<Piece> allies) {
        boolean existEnemyInPath = existPieceInPath(direction, enemy);
        boolean existAlliesInPath = existPieceInPath(direction, allies);
        return existEnemyInPath || existAlliesInPath;
    }

    private boolean existPieceInPath(SangDirection direction, List<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> direction.checkPositionInPath(getPosition(), piece.getPosition()));
    }

    private boolean existPieceInDestination(Position destination, List<Piece> pieces) {
        return pieces.stream().anyMatch(piece -> destination.equals(piece.getPosition()));
    }
}
