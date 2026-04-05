package repository.jdbc;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.entity.GameEntity;

class GameJdbcRepositoryTest {

    private static final String CONFIG_FILE_NAME = "database.properties";

    private static final JdbcConnectionGenerator CONNECTION_GENERATOR = JdbcConnectionGenerator.create(
            CONFIG_FILE_NAME);
    private static final Connection DB_CONNECTION = CONNECTION_GENERATOR.getDBConnection();

    private final JdbcTemplate template = new JdbcTemplate();
    private final GameJdbcRepository gameRepository = new GameJdbcRepository(template);

    @BeforeEach
    void setUp() {
        gameRepository.initTable(DB_CONNECTION);
    }

    @AfterEach
    void clearAll() {
        template.executeCommand(DB_CONNECTION, "DROP ALL OBJECTS");
    }

    @Test
    @DisplayName("Game 저장을 위한 테이블을 잘 구성한다")
    void initTable() {
        template.executeCommand(DB_CONNECTION, "DROP ALL OBJECTS");

        assertDoesNotThrow(
                () -> gameRepository.initTable(DB_CONNECTION)
        );
    }

    @Test
    @DisplayName("단일 Game 잘 저장한다")
    void save_success() {
        GameEntity gameEntity = new GameEntity(null, "CHO", "PLAYING");

        Long savedId = gameRepository.save(DB_CONNECTION, gameEntity);
        assertNotNull(savedId);
    }

    @Test
    @DisplayName("단일 Game 잘 가져온다")
    void find_success() {
        GameEntity gameEntity = new GameEntity(null, "CHO", "PLAYING");
        Long savedId = gameRepository.save(DB_CONNECTION, gameEntity);

        GameEntity found = gameRepository.find(DB_CONNECTION, savedId);
        assertNotNull(found);
        assertEquals(savedId, found.id());
        assertEquals("CHO", found.turn());
        assertEquals("PLAYING", found.state());
    }

    @Test
    @DisplayName("단일 Game을 상태를 기반으로 잘 가져온다")
    void findByState() {
        //given
        int expectPlayingGameSelectSize = 2;
        String testGameState = "PLAYING";
        GameEntity gameEntity = new GameEntity(null, "CHO", testGameState);
        GameEntity gameEntity2 = new GameEntity(null, "HAN", testGameState);
        Long savedId = gameRepository.save(DB_CONNECTION, gameEntity);
        Long savedId2 = gameRepository.save(DB_CONNECTION, gameEntity2);
        List<Long> savedIds = List.of(savedId, savedId2);

        //when
        List<GameEntity> playingGames = gameRepository.findByState(DB_CONNECTION, testGameState);
        List<Long> playingGamesIds = playingGames.stream()
                .map(game -> game.id())
                .toList();

        //then
        assertEquals(expectPlayingGameSelectSize, playingGames.size());
        assertEquals(savedIds, playingGamesIds);
    }

    @Test
    @DisplayName("Game 정보를 잘 수정한다")
    void update_success() {
        GameEntity gameEntity = new GameEntity(null, "CHO", "PLAYING");
        Long savedId = gameRepository.save(DB_CONNECTION, gameEntity);

        GameEntity updatedEntity = new GameEntity(savedId, "HAN", "PLAYING");
        gameRepository.update(DB_CONNECTION, savedId, updatedEntity);

        GameEntity updated = gameRepository.find(DB_CONNECTION, savedId);
        assertEquals("HAN", updated.turn());
    }
}
