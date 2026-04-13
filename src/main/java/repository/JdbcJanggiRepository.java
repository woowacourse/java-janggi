package repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import model.GameStatus;
import model.JanggiGame;
import model.JanggiState;
import model.board.Board;
import model.coordinate.Position;
import model.piece.Piece;
import repository.db.JdbcDao;
import repository.mapper.JanggiMapper;

public class JdbcJanggiRepository implements JanggiRepository {

    private final JdbcDao jdbcDao;

    public JdbcJanggiRepository(JdbcDao jdbcDao) {
        this.jdbcDao = jdbcDao;
    }

    @Override
    public Long save(JanggiGame janggiGame) {
        String sql = "INSERT INTO janggi_game (status, turn) VALUES (?, ?)";
        Long gameId = jdbcDao.executeInsert(sql, janggiGame.status().name(), janggiGame.turn().name());

        savePieces(gameId, janggiGame.getBoard());
        return gameId;
    }

    @Override
    public Optional<JanggiGame> findById(Long janggiId) {
        String sql = "SELECT * FROM janggi_game WHERE id = ?";
        return jdbcDao.executeQuery(sql, rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }

            Board board = fetchBoard(janggiId);
            JanggiState state = JanggiMapper.mapToState(rs);
            return Optional.of(new JanggiGame(board, state));
        }, janggiId);
    }

    @Override
    public void update(Long janggiId, JanggiGame janggiGame) {
        String sql = "UPDATE janggi_game SET status = ?, turn = ? WHERE id = ?";
        jdbcDao.executeUpdate(sql, janggiGame.status().name(), janggiGame.turn().name(), janggiId);

        jdbcDao.executeUpdate("DELETE FROM piece WHERE game_id = ?", janggiId);
        savePieces(janggiId, janggiGame.getBoard());
    }

    @Override
    public Map<Long, GameStatus> collectGameStatus() {
        String sql = "SELECT id, status FROM janggi_game";
        return jdbcDao.executeQuery(sql, rs -> {
            Map<Long, GameStatus> statusMap = new HashMap<>();
            while (rs.next()) {
                statusMap.put(rs.getLong("id"), JanggiMapper.mapToGameStatus(rs));
            }
            return statusMap;
        });
    }

    private void savePieces(Long gameId, Map<Position, Piece> board) {
        String sql = "INSERT INTO piece (game_id, `row`, col, `type`, team) VALUES (?, ?, ?, ?, ?)";

        List<Object[]> parameters = board.entrySet().stream()
                .map(entry -> {
                    Position pos = entry.getKey();
                    Piece piece = entry.getValue();
                    return new Object[]{gameId, pos.row(), pos.col(), piece.type().name(), piece.team().name()};
                })
                .toList();

        jdbcDao.executeBatch(sql, parameters);
    }

    private Board fetchBoard(Long gameId) {
        String sql = "SELECT * FROM piece WHERE game_id = ?";
        return jdbcDao.executeQuery(sql, rs -> {
            Map<Position, Piece> pieces = new HashMap<>();
            while (rs.next()) {
                Piece piece = JanggiMapper.mapToPiece(rs);
                Position position = JanggiMapper.mapToPosition(rs);
                pieces.put(position, piece);
            }
            return new Board(pieces);
        }, gameId);
    }
}