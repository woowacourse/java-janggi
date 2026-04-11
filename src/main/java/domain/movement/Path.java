package domain.movement;

import domain.board.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Path {
    private final List<Position> positions;

    public Path(List<Position> positions) {
        if (positions == null || positions.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 경로는 적어도 하나 이상의 포지션을 가져야 합니다.");
        }
        this.positions = Collections.unmodifiableList(new ArrayList<>(positions));
    }

    public List<Position> positions() {
        return positions;
    }

    public List<Position> positionsBeforeDestination() {
        return positions.subList(0, positions.size() - 1);
    }

    public Position destination() {
        return positions.getLast();
    }
}
