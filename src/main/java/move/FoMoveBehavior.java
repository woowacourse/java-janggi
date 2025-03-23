package move;

import exception.InvalidMovePosition;
import java.util.ArrayList;
import java.util.List;
import piece.Piece;
import piece.PieceType;
import piece.Pieces;
import piece.Position;
import piece.Route;
import piece.Team;

public class FoMoveBehavior implements MoveBehavior {


    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition, Team team) {
        Position smallerPosition = startPosition.getSmallerPosition(endPosition);
        Position biggerPosition = startPosition.getBiggerPosition(endPosition);

        List<Position> positions = new ArrayList<>();
        return calculateLegalRoute(startPosition, endPosition, smallerPosition, biggerPosition, positions);
    }

    private Route calculateLegalRoute(Position startPosition, Position endPosition, Position minPosition,
                                      Position maxPosition, List<Position> positions) {
        if (startPosition.isSameColumn(endPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.UP);
        }
        if (startPosition.isSameRow(endPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.RIGHT);
        }
        throw new InvalidMovePosition();
    }

    private Route calculateLegalRoute(Position minPosition, Position maxPosition, List<Position> positions,
                                      Direction direction) {
        while (!minPosition.equals(maxPosition)) {
            minPosition = minPosition.add(direction);
            positions.add(minPosition);
        }
        return new Route(positions);
    }

    @Override
    public Position move(Position destination, Pieces onRoutePieces, Team moveTeam) {
        validatePiecesEmpty(onRoutePieces);
        Piece firstPiece = onRoutePieces.getFirstPiece();
        Piece lastPiece = onRoutePieces.getLastPiece();
        var onRoutePiecesSize = onRoutePieces.size();

        validateIsFo(firstPiece, lastPiece);
        validateFoMove(destination, moveTeam, onRoutePiecesSize, firstPiece, lastPiece);
        return destination;
    }

    private void validateFoMove(Position destination, Team moveTeam, int onRoutePiecesSize, Piece firstPiece,
                                Piece lastPiece) {
        throwInvalidMoveBehaviorByCondition(() -> !(onRoutePiecesSize == 1 || onRoutePiecesSize == 2));
        throwInvalidMoveBehaviorByCondition(() -> onRoutePiecesSize == 1 && firstPiece.isSamePosition(destination));
        throwInvalidMoveBehaviorByCondition(() -> onRoutePiecesSize == 2 && !lastPiece.isSamePosition(destination));
        throwInvalidMoveBehaviorByCondition(
                () -> lastPiece.isSamePosition(destination) && lastPiece.isSameTeam(moveTeam));
    }

    private void validatePiecesEmpty(Pieces pieces) {
        if (pieces.size() == 0) {
            throw new InvalidMovePosition();
        }
    }

    private void validateIsFo(Piece firstPiece, Piece lastPiece) {
        throwInvalidMoveBehaviorByCondition(() -> isFo(firstPiece) || isFo(lastPiece));
    }

    private boolean isFo(Piece piece) {
        return piece.isSameType(PieceType.FO);
    }
}
