package position;

import game.Board;
import java.util.Collections;
import java.util.List;

public record Path(List<Position> positions) {

    @Override
    public List<Position> positions() {
        return Collections.unmodifiableList(positions);
    }

    public boolean isEmpty() {
        return positions.isEmpty();
    }

    public Position getLast() {
        if (isEmpty()) {
            throw new IllegalStateException("경로가 비어 있습니다.");
        }
        return positions.get(positions.size() - 1);
    }

    public Path withoutLast() {
        if (positions.size() <= 1) {
            return new Path(List.of());
        }
        return new Path(positions.subList(0, positions.size() - 1));
    }

    public boolean contains(Position position) {
        return positions.contains(position);
    }

    public void validateNoObstacles(Board board) {
        if (positions.stream().anyMatch(board::hasPieceAt)) {
            throw new IllegalArgumentException("중간에 기물이 있어 갈 수 없습니다.");
        }
    }

    public boolean isDestination(Position toPosition) {
        return !positions.isEmpty() && getLast().equals(toPosition);
    }
}
