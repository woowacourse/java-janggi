package repository.jdbc;

import domain.board.Board;
import domain.game.GameStatus;
import domain.game.JanggiGame;
import domain.game.JanggiGameRepository;
import repository.entity.JanggiGameEntity;
import repository.mapper.JanggiGameMapper;

import java.sql.*;

public class JdbcJanggiGameRepository implements JanggiGameRepository {

    private static final String INSERT = """
            INSERT INTO game (status, current_player_id)
            VALUES (?, ?)
            """;

    private static final String FIND_BY_ID = """
            SELECT game_id, status, current_player_id
            FROM game
            WHERE game_id = ?
            """;

    private static final String UPDATE = """
            UPDATE game
            SET status = ?, current_player_id = ?
            WHERE game_id = ?
            """;

    private static final String SAVE_GAME_FAILED = "게임 저장에 실패했습니다.";
    private static final String UPDATE_GAME_FAILED = "게임 갱신에 실패했습니다.";
    private static final String GET_GAME_ID_FAILED = "생성된 game_id를 가져오지 못했습니다.";
    private static final String GAME_NOT_FOUND = "해당 게임이 존재하지 않습니다.";
    private static final String FIND_GAME_FAILED = "게임 조회에 실패했습니다.";

    private final JdbcPlayerRepository playerRepository;
    private final JdbcPieceRepository pieceRepository;
    private final JanggiGameMapper janggiGameMapper;

    public JdbcJanggiGameRepository() {
        this.playerRepository = new JdbcPlayerRepository();
        this.pieceRepository = new JdbcPieceRepository();
        this.janggiGameMapper = new JanggiGameMapper();
    }

    @Override
    public long save(final Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, GameStatus.PLAYING.name());
            statement.setNull(2, java.sql.Types.BIGINT);
            statement.executeUpdate();

            return generatedGameId(statement);
        } catch (final SQLException exception) {
            throw new RuntimeException(SAVE_GAME_FAILED, exception);
        }
    }

    @Override
    public JanggiGame findById(final Connection connection, final long gameId) {
        final JanggiGameEntity gameEntity = findGameEntity(connection, gameId);
        final Board board = pieceRepository.findByGameId(connection, gameId);
        final var players = playerRepository.findByGameId(connection, gameId);

        return janggiGameMapper.toDomain(gameEntity, board, players);
    }

    @Override
    public void update(final Connection connection, final JanggiGame game) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, game.getGameStatus().name());
            statement.setLong(2, game.getCurrentPlayer().getPlayerId());
            statement.setLong(3, game.getGameId());
            statement.executeUpdate();
        } catch (final SQLException exception) {
            throw new RuntimeException(UPDATE_GAME_FAILED, exception);
        }
    }

    private long generatedGameId(final PreparedStatement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }

            throw new IllegalStateException(GET_GAME_ID_FAILED);
        }
    }

    private JanggiGameEntity findGameEntity(
            final Connection connection,
            final long gameId
    ) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new JanggiGameEntity(
                            resultSet.getLong("game_id"),
                            resultSet.getString("status"),
                            resultSet.getLong("current_player_id")
                    );
                }

                throw new IllegalStateException(GAME_NOT_FOUND);
            }
        } catch (final SQLException exception) {
            throw new RuntimeException(FIND_GAME_FAILED, exception);
        }
    }
}
