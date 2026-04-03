package domain.pieces;

import domain.*;

import java.util.List;

public class Chariot extends Piece {
    private final List<Direction> linearDirections = List.of(
            Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST
    );

    public Chariot(Camp camp) {
        super(camp, PieceType.CHARIOT);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardReader boardReader) {
        for (Direction direction : linearDirections) {
            if (canReachTarget(from, to, boardReader, direction)) {
                return true;
            }
        }
        return false;
    }

    private boolean canReachTarget(Position from, Position to, BoardReader boardReader, Direction direction) {
        Position current = from;
        while (current.canMove(direction)) {
            current = current.move(direction);
            if (current.equals(to)) {
                return true;
            }

            if (boardReader.isExist(current)) {
                break;
            }
        }
        return false;
    }
}
