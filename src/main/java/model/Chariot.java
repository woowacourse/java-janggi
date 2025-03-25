package model;

import java.awt.Point;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Chariot {

    private final Color team;

    public Chariot(Color team) {
        this.team = team;
    }

    public Set<Point> calculateMovePath(Point point, Map<Point, Color> existBoardPositions) {
        Set<Point> path = new HashSet<>();
        for (int i = point.x + 1; i < 10; ++i) {
            Point nextPoint = new Point(i, point.y);
            if (!existBoardPositions.containsKey(nextPoint)) {
                path.add(nextPoint);
            } else {
                if (!existBoardPositions.get(nextPoint).equals(team)) {
                    path.add(nextPoint);
                }
                break;
            }
        }
        for (int i = point.x - 1; i > 0; --i) {
            Point nextPoint = new Point(i, point.y);
            if (!existBoardPositions.containsKey(nextPoint)) {
                path.add(nextPoint);
            } else {
                if (!existBoardPositions.get(nextPoint).equals(team)) {
                    path.add(nextPoint);
                }
                break;
            }
        }
        for (int i = point.y - 1; i > 0; --i) {
            Point nextPoint = new Point(point.x, i);
            if (!existBoardPositions.containsKey(nextPoint)) {
                path.add(nextPoint);
            } else {
                if (!existBoardPositions.get(nextPoint).equals(team)) {
                    path.add(nextPoint);
                }
                break;
            }

        }
        for (int i = point.y + 1; i < 11; ++i) {
            Point nextPoint = new Point(point.x, i);
            if (!existBoardPositions.containsKey(nextPoint)) {
                path.add(nextPoint);
            } else {
                if (!existBoardPositions.get(nextPoint).equals(team)) {
                    path.add(nextPoint);
                }
                break;
            }

        }
        return path;
    }
}
