package domain.piece.state;

import domain.JanggiPosition;
import domain.pattern.Path;
import domain.pattern.Pattern;
import java.util.List;
import java.util.Map.Entry;

public abstract class NonContinuousPiece extends Moved {
    public List<Pattern> findMovablePath(Path path, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        return path.getPaths().entrySet().stream()
                .filter(entry -> {
                    List<Pattern> patterns = entry.getValue();
                    JanggiPosition newPosition = beforePosition.move(patterns);

                    return newPosition.equals(afterPosition);
                })
                .findFirst()
                .map(Entry::getValue)
                .orElseThrow(() -> new IllegalStateException("해당 말은 해당 경로로 이동할 수 없습니다."));

    }
}
