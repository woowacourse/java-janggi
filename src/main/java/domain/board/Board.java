package domain.board;

import static domain.common.Constant.MAX_COLUMN;
import static domain.common.Constant.MAX_ROW;
import static domain.common.Constant.MIN_COLUMN;
import static domain.common.Constant.MIN_ROW;

import domain.place.piece.Piece;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Board implements BoardView {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public List<List<String>> getFormatBoard() {
        List<List<String>> result = new ArrayList<>();
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            result.add(getFormatRow(row));
        }

        return result;
    }

    public void move(Position from, Position to, Side side) {
        Piece piece = getRequiredPiece(from);
        validateSameSide(piece, side);

        if (!piece.canMove(this, from, to)) {
            throw new IllegalArgumentException("[ERROR] 기물이 가지 못하는 자리입니다.");
        }

        change(from, to, piece);
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
    public boolean isSameTeam(Position from, Position to) {
        if (findPiece(to).isEmpty()) {
            return false;
        }

        Piece fromPiece = getRequiredPiece(from);
        Piece toPiece = getRequiredPiece(to);

        return fromPiece.isSameSide(toPiece);
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

    private Optional<Piece> findPiece(Position position) {
        return Optional.ofNullable(board.get(position));
    }

    private Piece getRequiredPiece(Position position) {
        return findPiece(position)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 현재 위치에 기물이 없습니다."));
    }

    private void validateSameSide(Piece piece, Side side) {
        if (!piece.isSameSide(side)) {
            throw new IllegalArgumentException("[ERROR] 본인의 기물을 선택해야 합니다.");
        }
    }

    private void change(Position from, Position to, Piece piece) {
        board.remove(from);
        board.put(to, piece);
    }
}
