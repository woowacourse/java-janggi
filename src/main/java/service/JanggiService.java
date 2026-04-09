package service;

import domain.board.Board;
import domain.game.JanggiGame;
import dto.GameSummary;
import dto.GameWrapper;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import repository.JanggiGameRepository;

public final class JanggiService {

    private final DataSource dataSource;
    private final JanggiGameRepository repository;

    public JanggiService(DataSource dataSource, JanggiGameRepository repository) {
        this.dataSource = dataSource;
        this.repository = repository;
    }

    public JanggiService(JanggiGameRepository repository) {
        this.dataSource = null;
        this.repository = repository;
    }

    public GameWrapper createGame(Board board) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            JanggiGame janggiGame = new JanggiGame(board);
            long generatedKey = repository.save(conn, janggiGame);

            conn.commit();

            return new GameWrapper(generatedKey, janggiGame);
        } catch (SQLException e) {
            rollback(conn);
            throw new RuntimeException(e);
        } finally {
            close(conn);
        }
    }

    public void saveGame(GameWrapper gameWrapper) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            repository.updateGameStatus(conn, gameWrapper.game(), gameWrapper.gameId());

            conn.commit();
        } catch (SQLException e) {
            rollback(conn);
            throw new RuntimeException(e);
        } finally {
            close(conn);
        }
    }

    public GameWrapper loadGame(long gameId) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            JanggiGame loadedGame = repository.findById(conn, gameId);

            conn.commit();

            return new GameWrapper(gameId, loadedGame);
        } catch (SQLException e) {
            rollback(conn);
            throw new RuntimeException(e);
        } finally {
            close(conn);
        }
    }

    public List<GameSummary> loadAllGameSummaries() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            List<GameSummary> gameSummaries = repository.findAll(conn);

            conn.commit();

            return gameSummaries;
        } catch (SQLException e) {
            rollback(conn);
            throw new RuntimeException(e);
        } finally {
            close(conn);
        }
    }

    void clear() {
        repository.clear();
    }

    private static void rollback(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
