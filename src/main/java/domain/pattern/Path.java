package domain.pattern;

import domain.Position;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class Path {
    protected List<Direction> pattern;
    protected Map<Direction, List<Pattern>> paths;

    protected Path(List<Direction> pattern, Map<Direction, List<Pattern>> paths) {
        this.pattern = pattern;
        this.paths = paths;
    }

    public List<Pattern> getPath(Position beforePosition, Position afterPosition) {
        return paths.entrySet().stream()
                .filter(entry -> {
                    List<Pattern> patterns = entry.getValue();
                    Position newPosition = beforePosition.move(patterns);

                    return newPosition.equals(afterPosition);
                })
                .findFirst()
                .map(Entry::getValue)
                .orElseThrow(() -> new IllegalStateException("해당 말은 해당 경로로 이동할 수 없습니다."));
    }

    public List<Pattern> getPatterns(Direction direction) {
        return paths.get(direction);
    }
}
