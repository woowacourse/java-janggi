package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Guard extends Piece {
    private final String name = "사";

    public Guard(Side side) {
        super(side);
    }

    @Override
    public List<Position> getAllPosition(Position position) {
        List<Position> positions = new ArrayList<>();

        // 상
        if (position.upPossible()) {
            positions.add(position.up());
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
    public List<Position> getPossibleDestinations(Position position, Map<Position, Piece> board) {
        List<Position> destinations = new ArrayList<>();

        // 상
        if (position.upPossible() && (!board.containsKey(position.up()) || !board.get(position.up()).isSameSideAs(side))) {
            destinations.add(position.up());
        }
        // 하
        if (position.downPossible() && (!board.containsKey(position.down()) || !board.get(position.down()).isSameSideAs(side))) {
            destinations.add(position.down());
        }
        // 좌
        if (position.leftPossible() && (!board.containsKey(position.left()) || !board.get(position.left()).isSameSideAs(side))) {
            destinations.add(position.left());
        }
        // 우
        if (position.rightPossible() && (!board.containsKey(position.right()) || !board.get(position.right()).isSameSideAs(side))) {
            destinations.add(position.right());
        }

        return destinations;
    }

    @Override
    public String toString() {
        return name;
    }
}
