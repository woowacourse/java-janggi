package team.janggi.domain.board;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import team.janggi.domain.Position;
import team.janggi.domain.piece.Piece;

public class BoardPieces implements BoardStateReader {
    private final Map<Position, Piece> map;

    public BoardPieces() {
        this.map = new HashMap<>();
    }

    @Override
    public Piece getPiece(Position position) {
        return map.get(position);
    }

    public void movePiece(Position from, Position to) {
        final Piece piece = getPiece(from);

        map.put(to, piece);
        map.put(from, Piece.EMPTY_PIECE);
    }

    public void setPiece(Position position, Piece piece) {
        map.put(position, piece);
    }

    public BoardStateReader getBoardStateReader() {
        return this;
    }

    public boolean isOutOfBounds(Position position) {
        return !map.containsKey(position);
    }

    public int size() {
        return map.size();
    }

    public Collection<Piece> getAllPiece() {
        return map.values();
    }
}
