package domain.strategy;

import domain.Team;
import domain.position.Position;
import domain.board.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class PawnStrategy implements Strategy {

    @Override
    public List<Direction> getDirections() {
        return List.of(Direction.NORTH, Direction.EAST, Direction.WEST);
    }

    @Override
    public List<Position> getMoveCandidates(Position from, Team team, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();

        for (Direction direction : getDirections()) {
            int targetRow = from.row() + direction.getRowOffset(team);
            int targetColumns = from.col() + direction.getColOffset(team);

            Position targetPosition = new Position(targetRow, targetColumns);
            candidates.add(targetPosition);
        }
        return candidates;
    }
}
