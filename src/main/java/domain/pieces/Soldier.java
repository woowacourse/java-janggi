package domain.pieces;

import domain.*;

import java.util.List;

public class Soldier extends Piece {

    private final List<List<Direction>> soliderDirections = MoveDirection.ofSoldier(this.getCamp());

    public Soldier(Camp camp) {
        super(camp, PieceType.SOLDIER);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardReader boardReader) {
        for (List<Direction> directions : soliderDirections) {
            List<Position> path = Route.path(from, directions);
            if (path.size() == 1 && path.getLast().equals(to)) {
                return true;
            }
        }
        return false;
    }
}
