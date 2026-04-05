package service;

import domain.board.Board;
import domain.game.JanggiGame;
import domain.game.Turn;
import dto.GameDto;
import dto.PieceSnapshot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class FacadeService {

    private static final String JDBC_SQLITE_JANGGI_DB = "jdbc:sqlite:janggi.db";

    private final JanggiService janggiService;
    private final GameService gameService;
    private final Connection connection;


    public FacadeService(JanggiService janggiService, GameService gameService) {
        this.janggiService = janggiService;
        this.gameService = gameService;
        try {
            connection = DriverManager.getConnection(JDBC_SQLITE_JANGGI_DB);
            gameService.setUp(connection);
            janggiService.setUp(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public JanggiGame loadOngoingGame() {
        try {
            GameDto gameDto = gameService.findOngoingGame(connection);
            Board board = janggiService.getBoard(connection, gameDto.id());
            Turn turn = Turn.valueOf(gameDto.currentTurn());
            return JanggiGame.of(board, turn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(List<PieceSnapshot> pieceSnapshots, String turn) {
        try {
            connection.setAutoCommit(false);
            int gameId = gameService.save(connection, turn);
            janggiService.save(connection, gameId, pieceSnapshots);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            rollback();
            throw new RuntimeException(e);
        }
    }

    public boolean existsGame() {
        try {
            return gameService.existsGame(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(List<Integer> from, List<Integer> to, String turn) {
        try {
            connection.setAutoCommit(false);
            int gameId = gameService.findOngoingGame(connection).id();
            janggiService.update(connection, gameId, from, to);
            gameService.updateTurn(connection, gameId, turn);
            connection.commit();
            connection.setAutoCommit(true); // 추가
        } catch (SQLException e) {
            rollback();
            throw new RuntimeException(e);
        }
    }
}
