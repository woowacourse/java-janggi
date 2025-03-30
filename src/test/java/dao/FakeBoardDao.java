package dao;

import domain.board.Point;
import domain.piece.Piece;
import domain.piece.Team;
import domain.piece.Wang;

import java.util.HashMap;
import java.util.Map;

public class FakeBoardDao implements BoardDao {

    private final Map<Point, Piece> pieces = new HashMap<>(
            Map.of(
                    Point.of(2, 5), new Wang(Team.HAN),
                    Point.of(9, 5), new Wang(Team.CHO)
    ));

    @Override
    public boolean hasRecords() {
        return !pieces.isEmpty();
    }

    @Override
    public Map<Point, Piece> load() {
        return pieces;
    }

    @Override
    public void save(final Point point, final Piece piece) {
        pieces.put(point, piece);
    }

    @Override
    public void removeAll() {
        pieces.clear();
    }
}
