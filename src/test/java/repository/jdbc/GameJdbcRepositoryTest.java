package repository.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.entity.Game;
import repository.entity.GameContext;

class GameJdbcRepositoryTest {

    private static final String CONFIG_FILE_NAME = "database.properties";

    private final JdbcTemplate template = new JdbcTemplate(JdbcConnectionGenerator.create(CONFIG_FILE_NAME));
    private final GameJdbcRepository gameRepository = new GameJdbcRepository(template);
    private final GameContextJdbcRepository gameContextRepository = new GameContextJdbcRepository(template);

    @BeforeEach
    void setUp() throws SQLException {
        template.executeCommand("CREATE TABLE IF NOT EXISTS game_contexts (" +
                "game_context_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "current_turn_own_team VARCHAR(10) NOT NULL, " +
                "game_state VARCHAR(10) NOT NULL)");

        template.executeCommand("CREATE TABLE IF NOT EXISTS games (" +
                "game_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "game_context_id BIGINT, " +
                "CONSTRAINT fk_game_context FOREIGN KEY (game_context_id) REFERENCES game_contexts(game_context_id) ON DELETE CASCADE)");
    }

    @AfterEach
    void clearAll() throws SQLException {
        String sql = "DROP ALL OBJECTS";
        template.executeCommand(sql);
    }

    @Test
    @DisplayName("단일 Game 잘 저장한다")
    void save_success() throws SQLException {
        Long contextId = gameContextRepository.save(new GameContext(null, "CHO", "PLAYING"));
        Game game = new Game(null, contextId, List.of());

        Long savedId = gameRepository.save(game);
        assertNotNull(savedId);
    }

    @Test
    @DisplayName("단일 Game 잘 가져온다")
    void find_success() throws SQLException {
        Long contextId = gameContextRepository.save(new GameContext(null, "CHO", "PLAYING"));
        Game game = new Game(null, contextId, List.of());
        Long savedId = gameRepository.save(game);

        Game found = gameRepository.find(savedId);
        assertNotNull(found);
        assertEquals(savedId, found.gameId());
        assertEquals(contextId, found.gameContextId());
    }

    @Test
    @DisplayName("Game 정보를 잘 수정한다")
    void update_success() throws SQLException {
        Long oldContextId = gameContextRepository.save(new GameContext(null, "CHO", "PLAYING"));
        Long newContextId = gameContextRepository.save(new GameContext(null, "HAN", "PLAYING"));

        Game game = new Game(null, oldContextId, List.of());
        Long savedId = gameRepository.save(game);

        GameContext newContext = new GameContext(newContextId, "HAN", "PLAYING");
        gameRepository.update(savedId, newContext);

        Game updated = gameRepository.find(savedId);
        assertEquals(newContextId, updated.gameContextId());
    }
}
