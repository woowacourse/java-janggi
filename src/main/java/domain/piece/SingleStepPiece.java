package domain.piece;

import domain.*;
import domain.board.BoardReader;
import domain.board.Palace;
import domain.position.Direction;
import domain.position.MoveDirection;
import domain.position.Position;
import domain.position.Route;

import java.util.List;

public abstract class SingleStepPiece extends Piece {

    public SingleStepPiece(Camp camp, PieceType pieceType) {
        super(camp, pieceType);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardReader boardReader) {
        if(!Palace.isPalacePosition(from) || !Palace.isPalacePosition(to)) {
            return false;
        }

        for (Direction direction : getDirections(from, to)) {
            List<Position> path = Route.path(from, direction);
            if (path.size() == 1 && path.getLast().equals(to)) {
                return true;
            }
        }

        return false;
    }

    private List<Direction> getDirections(Position from, Position to) {
        if (Palace.isGeneralPosition(from) || Palace.isGeneralPosition(to)) {
            return MoveDirection.ofAllAround();
        }

        return MoveDirection.ofLinear();
    }
}
