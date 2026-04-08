package service;

import database.ConnectionProvider;
import domain.board.Board;
import domain.game.JanggiGame;
import domain.game.Turn;
import dto.GameDto;
import dto.PieceSnapshot;
import exception.DataAccessException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JanggiService {

    private static final String SETUP_FAIL_MESSAGE = "데이터베이스 초기화에 실패했습니다.";
    private static final String LOAD_ONGOING_GAME_FAIL_MESSAGE = "진행 중인 게임 불러오기에 실패했습니다.";
    private static final String SAVE_FAIL_MESSAGE = "게임 저장에 실패했습니다.";
    private static final String UPDATE_FAIL_MESSAGE = "게임 업데이트에 실패했습니다.";
    private static final String GAME_END_FAIL_MESSAGE = "게임 종료 처리에 실패했습니다.";
    private static final String EXISTS_GAME_FAIL_MESSAGE = "진행 중인 게임 존재 여부 조회에 실패했습니다.";
    private static final String ROLLBACK_FAIL_MESSAGE = "트랜잭션 롤백에 실패했습니다.";
    private static final String CLOSE_FAIL_MESSAGE = "커넥션 반환에 실패했습니다.";

    private final BoardService boardService;
    private final GameService gameService;
    private final ConnectionProvider connectionProvider;

    public JanggiService(BoardService boardService, GameService gameService, ConnectionProvider connectionProvider) {
        this.boardService = boardService;
        this.gameService = gameService;
        this.connectionProvider = connectionProvider;

        try (Connection connection = connectionProvider.getConnection()) {
            gameService.setUp(connection);
            boardService.setUp(connection);
        } catch (SQLException e) {
            throw new DataAccessException(SETUP_FAIL_MESSAGE, e);
        }
    }

    public JanggiGame loadOngoingGame() {
        try (Connection connection = connectionProvider.getConnection()) {
            GameDto gameDto = gameService.findOngoingGame(connection);
            Board board = boardService.getBoard(connection, gameDto.id());
            Turn turn = Turn.valueOf(gameDto.currentTurn());
            return JanggiGame.of(board, turn);
        } catch (SQLException e) {
            throw new DataAccessException(LOAD_ONGOING_GAME_FAIL_MESSAGE, e);
        }
    }

    public void save(List<PieceSnapshot> pieceSnapshots, String turn) {
        Connection connection = null;
        try {
            connection = connectionProvider.getConnection();
            connection.setAutoCommit(false);
            int gameId = gameService.save(connection, turn);
            boardService.save(connection, gameId, pieceSnapshots);
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DataAccessException(SAVE_FAIL_MESSAGE, e);
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
            boardService.update(connection, gameId, from, to);
            gameService.updateTurn(connection, gameId, turn);
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DataAccessException(UPDATE_FAIL_MESSAGE, e);
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
            throw new DataAccessException(GAME_END_FAIL_MESSAGE, e);
        } finally {
            close(connection);
        }
    }

    public boolean existsGame() {
        try (Connection connection = connectionProvider.getConnection()) {
            return gameService.existsGame(connection);
        } catch (SQLException e) {
            throw new DataAccessException(EXISTS_GAME_FAIL_MESSAGE, e);
        }
    }

    private void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new DataAccessException(ROLLBACK_FAIL_MESSAGE, e);
            }
        }
    }

    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                throw new DataAccessException(CLOSE_FAIL_MESSAGE, e);
            }
        }
    }
}
