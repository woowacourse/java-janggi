package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Soldier extends Piece {
    private final String name = "졸";

    public Soldier(Side side) {
        super(side);
    }

    @Override
    public List<Position> getAllPosition(Position position) {
        List<Position> positions = new ArrayList<>();

        if (side.isCho()) {
            // 상
            if (position.upPossible()) {
                positions.add(position.up());
            }
            // 좌
            if (position.leftPossible()) {
                positions.add(position.left());
            }
            // 우
            if (position.rightPossible()) {
                positions.add(position.right());
            }
            return positions;
        }

        // 하
        if (position.downPossible()) {
            positions.add(position.down());
        }
        // 좌
        if (position.leftPossible()) {
            positions.add(position.left());
        }
        // 우
        if (position.rightPossible()) {
            positions.add(position.right());
        }
        return positions;
    }

    @Override
    public List<Position> getPossibleDestinations(Position position, Map<Position, Piece> map) {
        List<Position> destinations = new ArrayList<>();

        if (side.isCho()) {
            // 상
            if (position.upPossible() && (!map.containsKey(position.up()) || !map.get(position.up()).isAlly(side))) {
                destinations.add(position.up());
            }
            // 좌
            if (position.leftPossible() && (!map.containsKey(position.left()) || !map.get(position.left()).isAlly(side))) {
                destinations.add(position.left());
            }
            // 우
            if (position.rightPossible() && (!map.containsKey(position.right()) || !map.get(position.right()).isAlly(side))) {
                destinations.add(position.right());
            }
            return destinations;
        }

        // 하
        if (position.downPossible() && (!map.containsKey(position.down()) || !map.get(position.down()).isAlly(side))) {
            destinations.add(position.down());
        }
        // 좌
        if (position.leftPossible() && (!map.containsKey(position.left()) || !map.get(position.left()).isAlly(side))) {
            destinations.add(position.left());
        }
        // 우
        if (position.rightPossible() && (!map.containsKey(position.right()) || !map.get(position.right()).isAlly(side))) {
            destinations.add(position.right());
        }

        return destinations;
    }

    @Override
    public String toString() {
        return name;
    }
}
