package domain.board;

import static domain.common.Constant.MAX_COLUMN;
import static domain.common.Constant.MIN_COLUMN;
import static domain.common.Constant.MIN_ROW;
import static domain.common.Constant.MAX_ROW;

import domain.place.Place;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Place> board;

    public Board(Map<Position, Place> board) {
        this.board = Map.copyOf(board);
    }

    public List<String> getFormatBoard() {
        List<String> result = new ArrayList<>();
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            getFormatRow(row, result);
        }

        return result;
    }

    public boolean isSameTeam(Position from, Position to) {
        Place toPlace = board.get(to);

        if (toPlace.isEmpty()) {
            return false;
        }

        Place fromPlace = board.get(from);
        Side fromSide = fromPlace.getSide();
        return toPlace.isSameSide(fromSide);
    }

    public boolean isOutOfBounds(int row, int column){
        return row >= MIN_ROW && row <= MAX_ROW && column >= MIN_COLUMN && column <= MAX_COLUMN;
    }

    private void getFormatRow(int row, List<String> result) {
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            Position position = new Position(row, column);
            Place place = board.get(position);
            String format = place.getFormat();
            result.add(format);
        }
    }
}
