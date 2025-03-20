package object.strategy;

import java.util.ArrayList;
import java.util.List;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Pieces;
import object.Coordinate;
import object.Route;
import object.piece.Team;

public class CannonStrategy implements MoveStrategy {

    @Override
    public Route getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate, Team team) {
        Coordinate minCoordinate = Coordinate.getMinPosition(startCoordinate, endCoordinate);
        Coordinate maxCoordinate = Coordinate.getMaxPosition(startCoordinate, endCoordinate);

        List<Coordinate> coordinates = new ArrayList<>();
        if (startCoordinate.isSameColumn(endCoordinate)) {
            return calculateLegalRoute(minCoordinate, maxCoordinate, coordinates, new Coordinate(1, 0));
        }
        if (startCoordinate.isSameRow(endCoordinate)) {
            return calculateLegalRoute(minCoordinate, maxCoordinate, coordinates, new Coordinate(0, 1));
        }
        throw new IllegalArgumentException(MoveStrategy.INVALID_POSITION);
    }

    private static Route calculateLegalRoute(Coordinate minCoordinate, Coordinate maxCoordinate, List<Coordinate> coordinates,
                                             Coordinate direction) {
        while (!minCoordinate.equals(maxCoordinate)) {
            minCoordinate = minCoordinate.add(direction);
            coordinates.add(minCoordinate);
        }
        return new Route(coordinates);
    }

    @Override
    public Coordinate move(Coordinate destination, Pieces onRoutePieces, Team moveTeam) {
        validatePiecesEmpty(onRoutePieces);
        Piece firstPiece = onRoutePieces.getFirstPiece();
        Piece lastPiece = onRoutePieces.getLastPiece();
        var onRoutePiecesSize = onRoutePieces.size();

        validateIsFo(firstPiece, lastPiece);
        if (!(onRoutePiecesSize == 1 || onRoutePiecesSize == 2)) {
            throw new IllegalArgumentException(MoveStrategy.INVALID_POSITION);
        }
        if (onRoutePiecesSize == 1 && firstPiece.isSamePosition(destination)) {
            throw new IllegalArgumentException(MoveStrategy.INVALID_POSITION);
        }
        if (onRoutePiecesSize == 2 && !lastPiece.isSamePosition(destination)) {
            throw new IllegalArgumentException(MoveStrategy.INVALID_POSITION);
        }
        if (lastPiece.isSamePosition(destination) && lastPiece.isSameTeam(moveTeam)) {
            throw new IllegalArgumentException(MoveStrategy.INVALID_POSITION);
        }
        return destination;
    }

    private void validatePiecesEmpty(Pieces pieces) {
        if (pieces.size() == 0) {
            throw new IllegalArgumentException(MoveStrategy.INVALID_POSITION);
        }
    }

    private void validateIsFo(Piece firstPiece, Piece lastPiece) {
        if (isFo(firstPiece) || isFo(lastPiece)) {
            throw new IllegalArgumentException(MoveStrategy.INVALID_POSITION);
        }
    }

    private boolean isFo(Piece piece) {
        return piece.isSameType(PieceType.CANNON);
    }
}
