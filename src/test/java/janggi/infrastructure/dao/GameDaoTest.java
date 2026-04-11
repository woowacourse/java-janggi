package janggi.infrastructure.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.infrastructure.dao.dto.GameEntity;
import janggi.infrastructure.db.ConnectionContext;
import janggi.infrastructure.db.DatabaseConnection;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameDaoTest {

    private GameDao gameDao;

    @BeforeAll
    static void beforeAll() throws Exception {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             InputStream inputStream = GameDaoTest.class.getClassLoader().getResourceAsStream("schema.sql")) {

            if (inputStream == null) {
                throw new IllegalStateException("schema.sql 파일을 찾을 수 없습니다.");
            }

            String schemaSql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            statement.execute(schemaSql);
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        gameDao = new GameDao();

        Connection connection = DatabaseConnection.getConnection();
        ConnectionContext.set(connection);

        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM move_history");
            statement.execute("DELETE FROM game");
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
    void 새로운_게임을_저장하고_ID를_반환한다() throws Exception {
        Long gameId = gameDao.insertGame("고래", "제이콥", "LEFT_ELEPHANT", "RIGHT_ELEPHANT", "CHO");

        assertThat(gameId).isNotNull();
        assertThat(gameId).isGreaterThan(0L);
    }

    @Test
    void 특정_게임_ID를_입력받아_해당_게임_정보를_조회한다() throws Exception {
        Long savedId = gameDao.insertGame("고래", "제이콥", "LEFT_ELEPHANT", "RIGHT_ELEPHANT", "CHO");

        Optional<GameEntity> foundGame = gameDao.findById(savedId);

        assertThat(foundGame).isPresent();
        GameEntity entity = foundGame.get();
        assertThat(entity.id()).isEqualTo(savedId);
        assertThat(entity.choPlayerName()).isEqualTo("고래");
        assertThat(entity.hanPlayerName()).isEqualTo("제이콥");
        assertThat(entity.choFormation()).isEqualTo("LEFT_ELEPHANT");
        assertThat(entity.hanFormation()).isEqualTo("RIGHT_ELEPHANT");
        assertThat(entity.currentTurn()).isEqualTo("CHO");
        assertThat(entity.isPlaying()).isTrue();
    }

    @Test
    void 게임의_현재_턴과_진행_상태를_업데이트한다() throws Exception {
        Long gameId = gameDao.insertGame("고래", "제이콥", "LEFT_ELEPHANT", "RIGHT_ELEPHANT", "CHO");

        gameDao.updateGame(gameId, "HAN", false);

        GameEntity updatedGame = gameDao.findById(gameId).orElseThrow();
        assertThat(updatedGame.currentTurn()).isEqualTo("HAN");
        assertThat(updatedGame.isPlaying()).isFalse();
    }

    @Test
    void 모든_게임을_ID_내림차순으로_조회한다() throws Exception {
        gameDao.insertGame("초1", "한1", "LEFT_ELEPHANT", "LEFT_ELEPHANT", "CHO");
        gameDao.insertGame("초2", "한2", "RIGHT_ELEPHANT", "RIGHT_ELEPHANT", "HAN");

        List<GameEntity> games = gameDao.findAll();

        assertThat(games).hasSize(2);
        // 내림차순(ORDER BY id DESC)
        assertThat(games.get(0).choPlayerName()).isEqualTo("초2");
        assertThat(games.get(1).choPlayerName()).isEqualTo("초1");
    }
}
