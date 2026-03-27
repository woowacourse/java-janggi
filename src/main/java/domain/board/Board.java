package domain.board;

import static domain.common.Constant.MAX_COLUMN;
import static domain.common.Constant.MAX_ROW;
import static domain.common.Constant.MIN_COLUMN;
import static domain.common.Constant.MIN_ROW;

import domain.place.Empty;
import domain.place.Place;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements BoardView {

    private final Map<Position, Place> board;

    public Board(Map<Position, Place> board) {
        this.board = new HashMap<>(board);
    }

    public List<List<String>> getFormatBoard() {
        List<List<String>> result = new ArrayList<>();
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            result.add(getFormatRow(row));
        }

        return result;
    }

    private List<String> getFormatRow(int row) {
        List<String> rowFormats = new ArrayList<>();
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            Position position = new Position(row, column);
            Place place = board.get(position);
            rowFormats.add(place.getFormat());
        }
        return rowFormats;
    }

    public void move(Position from, Position to, Side side) {
        validateNotSamePosition(from, to);

        Place place = board.get(from);
        validateSourcePiece(place, side);

        if (!place.canMove(this, from, to)) {
            throw new IllegalArgumentException("[ERROR] 기물이 가지 못하는 자리입니다.");
        }

        movePiece(from, to);
    }

    private void validateNotSamePosition(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로는 이동할 수 없습니다.");
        }
    }

    private void validateSourcePiece(Place place, Side side) {
        if (place.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 선택한 위치에 기물이 없습니다.");
        }

        if (!place.isSameSide(side)) {
            throw new IllegalArgumentException("[ERROR] 본인의 기물을 선택해야 합니다.");
        }
    }

    private void movePiece(Position from, Position to) {
        Place piece = board.get(from);
        board.put(from, new Empty());
        board.put(to, piece);
    }

    @Override
    public boolean isCannon(Position position) {
        Place place = board.get(position);
        return place.isCannon();
    }

    @Override
    public boolean isEmpty(Position position) {
        Place place = board.get(position);
        return place.isEmpty();
    }

    @Override
    public boolean isSameTeam(Position from, Position to) {
        Place toPlace = board.get(to);

        if (toPlace.isEmpty()) {
            return false;
        }

        Place fromPlace = board.get(from);
        Side fromSide = fromPlace.getSide().orElseGet(() -> {;
            throw new IllegalStateException("[ERROR] 출발 위치에 기물이 없습니다.");
        });
        return toPlace.isSameSide(fromSide);
    }
}
