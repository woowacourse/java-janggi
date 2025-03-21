package domain.pattern;

import java.util.List;
import java.util.Map;

public abstract class Path {
    protected List<Direction> pattern;
    protected Map<Direction, List<Pattern>> paths;

    protected Path(List<Direction> pattern, Map<Direction, List<Pattern>> paths) {
        this.pattern = pattern;
        this.paths = paths;
    }

    public List<Pattern> getPatterns(Direction direction) {
        return paths.get(direction);
    }

    public Map<Direction, List<Pattern>> getPaths() {
        return paths;
    }
}
