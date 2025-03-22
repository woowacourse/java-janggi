package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.SaMovement;
import java.util.Arrays;
import java.util.List;

public class Sa extends Piece {

    public Sa(Country country) {
        super(country, PieceType.SA);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate currCoordinate,
                                                   Board board) {
        return Arrays.stream(SaMovement.values())
                .map(gungMovement -> movePosition(currCoordinate, gungMovement.getDirection()))
                .filter(next -> !board.isOutOfBoundary(next) && !board.isMyTeam(currCoordinate, next))
                .toList();
    }

    public static Coordinate movePosition(Coordinate currCoordinate, Coordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
