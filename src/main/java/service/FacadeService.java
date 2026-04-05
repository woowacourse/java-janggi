package service;

import database.ConnectionProvider;
import domain.board.Board;
import domain.game.JanggiGame;
import domain.game.Turn;
import dto.GameDto;
import dto.PieceSnapshot;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FacadeService {

    private final JanggiService janggiService;
    private final GameService gameService;
    private final ConnectionProvider connectionProvider;

    public FacadeService(JanggiService janggiService, GameService gameService, ConnectionProvider connectionProvider) {
        this.janggiService = janggiService;
        this.gameService = gameService;
        this.connectionProvider = connectionProvider;

        try (Connection connection = connectionProvider.getConnection()) {
            gameService.setUp(connection);
            janggiService.setUp(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public JanggiGame loadOngoingGame() {
        try (Connection connection = connectionProvider.getConnection()) {
            GameDto gameDto = gameService.findOngoingGame(connection);
            Board board = janggiService.getBoard(connection, gameDto.id());
            Turn turn = Turn.valueOf(gameDto.currentTurn());
            return JanggiGame.of(board, turn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(List<PieceSnapshot> pieceSnapshots, String turn) {
        Connection connection = null;
        try {
            connection = connectionProvider.getConnection();
            connection.setAutoCommit(false);
            int gameId = gameService.save(connection, turn);
            janggiService.save(connection, gameId, pieceSnapshots);
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
    }

    public void update(List<Integer> from, List<Integer> to, String turn) {
        Connection connection = null;
        try {
            connection = connectionProvider.getConnection();
            connection.setAutoCommit(false);
            int gameId = gameService.findOngoingGame(connection).id();
            janggiService.update(connection, gameId, from, to);
            gameService.updateTurn(connection, gameId, turn);
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
    }

    public void gameEnd() {
        Connection connection = null;
        try {
            connection = connectionProvider.getConnection();
            connection.setAutoCommit(false);
            int gameId = gameService.findOngoingGame(connection).id();
            gameService.gameEnd(connection, gameId);
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
    }

    public boolean existsGame() {
        try (Connection connection = connectionProvider.getConnection()) {
            return gameService.existsGame(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
