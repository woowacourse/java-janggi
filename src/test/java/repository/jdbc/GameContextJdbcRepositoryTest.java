package repository.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.entity.GameContextEntity;

class GameContextJdbcRepositoryTest {


    private static final String CONFIG_FILE_NAME = "database.properties";

    private static final String CREATE_GAME_CONTEXT_TABLE_SQL = "CREATE TABLE IF NOT EXISTS game_context (" +
            "game_context_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "current_turn_own_team VARCHAR(10) NOT NULL, " +
            "game_state VARCHAR(10) NOT NULL)";

    private JdbcTemplate template = new JdbcTemplate(JdbcConnectionGenerator.create(CONFIG_FILE_NAME));
    private GameContextJdbcRepository gameContextRepository = new GameContextJdbcRepository(template);

    @BeforeEach
    void setUp() throws SQLException {
        template.executeCommand(CREATE_GAME_CONTEXT_TABLE_SQL);
    }

    @AfterEach
    void clearAll() throws SQLException {
        String sql = "DROP ALL OBJECTS";
        template.executeCommand(sql);
    }

    @Test
    @DisplayName("단일 GameContext에 대해 잘 저장한다")
    void save_success() throws SQLException {
        //given
        GameContextEntity testEntity = new GameContextEntity(null, "CHO", "PLAYING");

        //when
        Long savedEntityDatabaseId = gameContextRepository.save(testEntity);

        assertNotNull(savedEntityDatabaseId);
    }

    @Test
    @DisplayName("단일 GameContext에 대해 ID로 잘 가져온다")
    void find_success() throws SQLException {
        //given
        String testCurrentTurnOwnTeam = "CHO";
        String testGameState = "PLAYING";
        GameContextEntity testEntity = new GameContextEntity(null, testCurrentTurnOwnTeam, testGameState);
        Long savedEntityDatabaseId = gameContextRepository.save(testEntity);

        GameContextEntity expectResult = new GameContextEntity(savedEntityDatabaseId, testCurrentTurnOwnTeam,
                testGameState);

        //when
        GameContextEntity result = gameContextRepository.find(savedEntityDatabaseId);

        assertEquals(expectResult, result);
    }

    @Test
    @DisplayName("단일 GameContext에 대해 Update를 잘한다 : 현재 턴 소유 팀 변경")
    void updateCurrentTurnOwnTeam_success() throws SQLException {
        //given
        String originCurrentTurnOwnTeam = "CHO";
        String expectCurrentTurnOwnTeam = "HAN";
        String testGameState = "PLAYING";

        GameContextEntity originEntity = new GameContextEntity(null, originCurrentTurnOwnTeam, testGameState);
        GameContextEntity updateEntity = new GameContextEntity(null, expectCurrentTurnOwnTeam, testGameState);

        Long savedEntityDatabaseId = gameContextRepository.save(originEntity);

        //when

        gameContextRepository.update(savedEntityDatabaseId, updateEntity);
        GameContextEntity result = gameContextRepository.find(savedEntityDatabaseId);

        assertEquals(expectCurrentTurnOwnTeam, result.currentTurnOwnTeam());
    }
}
