package domain.board;

import domain.piece.Delta;
import domain.piece.Position;
import domain.player.Team;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Palace {

    private final Map<Team, Set<Position>> area;
    private final Map<Position, List<Delta>> diagonalMovables;

    public Palace(final Map<Team, Set<Position>> area, final Map<Position, List<Delta>> diagonalMovables) {
        this.area = area;
        this.diagonalMovables = diagonalMovables;
    }


    public boolean inAllyPalace(final Position position, final Team team) {
        return area.get(team).contains(position);
    }

    public boolean inAnyPalace(final Position position) {
        return area.values().stream()
                .anyMatch(set -> set.contains(position));
    }


    public List<Delta> getDiagonalDeltas(final Position position) {
        return diagonalMovables.getOrDefault(position, List.of());
    }
}
