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
        boolean canPut = false;
        while (curPosition.upPossible()) {
            curPosition = curPosition.up();
            if (board.containsKey(curPosition)) {
                Piece piece = board.get(curPosition);
                if (canPut) {
                    if (!piece.isSameSideAs(side) && !(piece instanceof Cannon)) {
                        destinations.add(curPosition);
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
                destinations.add(curPosition);
            }
        }
        // 하
        curPosition = position;
        canPut = false;
        while (curPosition.downPossible()) {
            curPosition = curPosition.down();
            if (board.containsKey(curPosition)) {
                Piece piece = board.get(curPosition);
                if (canPut) {
                    if (!piece.isSameSideAs(side) && !(piece instanceof Cannon)) {
                        destinations.add(curPosition);
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
                destinations.add(curPosition);
            }
        }
        // 좌
        curPosition = position;
        canPut = false;
        while (curPosition.leftPossible()) {
            curPosition = curPosition.left();
            if (board.containsKey(curPosition)) {
                Piece piece = board.get(curPosition);
                if (canPut) {
                    if (!piece.isSameSideAs(side) && !(piece instanceof Cannon)) {
                        destinations.add(curPosition);
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
                destinations.add(curPosition);
            }
        }
        // 우
        curPosition = position;
        canPut = false;
        while (curPosition.rightPossible()) {
            curPosition = curPosition.right();
            if (board.containsKey(curPosition)) {
                Piece piece = board.get(curPosition);
                if (canPut) {
                    if (!piece.isSameSideAs(side) && !(piece instanceof Cannon)) {
                        destinations.add(curPosition);
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
                destinations.add(curPosition);
            }
        }

        return destinations;
    }

    @Override
    public String toString() {
        return name;
    }
}
