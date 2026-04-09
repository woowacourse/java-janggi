package domain.piece;

import domain.board.Country;
import domain.board.Direction;
import domain.board.Position;
import dto.Distance;
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
        List<Direction> directions = Direction.findDirections(distance.x(), distance.y());

        if (isInPalaceMove(from, to, pieceInfo.country())) {
            validateDirectionsInPalace(directions);
            return directions;
        }
        validateDirections(directions);
        return directions;
    }

    abstract protected void validateDirections(List<Direction> directions);

    protected void validateDirectionsInPalace(List<Direction> directions) {
    }

    public void validateClearPath(List<PieceType> pieceTypes, PieceType destinationPieceType) {
        if (!pieceTypes.isEmpty()) {
            throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
        }
    }

    protected boolean isInPalaceMove(Position from, Position to, Country country) {
        return false;
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
