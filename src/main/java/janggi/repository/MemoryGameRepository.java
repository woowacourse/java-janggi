package janggi.repository;

import janggi.domain.Coordinate;
import janggi.domain.Piece;
import janggi.domain.board.Board;
import janggi.service.PlayingTurn;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MemoryGameRepository implements Repository {

    private final Map<Coordinate, Piece> pieces;
    private PlayingTurn playingTurn = new PlayingTurn();

    public MemoryGameRepository() {
        pieces = new HashMap<>();
    }

    public MemoryGameRepository(Board board) {
        pieces = new HashMap<>(board.getPieces());
    }

    @Override
    public boolean isConnectable() {
        return true;
    }

    @Override
    public void save(final Piece piece) {
        pieces.put(piece.coordinate(), piece);
    }

    @Override
    public void update(final Coordinate from, final Coordinate to) {
        final var beforePiece = pieces.remove(from);

        final var afterPiece = beforePiece.moveTo(to);
        pieces.put(afterPiece.coordinate(), afterPiece);
    }

    @Override
    public Set<Piece> allPieces() {
        return new HashSet<>(pieces.values());
    }

    @Override
    public void deleteByCoordinate(final Coordinate coordinate) {
        pieces.remove(coordinate);
    }

    @Override
    public void clear() {
        pieces.clear();
        playingTurn = new PlayingTurn();
    }

    @Override
    public void updateTurn(final PlayingTurn playingTurn) {
        this.playingTurn = playingTurn;
    }

    @Override
    public PlayingTurn getTurn() {
        return playingTurn;
    }
}
