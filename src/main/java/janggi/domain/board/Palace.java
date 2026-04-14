package janggi.domain.board;

import janggi.domain.Direction;
import janggi.domain.vo.position.Path;
import janggi.domain.vo.position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static janggi.domain.Direction.*;

public class Palace {
    private final Map<Position, List<Direction>> moveCandidates;

    public Palace(Map<Position, List<Direction>> moveCandidates) {
        this.moveCandidates = moveCandidates;
    }

    public static Palace creatAllPalace() {
        Map<Position, List<Direction>> merged = hanPalace();
        merged.putAll(choPalace());

        return new Palace(merged);
    }

    public static Palace createHanPalace() {
        return new Palace(hanPalace());
    }

    public static Palace createChoPalace() {
        return new Palace(choPalace());
    }

    private static HashMap<Position, List<Direction>> choPalace() {
        return new HashMap<>() {
            {
                put(new Position(7, 3), List.of(SOUTH, EAST, SOUTH_EAST));
                put(new Position(7, 4), List.of(SOUTH, WEST, EAST));
                put(new Position(7, 5), List.of(WEST, SOUTH, SOUTH_WEST));
                put(new Position(8, 3), List.of(NORTH, SOUTH, EAST));
                put(new Position(8, 4), List.of(NORTH, SOUTH, EAST, WEST, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST));
                put(new Position(8, 5), List.of(NORTH, SOUTH, WEST));
                put(new Position(9, 3), List.of(NORTH, EAST, NORTH_EAST));
                put(new Position(9, 4), List.of(NORTH, EAST, WEST));
                put(new Position(9, 5), List.of(NORTH, WEST, NORTH_WEST));
            }
        };
    }

    private static Map<Position, List<Direction>> hanPalace() {
        return new HashMap<>() {
            {
                put(new Position(0, 3), List.of(SOUTH, EAST, SOUTH_EAST));
                put(new Position(0, 4), List.of(SOUTH, WEST, EAST));
                put(new Position(0, 5), List.of(WEST, SOUTH, SOUTH_WEST));
                put(new Position(1, 3), List.of(NORTH, SOUTH, EAST));
                put(new Position(1, 4), List.of(NORTH, SOUTH, EAST, WEST, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST));
                put(new Position(1, 5), List.of(NORTH, SOUTH, WEST));
                put(new Position(2, 3), List.of(NORTH, EAST, NORTH_EAST));
                put(new Position(2, 4), List.of(NORTH, EAST, WEST));
                put(new Position(2, 5), List.of(NORTH, WEST, NORTH_WEST));
            }
        };
    }

    public boolean isOnDiagonalPath(Position from, Position to) {
        Direction direction = Direction.between(from, to);
        return isContain(from) && isContain(to) && direction.isDiagonal() && moveCandidates.get(from).contains(direction);
    }

    public boolean isContain(Position position) {
        return moveCandidates.containsKey(position);
    }

    public boolean canInnerGo(Position from, Position to) {
        Direction direction = Direction.between(from, to);
        return moveCandidates.containsKey(to) && moveCandidates.get(from).contains(direction) && Path.countOfPositionBetween(from, to) == 1;
    }
}