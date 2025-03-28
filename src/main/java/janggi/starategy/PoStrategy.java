package janggi.starategy;

import janggi.direction.BeelineDirection;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.value.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class PoStrategy implements MoveStrategy {

    @Override
    public boolean ableToMove(Position start, Position destination, List<Piece> enemy, List<Piece> allies) {
        BeelineDirection direction = BeelineDirection.parse(start, destination);
        List<Position> positionsInPath = calculatePositionsInPath(start, direction, destination);
        List<Piece> piecesInPath = searchPieceInPath(enemy, allies, positionsInPath);

        boolean followRuleOfMove = checkRuleOfMove(direction);
        boolean existOnlyOnePieceInPath = existOnlyOnePieceInPath(piecesInPath);
        boolean existPoInPath = existPoInPath(piecesInPath);
        boolean existAlliesInDestination = existPieceInPosition(destination, allies);
        return followRuleOfMove && existOnlyOnePieceInPath && !existPoInPath && !existAlliesInDestination;
    }

    private List<Position> calculatePositionsInPath(Position start, BeelineDirection direction, Position destination) {
        BiFunction<Position, Position, List<Position>> calculationMethod = direction.getCalculatePositionsInPath();
        return calculationMethod.apply(start, destination);
    }

    private List<Piece> searchPieceInPath(List<Piece> enemy, List<Piece> allies,
            List<Position> positionsInPath) {
        ArrayList<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(enemy);
        allPieces.addAll(allies);
        return allPieces.stream()
                .filter(piece -> positionsInPath.contains(piece.getPosition()))
                .toList();
    }

    private boolean checkRuleOfMove(BeelineDirection direction) {
        return direction != BeelineDirection.NONE;
    }

    private boolean existOnlyOnePieceInPath(List<Piece> piecesInPath) {
        return piecesInPath.size() == 1;
    }

    private boolean existPoInPath(List<Piece> piecesInPath) {
        long poCount = piecesInPath.stream()
                .filter(alliesPiece -> alliesPiece.checkPieceType(PieceType.PO))
                .count();
        return poCount > 0;
    }

    private boolean existPieceInPosition(Position position, List<Piece> pieces) {
        return pieces.stream()
                .anyMatch(alliesPiece -> alliesPiece.getPosition().equals(position));
    }
}
