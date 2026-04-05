package domain.strategy;

import domain.Team;
import domain.position.Position;
import domain.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class PawnStrategy implements Strategy {

    @Override
    public List<Position> getMoveCandidates(Position from, Team team, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Direction[] directions = {Direction.NORTH, Direction.EAST, Direction.WEST};

        for (Direction direction : directions) {
            int targetRow = from.row() + direction.getRowOffset(team);
            int targetColumns = from.col() + direction.getColOffset(team);

            Position targetPosition = new Position(targetRow, targetColumns);
            candidates.add(targetPosition);
        }
        return candidates;
    }
}
