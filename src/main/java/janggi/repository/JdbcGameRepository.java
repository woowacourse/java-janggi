package janggi.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import janggi.domain.board.coordinate.Point;
import janggi.domain.game.Game;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;

public class JdbcGameRepository implements GameRepository {

    private final JdbcContext jdbcContext;
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JdbcGameRepository(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
        this.gameDao = new GameDao(jdbcContext);
        this.pieceDao = new PieceDao(jdbcContext);
    }

    @Override
    public Optional<Integer> findActiveGameId() {
        return gameDao.findActiveGameId();
    }

    @Override
    public int save(Game game) {
        try (Connection conn = jdbcContext.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int gameId = gameDao.insert(conn, game.getTurn());
                pieceDao.insertAll(conn, gameId, game.getBoard());
                conn.commit();
                return gameId;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 저장에 실패했습니다.", e);
        }
    }

    @Override
    public void update(int gameId, Game game) {
        try (Connection conn = jdbcContext.getConnection()) {
            conn.setAutoCommit(false);
            try {
                gameDao.updateTurn(conn, gameId, game.getTurn());
                pieceDao.deleteAll(conn, gameId);
                pieceDao.insertAll(conn, gameId, game.getBoard());
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 업데이트에 실패했습니다.", e);
        }
    }

    @Override
    public Game load(int gameId) {
        Side turn = gameDao.findTurn(gameId);
        Map<Point, Piece> board = pieceDao.findAll(gameId);
        return Game.loadGame(board, turn);
    }

    @Override
    public void finish(int gameId, Side winner) {
        try (Connection conn = jdbcContext.getConnection()) {
            conn.setAutoCommit(false);
            try {
                gameDao.finish(conn, gameId, winner);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 종료 처리에 실패하였습니다.", e);
        }
    }
}
