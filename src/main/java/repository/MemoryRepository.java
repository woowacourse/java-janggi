package repository;

import domain.Coordinate;
import domain.Piece;
import domain.Team;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MemoryRepository implements JanggiRepository {

    private final Map<Coordinate, Piece> pieces = new HashMap<>();
    private Team turn = Team.HAN;

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
    public void setTurn(final Team team) {
        turn = team;
    }

    @Override
    public Team getTurn() {
        return turn;
    }
}
