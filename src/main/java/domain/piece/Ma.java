package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.MaMovement;
import java.util.Arrays;
import java.util.List;

public class Ma extends Piece {

    public Ma(Country country) {
        super(country, PieceType.MA);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate currCoordinate,
                                                   Board board) {
        return Arrays.stream(MaMovement.values())
                .filter(maMovement -> !board.hasPiece(movePosition(currCoordinate, maMovement.getDirection())))
                .flatMap(maMovement -> maMovement.getDestination().stream()
                        .map(destination -> movePosition(currCoordinate, destination))
                        .filter(next -> !board.isOutOfBoundary(next))
                        .filter(next -> !board.hasPiece(next) || !board.isMyTeam(currCoordinate, next))
                )
                .toList();
    }

    public static Coordinate movePosition(Coordinate currCoordinate, Coordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
