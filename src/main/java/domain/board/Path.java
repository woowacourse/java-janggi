package domain.board;

import domain.piece.Position;
import java.util.List;

public class Path {

    private final List<Position> path;

    public Path(final List<Position> path) {
        this.path = path;
    }

    public List<Position> getPath() {
        return path;
    }
}
