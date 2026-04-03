package domain.pieces;

import domain.*;

import java.util.List;

public class SingleStepPiece extends Piece {

    private final List<Direction> singleStepDirections = List.of(
            Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST,
            Direction.NORTHWEST, Direction.NORTHEAST, Direction.SOUTHWEST, Direction.SOUTHEAST
    );

    public SingleStepPiece(Camp camp, PieceType pieceType) {
        super(camp, pieceType);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardReader boardReader) {
        for (Direction direction : singleStepDirections) {
            if (isTargetPosition(from, to, direction)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTargetPosition(Position from, Position to, Direction direction) {
        if (!from.canMove(direction)) {
            return false;
        }

        return from.move(direction).equals(to);
    }
}
