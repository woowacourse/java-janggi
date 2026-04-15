package domain.board.palace;

import domain.coordination.Coordination;
import java.util.List;

public class PalaceRoute {

    private static final PalaceRoute EMPTY = new PalaceRoute(false, List.of());

    private final boolean exists;
    private final List<Coordination> path;

    private PalaceRoute(boolean exists, List<Coordination> path) {
        this.exists = exists;
        this.path = path;
    }

    public static PalaceRoute empty() {
        return EMPTY;
    }

    public static PalaceRoute of(List<Coordination> path) {
        return new PalaceRoute(true, path);
    }

    public boolean exists() {
        return exists;
    }

    public List<Coordination> path() {
        return path;
    }
}
