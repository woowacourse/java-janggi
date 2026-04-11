package janggi.infrastructure.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.space.Position;
import janggi.infrastructure.dao.dto.MoveEntity;
import janggi.infrastructure.db.ConnectionContext;
import janggi.infrastructure.db.DatabaseConnection;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoveHistoryDaoTest {

    private MoveHistoryDao moveHistoryDao;

    @BeforeAll
    static void beforeAll() throws Exception {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             InputStream inputStream = MoveHistoryDaoTest.class.getClassLoader().getResourceAsStream("schema.sql")) {

            Assertions.assertNotNull(inputStream);
            String schemaSql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            statement.execute(schemaSql);
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        moveHistoryDao = new MoveHistoryDao();
        Connection connection = DatabaseConnection.getConnection();
        ConnectionContext.set(connection);
        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM move_history");
            statement.execute("DELETE FROM game");

            statement.execute("INSERT INTO game (id, cho_player_name, han_player_name, cho_formation, han_formation, current_turn) " +
                    "VALUES (1, '초', '한', 'LEFT_ELEPHANT', 'RIGHT_ELEPHANT', 'CHO')");
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        Connection connection = ConnectionContext.get();
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        ConnectionContext.clear();
    }

    @Test
    void 게임_진행_이력을_저장하고_순서대로_조회한다() throws Exception {
        // given
        Long gameId = 1L;
        Position firstSource = Position.of(0, 3);
        Position firstTarget = Position.of(0, 4);
        Position secondSource = Position.of(0, 6);
        Position secondTarget = Position.of(0, 5);

        // when
        moveHistoryDao.insertMove(gameId, firstSource, firstTarget);
        moveHistoryDao.insertMove(gameId, secondSource, secondTarget);

        List<MoveEntity> moves = moveHistoryDao.findAllByGameId(gameId);

        // then
        assertThat(moves).hasSize(2);

        // 첫 번째 이동 검증
        assertThat(moves.get(0).sourceX()).isEqualTo(0);
        assertThat(moves.get(0).sourceY()).isEqualTo(3);

        // 두 번째 이동 검증
        assertThat(moves.get(1).sourceX()).isEqualTo(0);
        assertThat(moves.get(1).sourceY()).isEqualTo(6);
    }
}
