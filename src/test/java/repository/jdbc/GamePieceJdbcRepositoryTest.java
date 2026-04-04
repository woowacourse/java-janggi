package repository.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.entity.GamePiece;

class GamePieceJdbcRepositoryTest {

    private static final String CONFIG_FILE_NAME = "database.properties";

    private static final JdbcConnectionGenerator CONNECTION_GENERATOR = JdbcConnectionGenerator.create(
            CONFIG_FILE_NAME);
    private static final Connection DB_CONNECTION = CONNECTION_GENERATOR.getDBConnection();

    private final JdbcTemplate template = new JdbcTemplate();
    private final GamePieceJdbcRepository gamePieceRepository = new GamePieceJdbcRepository(template);

    @BeforeEach
    void setUp() throws SQLException {
        template.executeCommand(DB_CONNECTION,
                "CREATE TABLE IF NOT EXISTS games (game_id BIGINT PRIMARY KEY AUTO_INCREMENT)");
        template.executeCommand(DB_CONNECTION,
                "CREATE TABLE IF NOT EXISTS pieces (piece_id BIGINT PRIMARY KEY AUTO_INCREMENT)");

        template.executeCommand(DB_CONNECTION, "INSERT INTO games (game_id) VALUES (1)");
        template.executeCommand(DB_CONNECTION, "INSERT INTO pieces (piece_id) VALUES (1)");
        template.executeCommand(DB_CONNECTION, "INSERT INTO pieces (piece_id) VALUES (2)");

        gamePieceRepository.initTable(DB_CONNECTION);
    }

    @AfterEach
    void clearAll() throws SQLException {
        String sql = "DROP ALL OBJECTS";
        template.executeCommand(DB_CONNECTION, sql);
    }

    @Test
    @DisplayName("하나의 GamePiece 잘 저장한다")
    void save_success() throws SQLException {
        GamePiece gamePiece = new GamePiece(null, 1L, 1L, 0, 0, true);
        Long id = gamePieceRepository.save(DB_CONNECTION, gamePiece);
        assertNotNull(id);
    }

    @Test
    @DisplayName("여러 GamePiece 잘 저장한다")
    void save_all_success() throws SQLException {
        List<GamePiece> gamePieces = List.of(
                new GamePiece(null, 1L, 1L, 0, 0, true),
                new GamePiece(null, 1L, 2L, 1, 1, true)
        );
        List<Long> ids = gamePieceRepository.saveAll(DB_CONNECTION, gamePieces);
        assertEquals(2, ids.size());
    }

    @Test
    @DisplayName("하나의 GamePiece 잘 찾는다")
    void find_success() throws SQLException {
        GamePiece gamePiece = new GamePiece(null, 1L, 1L, 0, 0, true);
        Long id = gamePieceRepository.save(DB_CONNECTION, gamePiece);

        GamePiece found = gamePieceRepository.find(DB_CONNECTION, id);
        assertEquals(1L, found.gameId());
        assertEquals(1L, found.pieceId());
        assertEquals(0, found.row());
        assertEquals(0, found.col());
        assertTrue(found.isActive());
    }

    @Test
    @DisplayName("전체 GamePiece 잘 찾는다")
    void find_all_success() throws SQLException {
        List<GamePiece> gamePieces = List.of(
                new GamePiece(null, 1L, 1L, 0, 0, true),
                new GamePiece(null, 1L, 2L, 1, 1, true)
        );
        gamePieceRepository.saveAll(DB_CONNECTION, gamePieces);

        List<GamePiece> foundList = gamePieceRepository.findAll(DB_CONNECTION);
        assertEquals(2, foundList.size());
    }

    @Test
    @DisplayName("GamePiece 수정이 잘 된다")
    void update_success() throws SQLException {
        GamePiece gamePiece = new GamePiece(null, 1L, 1L, 0, 0, true);
        Long id = gamePieceRepository.save(DB_CONNECTION, gamePiece);

        GamePiece newGamePiece = new GamePiece(id, 1L, 1L, 2, 2, false);
        gamePieceRepository.update(DB_CONNECTION, newGamePiece);

        GamePiece updated = gamePieceRepository.find(DB_CONNECTION, id);
        assertEquals(2, updated.row());
        assertEquals(2, updated.col());
        assertEquals(false, updated.isActive());
    }
}
