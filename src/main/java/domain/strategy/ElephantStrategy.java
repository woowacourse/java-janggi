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
        Position firstMyeok = from.next(straight.getRowOffset(team), straight.getColOffset(team));
        if (!board.getPiece(firstMyeok).isBlank()) return;
        addCandidatesForDiagonals(firstMyeok, team, board, straight, candidates);
    }

    private void addCandidatesForDiagonals(Position firstMyeok, Team team, PieceProvider board,
                                           Direction straight, List<Position> candidates) {
        for (Direction diag : getDiagonalsFor(straight)) {
            Position secondMyeok = firstMyeok.next(diag.getRowOffset(team), diag.getColOffset(team));
            if (!board.getPiece(secondMyeok).isBlank()) return;
            Position target = secondMyeok.next(diag.getRowOffset(team), diag.getColOffset(team));
            if (!board.getPiece(target).isBlank()) return;
            candidates.add(target);
        }
    }

    private List<Direction> getDiagonalsFor(Direction straight) {
        if (straight == Direction.NORTH) return List.of(Direction.NORTH_WEST, Direction.NORTH_EAST);
        if (straight == Direction.SOUTH) return List.of(Direction.SOUTH_WEST, Direction.SOUTH_EAST);
        if (straight == Direction.WEST) return List.of(Direction.NORTH_WEST, Direction.SOUTH_WEST);
        if (straight == Direction.EAST) return List.of(Direction.NORTH_EAST, Direction.SOUTH_EAST);
        return List.of();
    }
}
