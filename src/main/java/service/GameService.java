package service;

import domain.Game;
import domain.board.Board;
import domain.board.Position;
import domain.piece.PieceInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import repository.BoardRepository;
import repository.GameRepository;

public class GameService {
    private static final String NOT_EXIST_GAME = "[ERROR] 존재하지 않는 게임입니다.";
    private static final String FAILED_TRANSACTION_PROCESS = "[ERROR] 트랜잭션 처리 도중 오류가 발생했습니다. 모든 변경 사항을 롤백합니다.";
    private static final String FAILED_ROLLBACK = "[ERROR] 롤백에 실패했습니다.";
    private static final String FAILED_CONNECT_DB = "[ERROR] DB 연결에 실패했습니다.";

    private final DataSource dataSource;
    private final GameRepository gameRepository;
    private final BoardRepository boardRepository;

    public GameService(DataSource dataSource, GameRepository gameRepository, BoardRepository boardRepository) {
        this.dataSource = dataSource;
        this.gameRepository = gameRepository;
        this.boardRepository = boardRepository;
    }

    public Optional<Game> loadLatestGame() {
        return executeWithConnection(gameRepository::findLatest);
    }

    public Map<Position, PieceInfo> loadBoard(Long gameId) {
        return executeWithConnection(connection -> boardRepository.findAllByGameId(connection, gameId));
    }

    public Long saveGame(Game game, Board board) {
        return executeWithTransaction(connection -> {
            Long gameId = gameRepository.save(connection, game)
                    .orElseThrow(() -> new IllegalStateException(NOT_EXIST_GAME));
            boardRepository.saveAll(connection, gameId, board.toPieceSaveInfo());
            return gameId;
        });
    }

    public boolean move(Board board, Long gameId, Position from, Position to) {
        boolean isGeneralCaught = board.move(from, to);
        updateBoard(gameId, from, to, board.getSpecificPieceInfo(to));
        return isGeneralCaught;
    }

    public void finishGame(Long gameId) {
        try (
                Connection connection = dataSource.getConnection()
        ) {
            gameRepository.finishedGame(connection, gameId);
        } catch (SQLException exception) {
            throw new IllegalStateException(exception.getMessage());
        }
    }

    private void updateBoard(Long gameId, Position from, Position to, PieceInfo pieceInfo) {
        try (
                Connection connection = dataSource.getConnection()
        ) {
            boardRepository.delete(connection, gameId, from);
            boardRepository.delete(connection, gameId, to);
            boardRepository.save(connection, gameId, to, pieceInfo);
        } catch (SQLException exception) {
            throw new IllegalStateException(exception.getMessage());
        }
    }

    private <T> T executeWithConnection(ConnectionTask<T> task) {
        try (
                Connection connection = dataSource.getConnection()
        ) {
            return task.execute(connection);
        } catch (SQLException exception) {
            throw new IllegalStateException(exception.getMessage());
        }
    }

    private <T> T executeWithTransaction(TransactionTask<T> task) {
        try (
                Connection connection = dataSource.getConnection()
        ) {
            connection.setAutoCommit(false);
            try {
                T result = task.execute(connection);
                connection.commit();
                return result;
            } catch (RuntimeException | SQLException exception) {
                try {
                    connection.rollback();
                } catch (SQLException sqlException) {
                    sqlException.addSuppressed(exception);
                    throw new IllegalStateException(FAILED_ROLLBACK, sqlException);
                }
                throw new IllegalStateException(FAILED_TRANSACTION_PROCESS, exception);
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(FAILED_CONNECT_DB, exception);
        }
    }
}
