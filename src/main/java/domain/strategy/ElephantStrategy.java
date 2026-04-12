package domain.strategy;

import domain.Team;
import domain.position.Position;
import domain.board.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class ElephantStrategy implements Strategy {

    @Override
    public List<Direction> getDirections() {
        return List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }

    @Override
    public List<Position> getMoveCandidates(Position from, Team team, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        for (Direction straight : getDirections()) {
            addCandidatesForStraight(from, team, board, straight, candidates);
        }
        return candidates;
    }

    private void addCandidatesForStraight(Position from, Team team, PieceProvider board,
                                          Direction straight, List<Position> candidates) {
        Position myeok1 = move(from, straight, team);
        if (!board.isBlank(myeok1)) return;
        addCandidatesForDiagonals(myeok1, team, board, straight, candidates);
    }

    private void addCandidatesForDiagonals(Position myeok1, Team team, PieceProvider board,
                                           Direction straight, List<Position> candidates) {
        for (Direction diag : getDiagonalsFor(straight)) {
            Position myeok2 = move(myeok1, diag, team);
            if (!board.isBlank(myeok2)) continue;
            candidates.add(move(myeok2, diag, team));
        }
    }

    private Position move(Position pos, Direction dir, Team team) {
        return new Position(
                pos.row() + dir.getRowOffset(team),
                pos.col() + dir.getColOffset(team)
        );
    }

    private List<Direction> getDiagonalsFor(Direction straight) {
        if (straight == Direction.NORTH) return List.of(Direction.NORTH_WEST, Direction.NORTH_EAST);
        if (straight == Direction.SOUTH) return List.of(Direction.SOUTH_WEST, Direction.SOUTH_EAST);
        if (straight == Direction.WEST) return List.of(Direction.NORTH_WEST, Direction.SOUTH_WEST);
        if (straight == Direction.EAST) return List.of(Direction.NORTH_EAST, Direction.SOUTH_EAST);
        return List.of();
    }
}
