package strategy;

import java.util.ArrayList;
import java.util.List;
import piece.Piece;
import piece.PieceType;
import piece.Pieces;
import piece.Position;
import piece.Route;
import piece.Team;

public class FoMoveStrategy implements MoveStrategy {

    private static final String INVALID_MOVE_LOCATION = "이동불가능한 위치입니다.";

    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition, Team team) {
        Position minPosition = Position.getMinPosition(startPosition, endPosition);
        Position maxPosition = Position.getMaxPosition(startPosition, endPosition);

        List<Position> positions = new ArrayList<>();
        if (startPosition.isSameColumn(endPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, new Position(1, 0));
        }
        if (startPosition.isSameRow(endPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, new Position(0, 1));
        }
        throw new IllegalArgumentException(INVALID_MOVE_LOCATION);
    }

    private static Route calculateLegalRoute(Position minPosition, Position maxPosition, List<Position> positions,
                                             Position direction) {
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
        if (!(onRoutePiecesSize == 1 || onRoutePiecesSize == 2)) {
            throw new IllegalArgumentException(INVALID_MOVE_LOCATION);
        }
        if (onRoutePiecesSize == 1 && firstPiece.isSamePosition(destination)) {
            throw new IllegalArgumentException(INVALID_MOVE_LOCATION);
        }
        if (onRoutePiecesSize == 2 && !lastPiece.isSamePosition(destination)) {
            throw new IllegalArgumentException(INVALID_MOVE_LOCATION);
        }
        if (lastPiece.isSamePosition(destination) && lastPiece.isSameTeam(moveTeam)) {
            throw new IllegalArgumentException(INVALID_MOVE_LOCATION);
        }
        return destination;
    }

    private void validatePiecesEmpty(Pieces pieces) {
        if (pieces.size() == 0) {
            throw new IllegalArgumentException(INVALID_MOVE_LOCATION);
        }
    }

    private void validateIsFo(Piece firstPiece, Piece lastPiece) {
        if (isFo(firstPiece) || isFo(lastPiece)) {
            throw new IllegalArgumentException(INVALID_MOVE_LOCATION);
        }
    }

    private boolean isFo(Piece piece) {
        return piece.isSameType(PieceType.FO);
    }
}
