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

    abstract List<Direction> findDirections(Position from, Position to);
}
