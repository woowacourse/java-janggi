package domain.strategy;

import domain.Team;
import domain.position.Position;
import domain.board.PieceProvider;

import java.util.List;
import java.util.stream.Collectors;

public class PawnStrategy implements Strategy {

    @Override
    public List<Direction> getDirections() {
        return List.of(Direction.NORTH, Direction.EAST, Direction.WEST);
    }

    @Override
    public List<Position> getMoveCandidates(Position from, Team team, PieceProvider board) {
        return getDirections().stream()
                .map(direction -> from.next(direction.getRowOffset(team), direction.getColOffset(team)))
                .collect(Collectors.toList());
    }
}
