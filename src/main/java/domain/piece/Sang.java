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
    public List<Coordinate> availableMovePositions(Coordinate currCoordinate,
                                                   Board board) {
        List<Coordinate> availablePositions = new ArrayList<>();
        for (SangMovement sangMovement : SangMovement.values()) {
            Coordinate next = movePosition(currCoordinate, sangMovement.getDirection());
            if (!board.hasPiece(next)) {
                List<Coordinate> left = sangMovement.getLeftDestination();
                if (!board.hasPiece(movePosition(currCoordinate, left.get(0))) &&
                        (!board.hasPiece(movePosition(currCoordinate, left.get(1))) || !board.isMyTeam(
                                currCoordinate, movePosition(currCoordinate, left.get(1))))) {
                    availablePositions.add(movePosition(currCoordinate, left.get(1)));
                }

                List<Coordinate> right = sangMovement.getRightDestination();
                if (!board.hasPiece(movePosition(currCoordinate, right.get(0))) &&
                        (!board.hasPiece(movePosition(currCoordinate, right.get(1))) || !board.isMyTeam(
                                currCoordinate, movePosition(currCoordinate, right.get(1))))) {
                    availablePositions.add(movePosition(currCoordinate, right.get(1)));
                }
            }
        }
        return availablePositions;
    }

    public static Coordinate movePosition(Coordinate currCoordinate, Coordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
