package domain.position;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private final List<Position> positions;

    private Route(List<Position> positions) {
        if (positions.isEmpty()) {
            throw new IllegalArgumentException("빈 경로는 존재할 수 없습니다.");
        }
        this.positions = positions;
    }

    public static Route of(List<Position> positions) {
        return new Route(positions);
    }

    public Position searchEndPoint(Position startPoint) {
        int distance = 0;
        Position endPoint = positions.get(0);

        for (Position position : positions) {
            int distanceX = Math.abs(position.getX() - startPoint.getX());
            int distanceY = Math.abs(position.getY() - startPoint.getY());

            if (distance < distanceX + distanceY) {
                distance = distanceX + distanceY;
                endPoint = position;
            }
        }
        return endPoint;
    }

    public List<Position> getPoints() {
        return new ArrayList<>(positions);
    }

    public List<Position> getPointsExceptEndPoint() {
        return positions.subList(0, positions.size() - 1);
    }
}
