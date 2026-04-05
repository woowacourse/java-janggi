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
import repository.entity.GamePieceEntity;

class GamePieceJdbcRepositoryTest {

    private static final String CONFIG_FILE_NAME = "database.properties";

    private static final JdbcConnectionGenerator CONNECTION_GENERATOR = JdbcConnectionGenerator.create(
            CONFIG_FILE_NAME);
    private static final Connection DB_CONNECTION = CONNECTION_GENERATOR.getDBConnection();

    private final JdbcTemplate template = new JdbcTemplate();
    private final GameJdbcRepository gameRepository = new GameJdbcRepository(template);
    private final GamePieceJdbcRepository gamePieceRepository = new GamePieceJdbcRepository(template);

    @BeforeEach
    void setUp() {
        gameRepository.initTable(DB_CONNECTION);
        gamePieceRepository.initTable(DB_CONNECTION);
    }

    @AfterEach
    void clearAll() {
        template.executeCommand(DB_CONNECTION, "DROP ALL OBJECTS");
    }

    @Test
    @DisplayName("GamePiece 저장을 위한 테이블을 잘 구성한다")
    void initTable() {
        template.executeCommand(DB_CONNECTION, "DROP ALL OBJECTS");
        gameRepository.initTable(DB_CONNECTION);

        assertDoesNotThrow(
                () -> gamePieceRepository.initTable(DB_CONNECTION)
        );
    }

    @Test
    @DisplayName("단일 GamePiece를 잘 저장한다")
    void save_success() {
        // given
        Long gameId = gameRepository.save(DB_CONNECTION, new GameEntity(null, "CHO", "PLAYING"));
        GamePieceEntity pieceEntity = new GamePieceEntity(null, gameId, "JOL", "CHO", 4, 1, true);

        // when
        Long savedId = gamePieceRepository.save(DB_CONNECTION, pieceEntity);

        // then
        assertNotNull(savedId);
    }

    @Test
    @DisplayName("여러 GamePiece를 한 번에 잘 저장한다")
    void saveAll_success() {
        // given
        int expectSavedIdsSize = 2;
        Long gameId = gameRepository.save(DB_CONNECTION, new GameEntity(null, "CHO", "PLAYING"));
        List<GamePieceEntity> pieceEntities = List.of(
                new GamePieceEntity(null, gameId, "JOL", "CHO", 4, 1, true),
                new GamePieceEntity(null, gameId, "PO", "CHO", 3, 2, true)
        );

        // when
        List<Long> savedIds = gamePieceRepository.saveAll(DB_CONNECTION, pieceEntities);

        // then
        assertEquals(expectSavedIdsSize, savedIds.size());
    }

    @Test
    @DisplayName("단일 GamePiece를 id로 잘 찾아온다")
    void find_success() {
        // given
        String team = "CHO";
        String status = "PLAYING";
        String type = "JOL";
        int row = 4;
        int col = 1;
        boolean active = true;

        Long gameId = gameRepository.save(DB_CONNECTION, new GameEntity(null, team, status));

        GamePieceEntity pieceEntity = new GamePieceEntity(null, gameId, type, team, row, col, active);
        Long savedId = gamePieceRepository.save(DB_CONNECTION, pieceEntity);

        // when
        GamePieceEntity result = gamePieceRepository.find(DB_CONNECTION, savedId);

        // then
        GamePieceEntity expectPieceEntity = new GamePieceEntity(result.id(), gameId, type, team, row, col, active);
        assertEquals(expectPieceEntity, result);
    }

    @Test
    @DisplayName("gameId로 연관된 모든 GamePiece를 찾아온다")
    void findByGameId_success() {
        // given
        int expectSize = 2;
        Long gameId1 = gameRepository.save(DB_CONNECTION, new GameEntity(null, "CHO", "PLAYING"));
        Long gameId2 = gameRepository.save(DB_CONNECTION, new GameEntity(null, "HAN", "PLAYING"));

        gamePieceRepository.save(DB_CONNECTION, new GamePieceEntity(null, gameId1, "JOL", "CHO", 4, 1, true));
        gamePieceRepository.save(DB_CONNECTION, new GamePieceEntity(null, gameId1, "PO", "CHO", 3, 2, true));
        gamePieceRepository.save(DB_CONNECTION, new GamePieceEntity(null, gameId2, "SA", "HAN", 1, 4, true));

        // when
        List<GamePieceEntity> results = gamePieceRepository.findByGameId(DB_CONNECTION, gameId1);

        // then
        assertEquals(expectSize, results.size());
    }

    @Test
    @DisplayName("모든 GamePiece를 찾아온다")
    void findAll_success() {
        // given
        Long gameId = gameRepository.save(DB_CONNECTION, new GameEntity(null, "CHO", "PLAYING"));
        gamePieceRepository.save(DB_CONNECTION, new GamePieceEntity(null, gameId, "JOL", "CHO", 4, 1, true));
        gamePieceRepository.save(DB_CONNECTION, new GamePieceEntity(null, gameId, "PO", "CHO", 3, 2, true));
        int expectSize = 2;

        // when
        List<GamePieceEntity> allPieces = gamePieceRepository.findAll(DB_CONNECTION);

        // then
        assertEquals(expectSize, allPieces.size());
    }

    @Test
    @DisplayName("여러 GamePiece의 상태를 정상적으로 변경한다")
    void updateAll_success() {
        // given
        Long gameId = gameRepository.save(DB_CONNECTION, new GameEntity(null, "CHO", "PLAYING"));

        GamePieceEntity newPiece1 = new GamePieceEntity(null, gameId, "JOL", "CHO", 4, 1, true);
        GamePieceEntity newPiece2 = new GamePieceEntity(null, gameId, "PO", "CHO", 3, 2, true);

        Long savedId1 = gamePieceRepository.save(DB_CONNECTION, newPiece1);
        Long savedId2 = gamePieceRepository.save(DB_CONNECTION, newPiece2);

        GamePieceEntity updatedPiece1 = new GamePieceEntity(savedId1, gameId, "JOL", "CHO", 5, 1, true);
        GamePieceEntity updatedPiece2 = new GamePieceEntity(savedId2, gameId, "PO", "CHO", 3, 2, false);
        List<GamePieceEntity> updatedEntities = List.of(updatedPiece1, updatedPiece2);

        // when
        gamePieceRepository.updateAll(DB_CONNECTION, updatedEntities);

        // then
        GamePieceEntity result1 = gamePieceRepository.find(DB_CONNECTION, savedId1);
        GamePieceEntity result2 = gamePieceRepository.find(DB_CONNECTION, savedId2);

        assertEquals(updatedPiece1, result1);
        assertEquals(updatedPiece2, result2);
    }
}
