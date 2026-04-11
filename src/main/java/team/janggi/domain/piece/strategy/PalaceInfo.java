package team.janggi.domain.piece.strategy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import team.janggi.domain.Position;

public record PalaceInfo(
    Set<Position> palacePositions,
    Set<Position>...diagonalPaths
) {

    public boolean isDiagonalPath(Collection<Position> path) {
        return Arrays.stream(diagonalPaths)
                .anyMatch(diagonalPath -> diagonalPath.containsAll(path));
    }

    public boolean isInPalace(Position position){
        return palacePositions.contains(position);
    }
}
