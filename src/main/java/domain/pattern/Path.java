package domain.pattern;

import java.util.List;
import java.util.Map;

public class Path {
    protected Map<Direction, List<Pattern>> paths;

    public Path(Map<Direction, List<Pattern>> paths) {
        this.paths = paths;
    }

    public List<Pattern> getPatterns(Direction direction) {
        return paths.get(direction);
    }

    public Map<Direction, List<Pattern>> getPaths() {
        return paths;
    }
}
