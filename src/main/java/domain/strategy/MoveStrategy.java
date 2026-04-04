package domain.strategy;

import domain.Direction;
import domain.Path;
import domain.Position;
import domain.country.CountryType;
import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import domain.piece.PieceType;
import java.util.List;

public abstract class MoveStrategy {
    private static final String NOT_EMPTY_PATH = "[ERROR] 이동 경로에 다른 기물이 존재해 이동시킬 수 없습니다.";
    private static final String CANNOT_MOVE_SAME_COUNTRY_POSITION = "[ERROR] 같은 진영의 기물이 있는 위치로 이동시킬 수 없습니다.";
    private static final String TRY_GO_BACK = "[ERROR] 졸・병은 후진할 수 없습니다.";

    public Path path(Position from, Position to) {
        Path path = new Path();
        Position position = from;
        path.add(position);
        for (Direction direction : findDirections(from, to)) {
            position = position.nextPosition(direction);
            path.add(position);
        }
        return path;
    }

    List<Direction> findDirections(Position from, Position to) {
        List<Integer> distances = from.calculateDistance(to);
        int x = distances.get(0);
        int y = distances.get(1);

        List<Direction> directions = Direction.findDirections(x, y);
        validateDirections(directions, from, to);
        return directions;
    }

    abstract void validateDirections(List<Direction> directions, Position from, Position to);

    public void validateMove(PieceInfos pathPieceInfos, Position from, Position to) {
        validateToPiece(pathPieceInfos, from, to);
        // from, to Piece 제외한 path 검사
        pathPieceInfos.deleteFromAndTo(from, to);
        validatePath(pathPieceInfos);
    }

    void validateToPiece(PieceInfos pathPieceInfos, Position from, Position to) {
        if (pathPieceInfos.isEmptyPosition(to)) {
            return;
        }
        PieceInfo fromPiece = pathPieceInfos.get(from);
        PieceInfo toPiece = pathPieceInfos.get(to);
        if (fromPiece.countryType() == toPiece.countryType()) {
            throw new IllegalArgumentException(CANNOT_MOVE_SAME_COUNTRY_POSITION);
        }
        if (fromPiece.pieceType() == PieceType.SOLDIER) {
            validateSoldierMove(from, to, fromPiece.countryType());
        }
    }

    public void validateSoldierMove(Position from, Position to, CountryType countryType) {
        if (countryType == CountryType.CHO && from.y() > to.y()) {
            throw new IllegalArgumentException(TRY_GO_BACK);
        }
        if (countryType == CountryType.HAN && from.y() < to.y()) {
            throw new IllegalArgumentException(TRY_GO_BACK);
        }
    }

    void validatePath(PieceInfos pathPieceInfos) {
        if (pathPieceInfos.getSize() > 0) {
            throw new IllegalArgumentException(NOT_EMPTY_PATH);
        }
    }
}
