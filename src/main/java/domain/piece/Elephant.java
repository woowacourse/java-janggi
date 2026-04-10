package domain.piece;

import domain.*;
import domain.board.BoardReader;
import domain.position.Direction;
import domain.position.MoveDirection;
import domain.position.Position;
import domain.position.Route;

import java.util.List;

public class Elephant extends Piece {

    public Elephant(Camp camp) {
        super(camp, PieceType.ELEPHANT);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardReader boardReader) {
        for (List<Direction> directions : MoveDirection.ofElephant()) {
            List<Position> path = Route.path(from, directions);
            if (path.size() == 3 && path.getLast().equals(to)) {
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
}
