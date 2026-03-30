package domain.board;

import static domain.common.Constant.MAX_COLUMN;
import static domain.common.Constant.MAX_ROW;
import static domain.common.Constant.MIN_COLUMN;
import static domain.common.Constant.MIN_ROW;

import domain.place.Empty;
import domain.place.Place;
import domain.place.piece.PieceSymbol;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Board {

    private final Map<Position, Place> board;

    public Board(Map<Position, Place> board) {
        this.board = new HashMap<>(board);
    }

    public void move(Position from, Position to, Side side) {
        validateMove(from, to, side);

        Place sourcePlace = board.get(from);
        Map<Position, Place> obstacles = getObstacles(sourcePlace.getPath(from));

        if (!sourcePlace.canMove(obstacles, from, to)) {
            throw new IllegalArgumentException("[ERROR] 기물이 갈 수 없는 곳입니다.");
        }

        movePiece(from, to);
    }

    private Map<Position, Place> getObstacles(List<Position> path) {
        return path.stream()
                .map(position -> Map.entry(position, board.get(position)))
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void validateMove(Position from, Position to, Side side) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동 불가합니다.");
        }

        Place sourcePlace = board.get(from);
        Place targetPlace = board.get(to);

        if (sourcePlace.isEmpty() || !sourcePlace.hasSide(side)) {
            throw new IllegalArgumentException("[ERROR] 잘못된 기물 선택입니다.");
        }

        if (sourcePlace.isSameSide(targetPlace)) {
            throw new IllegalArgumentException("[ERROR] 아군 위치로 이동 불가합니다");
        }
    }

    private void movePiece(Position from, Position to) {
        Place piece = board.get(from);
        board.put(from, new Empty());
        board.put(to, piece);
    }

    public List<List<String>> getFormatBoard() {
        return getBoard(Place::getFormat);
    }

    public List<List<Optional<Side>>> getSideBoard() {
        return getBoard(Place::getSide);
    }

    private <T> List<List<T>> getBoard(Function<Place, T> mapper) {
        List<List<T>> result = new ArrayList<>();
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            result.add(getRow(row, mapper));
        }
        return result;
    }

    private <T> List<T> getRow(int row, Function<Place, T> mapper) {
        List<T> rowResult = new ArrayList<>();
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            Position position = new Position(row, column);
            Place place = board.get(position);
            rowResult.add(mapper.apply(place));
        }
        return rowResult;
    }
}
