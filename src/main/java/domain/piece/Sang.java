package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.SangMovement;
import java.util.ArrayList;
import java.util.List;

public class Sang extends Piece {

    public Sang(Country country) {
        super(country, PieceType.SANG);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate from,
                                                   Board board) {
        List<Coordinate> availablePositions = new ArrayList<>();
        for (SangMovement sangMovement : SangMovement.values()) {
            Coordinate next = movePosition(from, sangMovement.getDirection());
            if (!board.hasPiece(next)) {
                List<Coordinate> left = sangMovement.getLeftDestination();
                if (!board.hasPiece(movePosition(from, left.get(0))) &&
                        (!board.hasPiece(movePosition(from, left.get(1))) || !board.isMyTeam(
                                country, movePosition(from, left.get(1))))) {
                    availablePositions.add(movePosition(from, left.get(1)));
                }

                List<Coordinate> right = sangMovement.getRightDestination();
                if (!board.hasPiece(movePosition(from, right.get(0))) &&
                        (!board.hasPiece(movePosition(from, right.get(1))) || !board.isMyTeam(
                                country, movePosition(from, right.get(1))))) {
                    availablePositions.add(movePosition(from, right.get(1)));
                }
            }
        }
        return availablePositions;
    }

    public static Coordinate movePosition(Coordinate currCoordinate, Coordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
