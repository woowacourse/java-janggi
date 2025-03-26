package janggi.repository;

import janggi.service.PlayingTurn;
import janggi.domain.Coordinate;
import janggi.domain.Piece;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MemoryRepository implements Repository {

    private final Map<Coordinate, Piece> pieces = new HashMap<>();
    private PlayingTurn playingTurn = new PlayingTurn();

    @Override
    public void save(final Piece piece) {
        pieces.put(piece.getCoordinate(), piece);
    }

    @Override
    public void update(final Coordinate from, final Coordinate to) {
        final var beforePiece = pieces.remove(from);

        final var afterPiece = beforePiece.moveTo(to);
        pieces.put(afterPiece.getCoordinate(), afterPiece);
    }

    @Override
    public Set<Piece> findAll() {
        return new HashSet<>(pieces.values());
    }

    @Override
    public void deleteByCoordinate(final Coordinate coordinate) {
        pieces.remove(coordinate);
    }

    @Override
    public void clear() {
        pieces.clear();
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
