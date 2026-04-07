package domain.piece;

import domain.*;

import java.util.List;

public class Soldier extends Piece {

    public Soldier(Camp camp) {
        super(camp, PieceType.SOLDIER);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardReader boardReader) {
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
            return MoveDirection.ofSoldierInPalace(this.getCamp());
        }

        return MoveDirection.ofSoldier(this.getCamp());
    }
}
