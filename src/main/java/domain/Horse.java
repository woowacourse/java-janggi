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
            Position curPosition = position.up(); // 애초에 넘길때 가능한지 체크하고 만들면 조건문 하나 줄일 수 있지!
            positions.add(curPosition);
            if (curPosition.rightUpPossible()) {
                positions.add(curPosition.rightUp());
            }
            if (curPosition.leftUpPossible()) {
                positions.add(curPosition.leftUp());
            }
        }
        // 하
        if (position.downPossible()) {
            Position curPosition = position.down();
            positions.add(curPosition);
            if (curPosition.rightDownPossible()) {
                positions.add(curPosition.rightDown());
            }
            if (curPosition.leftDownPossible()) {
                positions.add(curPosition.leftDown());
            }
        }
        // 좌
        if (position.leftPossible()) {
            Position curPosition = position.left();
            positions.add(curPosition);
            if (curPosition.leftUpPossible()) {
                positions.add(curPosition.leftUp());
            }
            if (curPosition.leftDownPossible()) {
                positions.add(curPosition.leftDown());
            }
        }
        // 우
        if (position.rightPossible()) {
            Position curPosition = position.right();
            positions.add(curPosition);
            if (curPosition.rightUpPossible()) {
                positions.add(curPosition.rightUp());
            }
            if (curPosition.rightDownPossible()) {
                positions.add(curPosition.rightDown());
            }
        }

        return positions;
    }

    @Override
    public List<Position> getPossibleDestinations(Position position, Map<Position, Piece> map) {
        List<Position> destinations = new ArrayList<>();

        // 상
        if (position.upPossible()) {
            Position curPosition = position.up();
            if (!map.containsKey(curPosition)) {
                if (curPosition.rightUpPossible() && (!map.containsKey(curPosition.rightUp()) || !map.get(curPosition.rightUp()).isSameSideAs(side))) {
                    destinations.add(curPosition.rightUp());
                }
                if (curPosition.leftUpPossible() && (!map.containsKey(curPosition.leftUp()) || !map.get(curPosition.leftUp()).isSameSideAs(side))) {
                    destinations.add(curPosition.leftUp());
                }
            }
        }

        // 하
        if (position.downPossible()) {
            Position curPosition = position.down();
            if (!map.containsKey(curPosition)) {
                if (curPosition.rightDownPossible() && (!map.containsKey(curPosition.rightDown()) || !map.get(curPosition.rightDown()).isSameSideAs(side))) {
                    destinations.add(curPosition.rightDown());
                }
                if (curPosition.leftDownPossible() && (!map.containsKey(curPosition.leftDown())  || !map.get(curPosition.leftDown()).isSameSideAs(side))) {
                    destinations.add(curPosition.leftDown());
                }
            }
        }

        // 좌
        if (position.leftPossible()) {
            Position curPosition = position.left();
            if (!map.containsKey(curPosition)) {
                if (curPosition.leftUpPossible() && (!map.containsKey(curPosition.leftUp()) || !map.get(curPosition.leftUp()).isSameSideAs(side))) {
                    destinations.add(curPosition.leftUp());
                }
                if (curPosition.leftDownPossible() && (!map.containsKey(curPosition.leftDown()) || !map.get(curPosition.leftDown()).isSameSideAs(side))) {
                    destinations.add(curPosition.leftDown());
                }
            }
        }

        // 우
        if (position.rightPossible()) {
            Position curPosition = position.right();
            if (!map.containsKey(curPosition)) {
                if (curPosition.rightUpPossible() && (!map.containsKey(curPosition.rightUp()) || !map.get(curPosition.rightUp()).isSameSideAs(side))) {
                    destinations.add(curPosition.rightUp());
                }
                if (curPosition.rightDownPossible() && (!map.containsKey(curPosition.rightDown()) || !map.get(curPosition.rightDown()).isSameSideAs(side))) {
                    destinations.add(curPosition.rightDown());
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
