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
    private final DataSource dataSource;
    private final GameRepository gameRepository;
    private final BoardRepository boardRepository;

    public GameService(DataSource dataSource, GameRepository gameRepository, BoardRepository boardRepository) {
        this.dataSource = dataSource;
        this.gameRepository = gameRepository;
        this.boardRepository = boardRepository;
    }

    public Optional<Game> loadLatestGame() {
        try (
                Connection connection = dataSource.getConnection()
        ) {
            return gameRepository.findLatest(connection);
        } catch (SQLException exception) {
            throw new IllegalStateException(exception.getMessage());
        }
    }

    public Map<Position, PieceInfo> loadBoard(Long gameId) {
        try (
                Connection connection = dataSource.getConnection()
        ) {
            return boardRepository.findAllByGameId(connection, gameId);
        } catch (SQLException exception) {
            throw new IllegalStateException(exception.getMessage());
        }
    }

    public Long saveGame(Game game, Map<Position, PieceInfo> pieceInfos) {
        return executeWithTransaction(connection -> {
            Long gameId = gameRepository.save(connection, game)
                    .orElseThrow(() -> new IllegalStateException("[ERROR] 존재하지 않는 게임입니다."));
            boardRepository.saveAll(connection, gameId, pieceInfos);
            return gameId;
        });
    }

    public boolean move(Board board, Long gameId, Position from, Position to) {
        boolean isGeneralCaught = board.move(from, to);
        updateBoard(gameId, from, to, board.getPieceInfos());
        return isGeneralCaught;
    }

    public void finishGame(Long gameId) {
        try (Connection connection = dataSource.getConnection()) {
            gameRepository.finishedGame(connection, gameId);
        } catch (SQLException exception) {
            throw new IllegalStateException(exception.getMessage());
        }
    }

    private void updateBoard(Long gameId, Position from, Position to, Map<Position, PieceInfo> pieceInfos) {
        try (
                Connection connection = dataSource.getConnection()
        ) {
            boardRepository.delete(connection, gameId, from);
            boardRepository.delete(connection, gameId, to);
            PieceInfo pieceInfo = pieceInfos.get(to);
            boardRepository.save(connection, gameId, to, pieceInfo);
        } catch (SQLException exception) {
            throw new IllegalStateException(exception.getMessage());
        }
    }

    private <T> T executeWithTransaction(Transaction<T> task) {
        try (
                Connection connection = dataSource.getConnection()
        ) {
            connection.setAutoCommit(false);
            try {
                T result = task.execute(connection);
                connection.commit();
                return result;
            } catch (Exception exception) {
                connection.rollback();
                throw new IllegalStateException("[ERROR] DB 저장 도중 오류가 발생했습니다. 모든 변경 사항을 롤백합니다.", exception);
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR]", exception);
        }
    }
}
