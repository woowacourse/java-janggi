package domain.pieces;

import domain.*;

import java.util.List;

public class SingleStepPiece extends Piece {

    private final List<List<Direction>> singleStepDirections = MoveDirection.ofAllAround();

    public SingleStepPiece(Camp camp, PieceType pieceType) {
        super(camp, pieceType);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardReader boardReader) {
        for (List<Direction> directions : singleStepDirections) {
            List<Position> path = Route.path(from, directions);
            if (path.size() == 1 && path.getLast().equals(to)) {
                return true;
            }
        }
        return false;
    }
}
