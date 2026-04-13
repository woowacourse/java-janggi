package domain.piece;

import domain.board.BoardReader;
import domain.position.Direction;
import domain.position.Position;
import domain.position.Route;

import java.util.List;

public abstract class MultiStepPiece extends Piece {

    public MultiStepPiece(Camp camp, PieceType pieceType) {
        super(camp, pieceType);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardReader boardReader) {
        for (List<Direction> directions : getMoveDirections()) {
            List<Position> path = Route.path(from, directions);
            if (path.size() == getRequiredPathSize() && path.getLast().equals(to)) {
                return checkPositionExist(boardReader, path);
            }
        }
        return false;
    }

    private static boolean checkPositionExist(BoardReader boardReader, List<Position> path) {
        for (int i = 0; i < path.size() - 1; i++) {
            if (boardReader.isExist(path.get(i))) {
                return false;
            }
        }
        return true;
    }

    protected abstract List<List<Direction>> getMoveDirections();
    protected abstract int getRequiredPathSize();
}
