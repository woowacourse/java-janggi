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

    public Palace(Map<Team, Set<Position>> area, Map<Position, List<Delta>> diagonalMovables) {
        this.area = area;
        this.diagonalMovables = diagonalMovables;
    }


    public boolean inAllyPalace(Position position, Team team) {
        return area.get(team).contains(position);
    }

    public boolean inAnyPalace(Position position) {
        return area.values().stream()
                .anyMatch(set -> set.contains(position));
    }


    public List<Delta> getDiagonalDeltas(Position position) {
        return diagonalMovables.getOrDefault(position, List.of());
    }
}
