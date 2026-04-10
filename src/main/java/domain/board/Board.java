package domain.board;

import static domain.common.Constant.MAX_COLUMN;
import static domain.common.Constant.MAX_ROW;
import static domain.common.Constant.MIN_COLUMN;
import static domain.common.Constant.MIN_ROW;

import domain.place.moveStrategy.Direction;
import domain.place.piece.Piece;
import domain.position.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Board implements BoardView {

    private final Map<Position, Piece> board;
    private final Palace palace;

    public Board(Map<Position, Piece> board, Palace palace) {
        this.board = new HashMap<>(board);
        this.palace = palace;
    }

    public void move(Position from, Position to) {
        Piece piece = findPiece(from).get();

        if (!piece.canMove(this, from, to)) {
            throw new IllegalArgumentException("[ERROR] 기물이 가지 못하는 자리입니다.");
        }

        change(from, to, piece);
    }

    public Optional<Piece> findPiece(Position position) {
        return Optional.ofNullable(board.get(position));
    }

    public List<List<String>> getFormatBoard() {
        List<List<String>> result = new ArrayList<>();
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            result.add(getFormatRow(row));
        }

        return result;
    }

    @Override
    public boolean isCannon(Position position) {
        return findPiece(position)
                .map(Piece::isCannon)
                .orElse(false);
    }

    @Override
    public boolean isEmpty(Position position) {
        return findPiece(position).isEmpty();
    }

    @Override
    public boolean isInPalace(Position position) {
        return palace.isInPalace(position);
    }

    @Override
    public boolean isPalaceConnected(Position position, Direction direction) {
        return palace.isConnected(position, direction);
    }

    @Override
    public Set<Position> findPalaceNextPositions(Position position) {
        return palace.findNextPositions(position);
    }

    @Override
    public Set<Direction> findAvailableDirections(Position position) {
        return palace.findAvailableDirections(position);
    }

    private List<String> getFormatRow(int row) {
        List<String> rowFormats = new ArrayList<>();
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            Position position = new Position(row, column);
            rowFormats.add(findPiece(position)
                    .map(Piece::getFormat)
                    .orElse(" "));
        }
        return rowFormats;
    }

    private void change(Position from, Position to, Piece piece) {
        board.remove(from);
        board.put(to, piece);
    }
}
