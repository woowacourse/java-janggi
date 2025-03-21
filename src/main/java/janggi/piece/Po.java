package janggi.piece;

import janggi.direction.BeelineDirection;
import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;

public class Po extends Piece {

    private Po(final Position position) {
        super(PieceType.PO, position);
    }

    public static Po from(final Position position) {
        return new Po(position);
    }

    public static List<Po> generateInitialPos(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.PO.getHeight());
        return PieceType.PO.getDefaultXPositions().stream()
                .map(xPosition -> new Po(new Position(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Po move(final Position destination, final List<Piece> enemy, final List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Po(destination);
    }

    @Override
    public boolean ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {
        BeelineDirection direction = BeelineDirection.parse(getPosition(), destination);
        List<Position> positionsInPath = direction.calculatePositionsInPath(getPosition(), destination);
        List<Piece> enemyInPath = searchPieceInPath(enemy, positionsInPath);
        List<Piece> alliesInPath = searchPieceInPath(allies, positionsInPath);

        boolean followRuleOfMove = checkRuleOfMove(direction);
        boolean existOnlyOnePieceInPath = existOnlyOnePieceInPath(enemyInPath, alliesInPath);
        boolean existPoInPath = existPoInPath(enemyInPath, alliesInPath);
        boolean existAlliesInDestination = existPieceInPosition(destination, allies);
        return followRuleOfMove && existOnlyOnePieceInPath && !existPoInPath && !existAlliesInDestination;
    }

    private List<Piece> searchPieceInPath(List<Piece> pieces, List<Position> positionsInPath) {
        return pieces.stream()
                .filter(piece -> positionsInPath.contains(piece.getPosition()))
                .toList();
    }

    private boolean checkRuleOfMove(BeelineDirection direction) {
        return direction != BeelineDirection.NONE;
    }

    private boolean existOnlyOnePieceInPath(List<Piece> enemyInPath, List<Piece> alliesInPath) {
        return enemyInPath.size() + alliesInPath.size() == 1;
    }

    private boolean existPoInPath(List<Piece> enemyInPath, List<Piece> alliesInPath) {
        long enemyPoCount = enemyInPath.stream()
                .filter(alliesPiece -> alliesPiece.checkPieceType(PieceType.PO))
                .count();
        long alliesPoCount = alliesInPath.stream()
                .filter(alliesPiece -> alliesPiece.checkPieceType(PieceType.PO))
                .count();
        return enemyPoCount + alliesPoCount > 0;
    }

    private boolean existPieceInPosition(Position position, List<Piece> pieces) {
        return pieces.stream()
                .anyMatch(alliesPiece -> alliesPiece.getPosition().equals(position));
    }
}
