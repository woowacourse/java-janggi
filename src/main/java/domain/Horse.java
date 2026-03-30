package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Horse extends Piece {
    private final String name = "마";

    public Horse(Side side) {
        super(side);
    }

    @Override
    public List<Position> getAllPosition(Position position) {
        List<Position> positions = new ArrayList<>();

        // 상
        if (position.upPossible()) {
            Position currentPosition = position.up(); // 애초에 넘길때 가능한지 체크하고 만들면 조건문 하나 줄일 수 있지!
            positions.add(currentPosition);
            if (currentPosition.rightUpPossible()) {
                positions.add(currentPosition.rightUp());
            }
            if (currentPosition.leftUpPossible()) {
                positions.add(currentPosition.leftUp());
            }
        }
        // 하
        if (position.downPossible()) {
            Position currentPosition = position.down();
            positions.add(currentPosition);
            if (currentPosition.rightDownPossible()) {
                positions.add(currentPosition.rightDown());
            }
            if (currentPosition.leftDownPossible()) {
                positions.add(currentPosition.leftDown());
            }
        }
        // 좌
        if (position.leftPossible()) {
            Position currentPosition = position.left();
            positions.add(currentPosition);
            if (currentPosition.leftUpPossible()) {
                positions.add(currentPosition.leftUp());
            }
            if (currentPosition.leftDownPossible()) {
                positions.add(currentPosition.leftDown());
            }
        }
        // 우
        if (position.rightPossible()) {
            Position currentPosition = position.right();
            positions.add(currentPosition);
            if (currentPosition.rightUpPossible()) {
                positions.add(currentPosition.rightUp());
            }
            if (currentPosition.rightDownPossible()) {
                positions.add(currentPosition.rightDown());
            }
        }

        return positions;
    }

    @Override
    public List<Position> getPossibleDestinations(Position position, Map<Position, Piece> map) {
        List<Position> destinations = new ArrayList<>();

        // 상
        if (position.upPossible()) {
            Position currentPosition = position.up();
            if (!map.containsKey(currentPosition)) {
                if (currentPosition.rightUpPossible() && (!map.containsKey(currentPosition.rightUp()) || !map.get(currentPosition.rightUp()).isAlly(side))) {
                    destinations.add(currentPosition.rightUp());
                }
                if (currentPosition.leftUpPossible() && (!map.containsKey(currentPosition.leftUp()) || !map.get(currentPosition.leftUp()).isAlly(side))) {
                    destinations.add(currentPosition.leftUp());
                }
            }
        }

        // 하
        if (position.downPossible()) {
            Position currentPosition = position.down();
            if (!map.containsKey(currentPosition)) {
                if (currentPosition.rightDownPossible() && (!map.containsKey(currentPosition.rightDown()) || !map.get(currentPosition.rightDown()).isAlly(side))) {
                    destinations.add(currentPosition.rightDown());
                }
                if (currentPosition.leftDownPossible() && (!map.containsKey(currentPosition.leftDown())  || !map.get(currentPosition.leftDown()).isAlly(side))) {
                    destinations.add(currentPosition.leftDown());
                }
            }
        }

        // 좌
        if (position.leftPossible()) {
            Position currentPosition = position.left();
            if (!map.containsKey(currentPosition)) {
                if (currentPosition.leftUpPossible() && (!map.containsKey(currentPosition.leftUp()) || !map.get(currentPosition.leftUp()).isAlly(side))) {
                    destinations.add(currentPosition.leftUp());
                }
                if (currentPosition.leftDownPossible() && (!map.containsKey(currentPosition.leftDown()) || !map.get(currentPosition.leftDown()).isAlly(side))) {
                    destinations.add(currentPosition.leftDown());
                }
            }
        }

        // 우
        if (position.rightPossible()) {
            Position currentPosition = position.right();
            if (!map.containsKey(currentPosition)) {
                if (currentPosition.rightUpPossible() && (!map.containsKey(currentPosition.rightUp()) || !map.get(currentPosition.rightUp()).isAlly(side))) {
                    destinations.add(currentPosition.rightUp());
                }
                if (currentPosition.rightDownPossible() && (!map.containsKey(currentPosition.rightDown()) || !map.get(currentPosition.rightDown()).isAlly(side))) {
                    destinations.add(currentPosition.rightDown());
                }
            }
        }

        return destinations;
    }

    @Override
    public String toString() {
        return name;
    }
}
