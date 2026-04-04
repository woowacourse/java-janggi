package janggi.infra.dao;

import janggi.domain.dynasty.Dynasty;
import janggi.infra.config.TestDataSourceConfig;
import janggi.infra.entity.GameEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

import static janggi.fixture.TestFixture.createGameRoomEntity;
import static org.assertj.core.api.Assertions.assertThat;

class JdbcGameRoomDaoTest {

    private final DataSource dataSource = new TestDataSourceConfig().dataSource();
    private final JdbcGameDAO jdbcGameRoomDao = new JdbcGameDAO(dataSource);


    @Test
    @DisplayName("게임방을 데이터베이스에 저장한다.")
    public void save_success() throws Exception {
        // given
        GameEntity gameEntity = createGameRoomEntity("room1", Dynasty.HAN, LocalDateTime.of(2026, 4, 3, 15, 30));

        // when
        Long generatedKey = jdbcGameRoomDao.save(gameEntity);

        // then
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) FROM game_room WHERE game_room_id = ?");
        ) {
            preparedStatement.setLong(1, generatedKey);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            assertThat(resultSet.getInt(1)).isEqualTo(1);
        }
    }

}
