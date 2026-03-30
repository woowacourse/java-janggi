package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cannon extends Piece {
    private final String name = "포";

    public Cannon(Side side) {
        super(side);
    }

    @Override
    public List<Position> getAllPosition(Position position) {
        List<Position> positions = new ArrayList<>();
        // 상
        Position currentPosition = position;
        while (currentPosition.upPossible()) {
            currentPosition = currentPosition.up();
            positions.add(currentPosition);
        }
        // 하
        currentPosition = position;
        while (currentPosition.downPossible()) {
            currentPosition = currentPosition.down();
            positions.add(currentPosition);
        }
        // 좌
        currentPosition = position;
        while (currentPosition.leftPossible()) {
            currentPosition = currentPosition.left();
            positions.add(currentPosition);
        }
        // 우
        currentPosition = position;
        while (currentPosition.rightPossible()) {
            currentPosition = currentPosition.right();
            positions.add(currentPosition);
        }

        return positions;
    }

    @Override
    public List<Position> getPossibleDestinations(Position position, Map<Position, Piece> board) {
        List<Position> destinations = new ArrayList<>();

        // 상
        Position currentPosition = position;
        boolean canPut = false;
        while (currentPosition.upPossible()) {
            currentPosition = currentPosition.up();
            if (board.containsKey(currentPosition)) {
                Piece piece = board.get(currentPosition);
                if (canPut) {
                    if (!piece.isSameSideAs(side) && !(piece instanceof Cannon)) {
                        destinations.add(currentPosition);
                    }
                    break;
                }

                if (piece instanceof Cannon) {
                    break;
                }

                canPut = true;
                continue;
            }

            if (canPut) {
                destinations.add(currentPosition);
            }
        }
        // 하
        currentPosition = position;
        canPut = false;
        while (currentPosition.downPossible()) {
            currentPosition = currentPosition.down();
            if (board.containsKey(currentPosition)) {
                Piece piece = board.get(currentPosition);
                if (canPut) {
                    if (!piece.isSameSideAs(side) && !(piece instanceof Cannon)) {
                        destinations.add(currentPosition);
                    }
                    break;
                }

                if (piece instanceof Cannon) {
                    break;
                }

                canPut = true;
                continue;
            }

            if (canPut) {
                destinations.add(currentPosition);
            }
        }
        // 좌
        currentPosition = position;
        canPut = false;
        while (currentPosition.leftPossible()) {
            currentPosition = currentPosition.left();
            if (board.containsKey(currentPosition)) {
                Piece piece = board.get(currentPosition);
                if (canPut) {
                    if (!piece.isSameSideAs(side) && !(piece instanceof Cannon)) {
                        destinations.add(currentPosition);
                    }
                    break;
                }

                if (piece instanceof Cannon) {
                    break;
                }

                canPut = true;
                continue;
            }

            if (canPut) {
                destinations.add(currentPosition);
            }
        }
        // 우
        currentPosition = position;
        canPut = false;
        while (currentPosition.rightPossible()) {
            currentPosition = currentPosition.right();
            if (board.containsKey(currentPosition)) {
                Piece piece = board.get(currentPosition);
                if (canPut) {
                    if (!piece.isSameSideAs(side) && !(piece instanceof Cannon)) {
                        destinations.add(currentPosition);
                    }
                    break;
                }

                if (piece instanceof Cannon) {
                    break;
                }

                canPut = true;
                continue;
            }

            if (canPut) {
                destinations.add(currentPosition);
            }
        }

        return destinations;
    }

    @Override
    public String toString() {
        return name;
    }
}
