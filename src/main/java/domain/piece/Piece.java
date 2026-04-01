package domain.piece;

import domain.Board;
import domain.Country;
import domain.Direction;
import domain.Distance;
import domain.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private static final String CAN_NOT_MOVE_TO_POSITION = "[ERROR] 해당 경로로 기물을 이동시킬 수 없습니다.";

    protected final PieceInfo pieceInfo;

    public Piece(PieceInfo pieceInfo) {
        this.pieceInfo = pieceInfo;
    }

    public List<Position> findPaths(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        Position position = from;
        for (Direction direction : findMovingDirections(from, to)) {
            position = position.nextPosition(direction);
            path.add(position);
        }
        return path;
    }

    public List<Direction> findMovingDirections(Position from, Position to) {
        Distance distance = from.calculateDistance(to);
        int x = distance.x();
        int y = distance.y();

        List<Direction> directions = Direction.findDirections(x, y);
        validateDirections(directions);
        return directions;
    }

    abstract protected void validateDirections(List<Direction> directions);

    public void validateClearPath(List<Position> paths, Board board) {
        for (int index = 0; index < paths.size() - 1; index++) {
            if (!board.isEmpty(paths.get(index))) {
                throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
            }
        }
    }

    public PieceInfo getPieceInfo() {
        return pieceInfo;
    }

    public PieceType getPieceType() {
        return pieceInfo.pieceType();
    }

    public Country getPieceCountry() {
        return pieceInfo.country();
    }
}
