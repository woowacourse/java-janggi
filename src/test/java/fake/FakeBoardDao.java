package fake;

import dao.BoardDao;
import domain.board.Point;
import domain.piece.Piece;
import domain.piece.Team;
import domain.piece.Wang;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class FakeBoardDao implements BoardDao {

    private final Map<Integer, Map<Point, Piece>> board = new HashMap<>(
            Map.of(1, new HashMap<>(Map.of(
                    Point.of(2, 5), new Wang(Team.HAN),
                    Point.of(9, 5), new Wang(Team.CHO))
            ))
    );

    @Override
    public boolean hasRecords() {
        return !board.isEmpty();
    }

    @Override
    public Map<Point, Piece> load(final int boardId) {
        return board.get(boardId);
    }

    @Override
    public void save(final Connection connection, final Point point, final Piece piece, final int boardId) {
        board.put(boardId, Map.of(point, piece));
    }

    @Override
    public void remove(final Connection connection, final int boardId) {
        board.get(boardId).clear();
    }

    @Override
    public void removeAll(final Connection connection) {
        board.clear();
    }
}
