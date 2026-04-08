package infra.jdbc;

import infra.jdbc.dao.GameDao;
import infra.jdbc.dao.GameDao.GameMetadata;
import infra.jdbc.dao.GamePieceDao;
import infra.jdbc.exception.JdbcRepositoryException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import repository.GameRepository;
import repository.SavedGameDto;

public class JdbcGameRepository implements GameRepository {

    private final JdbcConnectionManager connectionManager;
    private final GameDao gameDao;
    private final GamePieceDao gamePieceDao;

    public JdbcGameRepository(JdbcConnectionManager connectionManager) {
        this(connectionManager, new GameDao(), new GamePieceDao());
    }

    public JdbcGameRepository(JdbcConnectionManager connectionManager, GameDao gameDao, GamePieceDao gamePieceDao) {
        this.connectionManager = connectionManager;
        this.gameDao = gameDao;
        this.gamePieceDao = gamePieceDao;
    }

    @Override
    public long save(SavedGameDto savedGameDto) {
        return executeInTransaction(connection -> {
            long gameId = gameDao.insert(connection, savedGameDto);
            gamePieceDao.insertAll(connection, gameId, savedGameDto.pieces());
            return gameId;
        }, "장기 게임 저장에 실패했습니다.");
    }

    @Override
    public Optional<SavedGameDto> findLatestRunningGame() {
        try (Connection connection = connectionManager.getConnection()) {
            Optional<GameMetadata> latestRunningGame = gameDao.findLatestRunningGame(connection);
            if (latestRunningGame.isEmpty()) {
                return Optional.empty();
            }

            GameMetadata metadata = latestRunningGame.orElseThrow();
            return Optional.of(new SavedGameDto(
                    metadata.gameId(),
                    metadata.currentTurn(),
                    metadata.status(),
                    metadata.winner(),
                    metadata.createdAt(),
                    metadata.updatedAt(),
                    gamePieceDao.findAllByGameId(connection, metadata.gameId())
            ));
        } catch (SQLException e) {
            throw new JdbcRepositoryException("진행 중인 장기 게임 조회에 실패했습니다.", e);
        }
    }

    @Override
    public void update(SavedGameDto savedGameDto) {
        if (savedGameDto.gameId() == null) {
            throw new JdbcRepositoryException("수정할 장기 게임의 식별자가 없습니다.");
        }

        executeInTransaction(connection -> {
            gameDao.update(connection, savedGameDto);
            gamePieceDao.deleteAllByGameId(connection, savedGameDto.gameId());
            gamePieceDao.insertAll(connection, savedGameDto.gameId(), savedGameDto.pieces());
            return null;
        }, "장기 게임 수정에 실패했습니다.");
    }

    private <T> T executeInTransaction(JdbcTransaction<T> transaction, String errorMessage) {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                T result = transaction.execute(connection);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                throw new JdbcRepositoryException(errorMessage, e);
            }
        } catch (SQLException e) {
            throw new JdbcRepositoryException(errorMessage, e);
        }
    }

    @FunctionalInterface
    private interface JdbcTransaction<T> {
        T execute(Connection connection) throws SQLException;
    }
}
