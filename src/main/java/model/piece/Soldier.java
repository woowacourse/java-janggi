package model.piece;

import java.util.List;
import model.Team;
import model.coordinate.Position;

public class Soldier extends Piece {

    public Soldier(Team team) {
        super(team, PieceType.SOLDIER);
    }

    @Override
    public List<Position> extractPath(Position current, Position next) {
        return List.of();
    }

    @Override
    public boolean canMove(Position current, Position next) {
        int rowDiff = next.calculateRowDiff(current);
        int colDiff = Math.abs(next.calculateColDiff(current));
        return isReachable(rowDiff, colDiff);
    }

    @Override
    protected boolean isReachable(int rowDiff, int colDiff) {
        if (isCho()) {
            return (rowDiff == -1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
        }
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
    }
}
