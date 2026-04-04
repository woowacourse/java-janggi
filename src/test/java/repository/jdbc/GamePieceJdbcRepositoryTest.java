package repository.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.entity.GamePiece;

class GamePieceJdbcRepositoryTest {

    private static final String CONFIG_FILE_NAME = "database.properties";

    private final JdbcTemplate template = new JdbcTemplate(JdbcConnectionGenerator.create(CONFIG_FILE_NAME));
    private final GamePieceJdbcRepository gamePieceRepository = new GamePieceJdbcRepository(template);

    @BeforeEach
    void setUp() throws SQLException {
        template.executeCommand("CREATE TABLE IF NOT EXISTS games (game_id BIGINT PRIMARY KEY AUTO_INCREMENT)");
        template.executeCommand("CREATE TABLE IF NOT EXISTS pieces (piece_id BIGINT PRIMARY KEY AUTO_INCREMENT)");
        
        template.executeCommand("INSERT INTO games (game_id) VALUES (1)");
        template.executeCommand("INSERT INTO pieces (piece_id) VALUES (1)");
        template.executeCommand("INSERT INTO pieces (piece_id) VALUES (2)");

        gamePieceRepository.initTable();
    }

    @AfterEach
    void clearAll() throws SQLException {
        String sql = "DROP ALL OBJECTS";
        template.executeCommand(sql);
    }

    @Test
    @DisplayName("하나의 GamePiece 잘 저장한다")
    void save_success() throws SQLException {
        GamePiece gamePiece = new GamePiece(null, 1L, 1L, 0, 0, true);
        Long id = gamePieceRepository.save(gamePiece);
        assertNotNull(id);
    }

    @Test
    @DisplayName("여러 GamePiece 잘 저장한다")
    void save_all_success() throws SQLException {
        List<GamePiece> gamePieces = List.of(
                new GamePiece(null, 1L, 1L, 0, 0, true),
                new GamePiece(null, 1L, 2L, 1, 1, true)
        );
        List<Long> ids = gamePieceRepository.saveAll(gamePieces);
        assertEquals(2, ids.size());
    }

    @Test
    @DisplayName("하나의 GamePiece 잘 찾는다")
    void find_success() throws SQLException {
        GamePiece gamePiece = new GamePiece(null, 1L, 1L, 0, 0, true);
        Long id = gamePieceRepository.save(gamePiece);
        
        GamePiece found = gamePieceRepository.find(id);
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
        gamePieceRepository.saveAll(gamePieces);
        
        List<GamePiece> foundList = gamePieceRepository.findAll();
        assertEquals(2, foundList.size());
    }

    @Test
    @DisplayName("GamePiece 수정이 잘 된다")
    void update_success() throws SQLException {
        GamePiece gamePiece = new GamePiece(null, 1L, 1L, 0, 0, true);
        Long id = gamePieceRepository.save(gamePiece);
        
        GamePiece newGamePiece = new GamePiece(id, 1L, 1L, 2, 2, false);
        gamePieceRepository.update(newGamePiece);
        
        GamePiece updated = gamePieceRepository.find(id);
        assertEquals(2, updated.row());
        assertEquals(2, updated.col());
        assertEquals(false, updated.isActive());
    }
}
