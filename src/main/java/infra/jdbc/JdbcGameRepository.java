package infra.jdbc;

import domain.game.JanggiGame;
import infra.jdbc.dao.GameDao;
import infra.jdbc.dao.GameDao.GameMetadata;
import infra.jdbc.dao.GamePieceDao;
import infra.jdbc.exception.JdbcRepositoryException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Clock;
import java.util.Optional;
import repository.GameRepository;

public class JdbcGameRepository implements GameRepository {

    private final JdbcConnectionManager connectionManager;
    private final GameDao gameDao;
    private final GamePieceDao gamePieceDao;
    private final SavedGameWriteMapper writeMapper;
    private final SavedGameReadMapper readMapper;

    public JdbcGameRepository(JdbcConnectionManager connectionManager) {
        this(
                connectionManager,
                new GameDao(),
                new GamePieceDao(),
                new SavedGameWriteMapper(Clock.systemDefaultZone()),
                new SavedGameReadMapper()
        );
    }

    public JdbcGameRepository(
            JdbcConnectionManager connectionManager,
            SavedGameWriteMapper writeMapper,
            SavedGameReadMapper readMapper
    ) {
        this(connectionManager, new GameDao(), new GamePieceDao(), writeMapper, readMapper);
    }

    public JdbcGameRepository(
            JdbcConnectionManager connectionManager,
            GameDao gameDao,
            GamePieceDao gamePieceDao,
            SavedGameWriteMapper writeMapper,
            SavedGameReadMapper readMapper
    ) {
        this.connectionManager = connectionManager;
        this.gameDao = gameDao;
        this.gamePieceDao = gamePieceDao;
        this.writeMapper = writeMapper;
        this.readMapper = readMapper;
    }

    @Override
    public JanggiGame save(JanggiGame janggiGame) {
        SavedGameDto savedGameDto = writeMapper.toSavedGameDto(janggiGame);
        long gameId = executeInTransaction(connection -> {
            long savedGameId = gameDao.insert(connection, savedGameDto);
            gamePieceDao.insertAll(connection, savedGameId, savedGameDto.pieces());
            return savedGameId;
        }, "장기 게임 저장에 실패했습니다.");

        janggiGame.assignGameId(gameId);
        return janggiGame;
    }

    @Override
    public Optional<JanggiGame> findLatestRunningGame() {
        try (Connection connection = connectionManager.getConnection()) {
            Optional<GameMetadata> latestRunningGame = gameDao.findLatestRunningGame(connection);
            if (latestRunningGame.isEmpty()) {
                return Optional.empty();
            }

            GameMetadata metadata = latestRunningGame.orElseThrow();
            SavedGameDto savedGameDto = new SavedGameDto(
                    metadata.gameId(),
                    metadata.currentTurn(),
                    metadata.status(),
                    metadata.winner(),
                    metadata.createdAt(),
                    metadata.updatedAt(),
                    gamePieceDao.findAllByGameId(connection, metadata.gameId())
            );
            return Optional.of(readMapper.toJanggiGame(savedGameDto));
        } catch (SQLException e) {
            throw new JdbcRepositoryException("진행 중인 장기 게임 조회에 실패했습니다.", e);
        }
    }

    @Override
    public void update(JanggiGame janggiGame) {
        if (janggiGame.gameId() == null) {
            throw new JdbcRepositoryException("수정할 장기 게임의 식별자가 없습니다.");
        }

        java.time.LocalDateTime updatedAt = writeMapper.updatedAt();
        executeInTransaction(connection -> {
            gameDao.update(
                    connection,
                    janggiGame.gameId(),
                    janggiGame.currentTurn(),
                    janggiGame.gameResult().status(),
                    janggiGame.gameResult().winner(),
                    updatedAt
            );
            gamePieceDao.deleteAllByGameId(connection, janggiGame.gameId());
            gamePieceDao.insertAll(connection, janggiGame.gameId(), writeMapper.toSavedPieceDtos(janggiGame));
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
