package repository.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.entity.GameContext;

class GameContextJdbcRepositoryTest {


    private static final String CONFIG_FILE_NAME = "database.properties";

    private static final JdbcConnectionGenerator CONNECTION_GENERATOR = JdbcConnectionGenerator.create(
            CONFIG_FILE_NAME);
    private static final Connection DB_CONNECTION = CONNECTION_GENERATOR.getDBConnection();

    private static final String CREATE_GAME_CONTEXT_TABLE_SQL = "CREATE TABLE IF NOT EXISTS game_contexts (" +
            "game_context_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "current_turn_own_team VARCHAR(10) NOT NULL, " +
            "game_state VARCHAR(10) NOT NULL)";

    private final JdbcTemplate template = new JdbcTemplate();
    private final GameContextJdbcRepository gameContextRepository = new GameContextJdbcRepository(template);

    @BeforeEach
    void setUp() throws SQLException {
        template.executeCommand(DB_CONNECTION, CREATE_GAME_CONTEXT_TABLE_SQL);
    }

    @AfterEach
    void clearAll() throws SQLException {
        String sql = "DROP ALL OBJECTS";
        template.executeCommand(DB_CONNECTION, sql);
    }

    @Test
    @DisplayName("단일 GameContext에 대해 잘 저장한다")
    void save_success() throws SQLException {
        //given
        GameContext testEntity = new GameContext(null, "CHO", "PLAYING");

        //when
        Long savedEntityDatabaseId = gameContextRepository.save(DB_CONNECTION, testEntity);

        assertNotNull(savedEntityDatabaseId);
    }

    @Test
    @DisplayName("단일 GameContext에 대해 ID로 잘 가져온다")
    void find_success() throws SQLException {
        //given
        String testCurrentTurnOwnTeam = "CHO";
        String testGameState = "PLAYING";
        GameContext testEntity = new GameContext(null, testCurrentTurnOwnTeam, testGameState);
        Long savedEntityDatabaseId = gameContextRepository.save(DB_CONNECTION, testEntity);

        GameContext expectResult = new GameContext(savedEntityDatabaseId, testCurrentTurnOwnTeam,
                testGameState);

        //when
        GameContext result = gameContextRepository.find(DB_CONNECTION, savedEntityDatabaseId);

        assertEquals(expectResult, result);
    }

    @Test
    @DisplayName("단일 GameContext에 대해 Update를 잘한다 : 현재 턴 소유 팀 변경")
    void updateCurrentTurnOwnTeam_success() throws SQLException {
        //given
        String originCurrentTurnOwnTeam = "CHO";
        String expectCurrentTurnOwnTeam = "HAN";
        String testGameState = "PLAYING";

        GameContext originEntity = new GameContext(null, originCurrentTurnOwnTeam, testGameState);
        GameContext updateEntity = new GameContext(null, expectCurrentTurnOwnTeam, testGameState);

        Long savedEntityDatabaseId = gameContextRepository.save(DB_CONNECTION, originEntity);

        //when

        gameContextRepository.update(DB_CONNECTION, savedEntityDatabaseId, updateEntity);
        GameContext result = gameContextRepository.find(DB_CONNECTION, savedEntityDatabaseId);

        assertEquals(expectCurrentTurnOwnTeam, result.currentTurnOwnTeam());
    }
}
