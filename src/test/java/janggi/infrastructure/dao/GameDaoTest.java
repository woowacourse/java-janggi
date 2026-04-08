package janggi.infrastructure.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.infrastructure.dao.dto.GameEntity;
import janggi.infrastructure.db.DatabaseConnection;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameDaoTest {
    private static GameDao gameDao;

    @BeforeAll
    static void beforeAll() throws Exception {
        gameDao = new GameDao();

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
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM piece");
            statement.execute("DELETE FROM game");
        }
    }

    @Test
    void 새로운_게임_정보를_DB에_저장하고_자동_생성된_게임_ID를_반환한다() throws Exception {
        // given
        String choName = "고래";
        String hanName = "제이콥";
        String currentTurn = "CHO";

        // when
        Long generatedGameId;
        try (Connection connection = DatabaseConnection.getConnection()) {
            generatedGameId = gameDao.insertGame(connection, choName, hanName, currentTurn);
        }

        // then
        assertThat(generatedGameId).isNotNull();
        assertThat(generatedGameId).isGreaterThan(0L);
    }

    @Test
    void 특정_게임_ID를_입력받아_DB에서_해당_게임_정보를_조회한다() throws Exception {
        // given
        String choName = "고래";
        String hanName = "제이콥";
        String currentTurn = "CHO";
        Long savedId;

        try (Connection connection = DatabaseConnection.getConnection()) {
            savedId = gameDao.insertGame(connection, choName, hanName, currentTurn);
        }

        // when
        GameEntity foundGame = gameDao.findById(savedId)
                .orElseThrow(() -> new IllegalArgumentException("게임을 찾을 수 없습니다."));

        // then (검증)
        assertThat(foundGame.id()).isEqualTo(savedId);
        assertThat(foundGame.choPlayerName()).isEqualTo(choName);
        assertThat(foundGame.hanPlayerName()).isEqualTo(hanName);
        assertThat(foundGame.currentTurn()).isEqualTo(currentTurn);
        assertThat(foundGame.isPlaying()).isTrue();
    }
}
