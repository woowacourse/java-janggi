package model.piece;

import model.Team;
import model.coordinate.Position;

import java.util.List;

public class Soldier extends Piece {

    public Soldier(Team team) {
        super(team, PieceType.SOLDIER);
    }

    @Override
    public List<Position> extractPath(Position current, Position next) {
        return List.of();
    }

    @Override
    protected boolean isReachable(int rowDiff, int colDiff) {
        int absColDiff = Math.abs(colDiff);
        if (isCho()) {
            return (rowDiff == -1 && absColDiff == 0) || (rowDiff == 0 && absColDiff == 1);
        }
        return (rowDiff == 1 && absColDiff == 0) || (rowDiff == 0 && absColDiff == 1);
    }
}
