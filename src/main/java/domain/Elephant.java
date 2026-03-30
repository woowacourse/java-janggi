package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Elephant extends Piece {
    private final String name = "상";

    public Elephant(Side side) {
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
                Position rightUpPosition = currentPosition.rightUp();
                positions.add(rightUpPosition);
                if (rightUpPosition.rightUpPossible()) {
                    positions.add(rightUpPosition.rightUp());
                }
            }
            if (currentPosition.leftUpPossible()) {
                Position leftUPosition = currentPosition.leftUp();
                positions.add(leftUPosition);
                if (leftUPosition.leftUpPossible()) {
                    positions.add(leftUPosition.leftUp());
                }
            }
        }
        // 하
        if (position.downPossible()) {
            Position currentPosition = position.down(); // 애초에 넘길때 가능한지 체크하고 만들면 조건문 하나 줄일 수 있지!
            positions.add(currentPosition);
            if (currentPosition.rightDownPossible()) {
                Position rightDownPosition = currentPosition.rightDown();
                positions.add(rightDownPosition);
                if (rightDownPosition.rightDownPossible()) {
                    positions.add(rightDownPosition.rightDown());
                }
            }
            if (currentPosition.leftDownPossible()) {
                Position leftDownPosition = currentPosition.leftDown();
                positions.add(leftDownPosition);
                if (leftDownPosition.leftDownPossible()) {
                    positions.add(leftDownPosition.leftDown());
                }
            }
        }
        // 좌
        if (position.leftPossible()) {
            Position currentPosition = position.left(); // 애초에 넘길때 가능한지 체크하고 만들면 조건문 하나 줄일 수 있지!
            positions.add(currentPosition);
            if (currentPosition.leftUpPossible()) {
                Position leftUpPosition = currentPosition.leftUp();
                positions.add(leftUpPosition);
                if (leftUpPosition.leftUpPossible()) {
                    positions.add(leftUpPosition.leftUp());
                }
            }
            if (currentPosition.leftDownPossible()) {
                Position leftDownPosition = currentPosition.leftDown();
                positions.add(leftDownPosition);
                if (leftDownPosition.leftDownPossible()) {
                    positions.add(leftDownPosition.leftDown());
                }
            }
        }
        // 우
        if (position.rightPossible()) {
            Position currentPosition = position.right(); // 애초에 넘길때 가능한지 체크하고 만들면 조건문 하나 줄일 수 있지!
            positions.add(currentPosition);
            if (currentPosition.rightUpPossible()) {
                Position rightUpPosition = currentPosition.rightUp();
                positions.add(rightUpPosition);
                if (rightUpPosition.rightUpPossible()) {
                    positions.add(rightUpPosition.rightUp());
                }
            }
            if (currentPosition.rightDownPossible()) {
                Position rightDownPosition = currentPosition.rightDown();
                positions.add(rightDownPosition);
                if (rightDownPosition.rightDownPossible()) {
                    positions.add(rightDownPosition.rightDown());
                }
            }
        }

        return positions;
    }

    @Override
    public List<Position> getPossibleDestinations(Position position, Map<Position, Piece> map) {
        List<Position> destinations = new ArrayList<>();

        // 상
        if (position.upPossible() && !map.containsKey(position.up())) {
            Position currentPosition = position.up(); // 애초에 넘길때 가능한지 체크하고 만들면 조건문 하나 줄일 수 있지!
            if (currentPosition.rightUpPossible() && !map.containsKey(currentPosition.rightUp())) {
                Position rightUpPosition = currentPosition.rightUp();
                if (rightUpPosition.rightUpPossible() && (!map.containsKey(rightUpPosition.rightUp()) || !map.get(rightUpPosition.rightUp()).isAlly(side))) {
                    destinations.add(rightUpPosition.rightUp());
                }
            }
            if (currentPosition.leftUpPossible() && !map.containsKey(currentPosition.leftUp())) {
                Position leftUpPosition = currentPosition.leftUp();
                if (leftUpPosition.leftUpPossible() && (!map.containsKey(leftUpPosition.rightUp()) || !map.get(leftUpPosition.leftUp()).isAlly(side))) {
                    destinations.add(leftUpPosition.leftUp());
                }
            }
        }
        // 하
        if (position.downPossible() && !map.containsKey(position.down())) {
            Position currentPosition = position.down(); // 애초에 넘길때 가능한지 체크하고 만들면 조건문 하나 줄일 수 있지!
            if (currentPosition.rightDownPossible() && !map.containsKey(currentPosition.rightDown())) {
                Position rightDownPosition = currentPosition.rightDown();
                if (rightDownPosition.rightDownPossible() && (!map.containsKey(rightDownPosition.rightDown()) || !map.get(rightDownPosition.rightDown()).isAlly(side))) {
                    destinations.add(rightDownPosition.rightDown());
                }
            }
            if (currentPosition.leftDownPossible() && !map.containsKey(currentPosition.leftDown())) {
                Position leftDownPosition = currentPosition.leftDown();
                if (leftDownPosition.leftDownPossible() && (!map.containsKey(leftDownPosition.leftDown()) || !map.get(leftDownPosition.leftDown()).isAlly(side))) {
                    destinations.add(leftDownPosition.leftDown());
                }
            }
        }
        // 좌
        if (position.leftPossible() && !map.containsKey(position.left())) {
            Position currentPosition = position.left(); // 애초에 넘길때 가능한지 체크하고 만들면 조건문 하나 줄일 수 있지!
            if (currentPosition.leftUpPossible() && !map.containsKey(currentPosition.leftUp())) {
                Position leftUpPosition = currentPosition.leftUp();
                if (leftUpPosition.leftUpPossible() && (!map.containsKey(leftUpPosition.leftUp()) || !map.get(leftUpPosition.leftUp()).isAlly(side))) {
                    destinations.add(leftUpPosition.leftUp());
                }
            }
            if (currentPosition.leftDownPossible() && !map.containsKey(currentPosition.leftDown())) {
                Position leftDownPosition = currentPosition.leftDown();
                if (leftDownPosition.leftDownPossible() && (!map.containsKey(leftDownPosition.leftDown()) || !map.get(leftDownPosition.leftDown()).isAlly(side))) {
                    destinations.add(leftDownPosition.leftDown());
                }
            }
        }
        // 우
        if (position.rightPossible() && !map.containsKey(position.right())) {
            Position currentPosition = position.right(); // 애초에 넘길때 가능한지 체크하고 만들면 조건문 하나 줄일 수 있지!
            if (currentPosition.rightUpPossible() && !map.containsKey(currentPosition.rightUp())) {
                Position rightUpPosition = currentPosition.rightUp();
                if (rightUpPosition.rightUpPossible() && (!map.containsKey(rightUpPosition.rightUp()) || !map.get(rightUpPosition.rightUp()).isAlly(side))) {
                    destinations.add(rightUpPosition.rightUp());
                }
            }
            if (currentPosition.rightDownPossible() && !map.containsKey(currentPosition.rightDown())) {
                Position rightDownPosition = currentPosition.rightDown();
                if (rightDownPosition.rightDownPossible() && (!map.containsKey(rightDownPosition.rightDown()) || !map.get(rightDownPosition.rightDown()).isAlly(side))) {
                    destinations.add(rightDownPosition.rightDown());
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
