package domain;

import domain.Position;
import domain.Side;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Chariot extends Piece {
    private final String name = "차";

    public Chariot(Side side) {
        super(side);
    }

    @Override
    public List<Position> getAllPosition(Position position) {
        List<Position> positions = new ArrayList<>();
        // 상
        Position curPosition = position;
        while (curPosition.upPossible()) {
            curPosition = curPosition.up();
            positions.add(curPosition);
        }
        // 하
        curPosition = position;
        while (curPosition.downPossible()) {
            curPosition = curPosition.down();
            positions.add(curPosition);
        }
        // 좌
        curPosition = position;
        while (curPosition.leftPossible()) {
            curPosition = curPosition.left();
            positions.add(curPosition);
        }
        // 우
        curPosition = position;
        while (curPosition.rightPossible()) {
            curPosition = curPosition.right();
            positions.add(curPosition);
        }

        return positions;
    }

    @Override
    public List<Position> getPossibleDestinations(Position position, Map<Position, Piece> board) {
        List<Position> destinations = new ArrayList<>();

        // 상
        Position curPosition = position;
        while (curPosition.upPossible()) {
            curPosition = curPosition.up();
            if (board.containsKey(curPosition)) {
                Piece piece = board.get(curPosition);
                if (!piece.isSameSideAs(side)) {
                    destinations.add(curPosition);
                }
                break;
            }
            destinations.add(curPosition);
        }
        // 하
        curPosition = position;
        while (curPosition.downPossible()) {
            curPosition = curPosition.down();
            if (board.containsKey(curPosition)) {
                Piece piece = board.get(curPosition);
                if (!piece.isSameSideAs(side)) {
                    destinations.add(curPosition);
                }
                break;
            }
            destinations.add(curPosition);
        }
        // 좌
        curPosition = position;
        while (curPosition.leftPossible()) {
            curPosition = curPosition.left();
            if (board.containsKey(curPosition)) {
                Piece piece = board.get(curPosition);
                if (!piece.isSameSideAs(side)) {
                    destinations.add(curPosition);
                }
                break;
            }
            destinations.add(curPosition);
        }
        // 우
        curPosition = position;
        while (curPosition.rightPossible()) {
            curPosition = curPosition.right();
            if (board.containsKey(curPosition)) {
                Piece piece = board.get(curPosition);
                if (!piece.isSameSideAs(side)) {
                    destinations.add(curPosition);
                }
                break;
            }
            destinations.add(curPosition);
        }

        return destinations;
    }

    @Override
    public String toString() {
        return name;
    }
}
