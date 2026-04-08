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

    public boolean contains(Position position) {
        return positions.contains(position);
    }

    public boolean endsAt(Position position) {
        return positions.get(positions.size() - 1).equals(position);
    }

    public List<Position> intermediates() {
        return positions.subList(0, positions.size() - 1);
    }

    public List<Position> positions() {
        return positions;
    }

    public Path subPathTo(Position position) {
        int index = positions.indexOf(position);
        if (index == -1) {
            throw new IllegalArgumentException("[ERROR] 해당 포지션이 경로에 없습니다: " + position);
        }
        return new Path(positions.subList(0, index + 1));
    }
}
