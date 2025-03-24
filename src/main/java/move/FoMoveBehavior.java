package move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import move.direction.Direction;
import piece.Piece;
import piece.PieceType;
import piece.Pieces;
import piece.Team;
import piece.position.Position;

public class FoMoveBehavior extends MoveBehavior {

    @Override
    public Position move(Position destination, Pieces onRoutePieces, Team moveTeam) {
        validatePiecesEmpty(onRoutePieces);
        Piece onRouteFirstPiece = onRoutePieces.getFirstPiece();
        Piece onRoutelastPiece = onRoutePieces.getLastPiece();
        var onRoutePiecesSize = onRoutePieces.size();

        validateFoMove(destination, moveTeam, onRoutePiecesSize, onRouteFirstPiece, onRoutelastPiece);
        return destination;
    }

    private List<Position> calculateLegalRoute(Position startPosition, Position endPosition, Position minPosition,
                                               Position maxPosition, List<Position> positions) {
        if (startPosition.isSameColumn(endPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.UP);
        }
        if (startPosition.isSameRow(endPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.RIGHT);
        }
        throw new InvalidMovePosition();
    }

    private List<Position> calculateLegalRoute(Position minPosition, Position maxPosition, List<Position> positions,
                                               Direction direction) {
        while (!minPosition.equals(maxPosition)) {
            minPosition = minPosition.add(direction);
            positions.add(minPosition);
        }
        return Collections.unmodifiableList(positions);
    }

    private void validateFoMove(Position destination, Team moveTeam, int onRoutePiecesSize, Piece firstPiece,
                                Piece lastPiece) {
        throwInvalidMoveBehaviorByCondition(() -> isFo(firstPiece) || isFo(lastPiece));
        throwInvalidMoveBehaviorByCondition(() -> !(onRoutePiecesSize == 1 || onRoutePiecesSize == 2));

        if (onRoutePiecesSize == 1) {
            throwInvalidMoveBehaviorByCondition(() -> firstPiece.isSamePosition(destination));
        }
        if (onRoutePiecesSize == 2) {
            throwInvalidMoveBehaviorByCondition(() -> !lastPiece.isSamePosition(destination));
        }
        throwInvalidMoveBehaviorByCondition(
                () -> lastPiece.isSamePosition(destination) && lastPiece.isSameTeam(moveTeam));
    }

    private void validatePiecesEmpty(Pieces pieces) {
        if (pieces.size() == 0) {
            throw new InvalidMovePosition();
        }
    }

    private boolean isFo(Piece piece) {
        return piece.isSameType(getPieceType());
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.FO;
    }

    @Override
    public List<Position> calculateLegalRoute(Position startPosition, Position endPosition, Team team) {
        Position smallerPosition = startPosition.getSmallerPosition(endPosition);
        Position biggerPosition = startPosition.getBiggerPosition(endPosition);

        List<Position> positions = new ArrayList<>();
        return calculateLegalRoute(startPosition, endPosition, smallerPosition, biggerPosition, positions);
    }
}
