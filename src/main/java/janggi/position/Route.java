package janggi.position;

import java.util.List;

public class Route {
    private final List<Direction> directions;

    public Route(List<Direction> directions) {
        this.directions = directions;
    }

//    public Position sum() {
//        int sumX = positions.stream()
//                .mapToInt(Position::x)
//                .sum();
//        int sumY = positions.stream()
//                .mapToInt(Position::y)
//                .sum();
//        return new Position(sumX, sumY);
//    }
}

