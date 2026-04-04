package janggi.infra.dao;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.RoomName;
import janggi.infra.config.TestDataSourceConfig;
import janggi.infra.entity.GameEntity;
import janggi.infra.transaction.ConnectionProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDateTime;

import static janggi.fixture.TestFixture.createGameRoomEntity;
import static org.assertj.core.api.Assertions.assertThat;

class JdbcGameRoomDaoTest {

    private final ConnectionProvider connectionProvider = new ConnectionProvider(new TestDataSourceConfig().dataSource());
    private final JdbcGameDAO jdbcGameRoomDao = new JdbcGameDAO(connectionProvider);


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

}
