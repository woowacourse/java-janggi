package domain.pieces;

import domain.*;

import java.util.ArrayList;
import java.util.List;

public class Soldier extends Piece {

    public Soldier(Camp camp) {
        super(camp, PieceType.SOLDIER);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardReader boardReader) {
        List<List<Direction>> soliderDirections = initSoliderDirections();

        for (List<Direction> directions : soliderDirections) {
            List<Position> path = Route.path(from, directions);
            if (path.size() == 1 && path.getLast().equals(to)) {
                return true;
            }
        }
        return false;
    }

    private List<List<Direction>> initSoliderDirections() {
        List<List<Direction>> soliderDirections = new ArrayList<>(
                List.of(
                        List.of(Direction.WEST),
                        List.of(Direction.EAST)
                )
        );
        if(this.isSameCamp(Camp.CHO)) {
            soliderDirections.add(List.of(Direction.NORTH));
        }

        if (this.isSameCamp(Camp.HAN)) {
            soliderDirections.add(List.of(Direction.SOUTH));
        }

        return soliderDirections;
    }
}
