package janggi.model.palace;

import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.UndirectedLine;
import java.util.Set;

public class Palace {

    private final Set<Position> positions;
    private final Set<UndirectedLine> edges;

    public Palace(Set<Position> positions, Set<UndirectedLine> edges) {
        this.positions = positions;
        this.edges = edges;
    }

    public boolean contains(Position position) {
        return positions.contains(position);
    }

    public boolean isAdjacent(Position one, Position other) {
        return edges.contains(new UndirectedLine(one, other));
    }
}
