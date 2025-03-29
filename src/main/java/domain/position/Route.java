package domain.position;

import domain.unit.Team;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Route {

    private final List<Position> positions;

    private Route(List<Position> positions) {
        this.positions = positions;
    }

    public static Route of(List<Position> positions) {
        return new Route(positions);
    }

    public boolean isMovingForward(Position source, Team team) {
        Position destination = searchDestination(source);
        return destination.isForwardOf(source, team);
    }

    public Position searchDestination(Position position) {
        return positions.stream()
                .max(Comparator.comparingDouble(pos -> pos.calculateDistance(position)))
                .orElseThrow(() -> new IllegalArgumentException("비어있는 경로입니다."));
    }

    public List<Position> getPositionsExceptDestination(Position position) {
        Position endPoint = searchDestination(position);
        return positions.stream()
                .filter(pos -> !pos.equals(endPoint))
                .toList();
    }

    public List<Position> getPositions() {
        return Collections.unmodifiableList(positions);
    }
}
