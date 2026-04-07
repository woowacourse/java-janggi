package janggi.infra.dao;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.RoomName;
import janggi.infra.config.TestDataSourceConfig;
import janggi.infra.entity.GameEntity;
import janggi.infra.util.ConnectionProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;
import static janggi.fixture.TestFixture.createGameRoomEntity;
import static janggi.fixture.TestFixture.saveGameRoomEntity;
import static org.assertj.core.api.Assertions.assertThat;

class JdbcGameRoomDaoTest {

    private final DataSource dataSource = new TestDataSourceConfig().dataSource();
    private final ConnectionProvider connectionProvider = new ConnectionProvider(dataSource);
    private final JdbcGameDAO jdbcGameRoomDao = new JdbcGameDAO(connectionProvider);

    @AfterEach
    void tearDown() {
        try (
                Connection conn = connectionProvider.getConnection();
                Statement statement = conn.createStatement();
        ) {
            statement.executeUpdate("DELETE FROM game");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("게임방을 데이터베이스에 저장한다.")
    public void save_success() throws Exception {
        // given
        GameEntity gameEntity = createGameRoomEntity(new RoomName("room1"), Dynasty.HAN, LocalDateTime.of(2026, 4, 3, 15, 30));

        // when
        Long generatedKey = jdbcGameRoomDao.save(gameEntity);

        // then
        try (
                Connection conn = connectionProvider.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) FROM game WHERE game_id = ?");
        ) {
            preparedStatement.setLong(1, generatedKey);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            assertThat(resultSet.getInt(1)).isEqualTo(1);
        }
    }

    @Test
    @DisplayName("게임을 id로 찾아온다.")
    public void findById_success() throws Exception {
        // given
        GameEntity savedGame = saveGameRoomEntity(
                new RoomName("room1"),
                Dynasty.CHO,
                LocalDateTime.of(2026, 4, 5, 10, 0),
                dataSource
        );

        // when
        Optional<GameEntity> result = jdbcGameRoomDao.findById(savedGame.id());

        // then
        assertThat(result).isPresent();
        GameEntity foundGame = result.get();

        assertThat(foundGame.id()).isEqualTo(savedGame.id());
        assertThat(foundGame.roomName().roomName()).isEqualTo("room1");
        assertThat(foundGame.currentTurn()).isEqualTo(Dynasty.CHO);
        assertThat(foundGame.lastPlayedAt())
                .isEqualTo(LocalDateTime.of(2026, 4, 5, 10, 0));
    }

    @Test
    @DisplayName("게임을 최신순으로 찾아온다.")
    public void findAllOrderByLastPlayedAtDESC_success() throws Exception {
        // given
        saveGameRoomEntity(new RoomName("room1"), CHO,
                LocalDateTime.of(2024, 4, 5, 10, 0), dataSource);
        saveGameRoomEntity(new RoomName("room2"), HAN,
                LocalDateTime.of(2025, 4, 5, 10, 0), dataSource);
        saveGameRoomEntity(new RoomName("room3"), CHO,
                LocalDateTime.of(2026, 4, 5, 10, 0), dataSource);
        
        // when
        List<GameEntity> gameEntities = jdbcGameRoomDao.findAllOrderByLastPlayedAtDesc();

        // then
        assertThat(gameEntities).hasSize(3)
                .extracting(GameEntity::roomName)
                .extracting(RoomName::roomName)
                .containsExactly("room3", "room2", "room1");
        assertThat(gameEntities)
                .extracting(GameEntity::id)
                .allMatch(Objects::nonNull);
    }

    @Test
    @DisplayName("GameEntity의 현재 턴과 최근 플레이 시간을 업데이트한다.")
    public void update() throws Exception {
        // given
        GameEntity gameEntity = saveGameRoomEntity(new RoomName("room1"), CHO,
                LocalDateTime.of(2024, 4, 5, 10, 0), dataSource);

        Dynasty updatedTurn = HAN;
        LocalDateTime updatedLastPlayedAt = LocalDateTime.of(2024, 4, 5, 17, 12);

        GameEntity updatedGameEntity =
                createGameRoomEntity(gameEntity.id(), new RoomName("room1"), updatedTurn, updatedLastPlayedAt);

        // when
        jdbcGameRoomDao.updateCurrentTurnAndLastPlayedAt(updatedGameEntity);

        // then
        try (
                Connection conn = connectionProvider.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM game WHERE game_id = ?");
        ) {
            preparedStatement.setLong(1, gameEntity.id());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            assertThat(resultSet.getString("current_turn")).isEqualTo(updatedTurn.name());
            assertThat(resultSet.getTimestamp("last_played_at")).isEqualTo(Timestamp.valueOf(updatedLastPlayedAt));
        }
    }
}
