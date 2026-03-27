package domain.piece;

import domain.Direction;
import domain.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    protected final PieceInfo pieceInfo;

    public Piece(PieceInfo pieceInfo) {
        this.pieceInfo = pieceInfo;
    }

    public PieceInfo getPieceInfo() {
        return pieceInfo;
    }

    public List<Position> path(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        Position position = from;
        for (Direction direction : findDirections(from, to)) {
            position = position.nextPosition(direction);
            path.add(position);
        }
        return path;
    }

    public List<Direction> findDirections(Position from, Position to) {
        List<Integer> distances = from.calculateDistance(to);
        int x = distances.get(0);
        int y = distances.get(1);

        List<Direction> directions = Direction.findDirections(x, y);
        validateDirections(directions);
        return directions;
    }

    abstract void validateDirections(List<Direction> directions);
}
