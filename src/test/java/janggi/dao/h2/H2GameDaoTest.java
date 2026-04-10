package janggi.dao.h2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import janggi.dao.GameDao;
import janggi.dao.entity.GameEntity;
import janggi.domain.game.Status;
import janggi.domain.side.Side;
import janggi.view.BoardSetUpFormat;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// H2GameRepositoryTest.java
class H2GameDaoTest {

    private TestH2DataSource dataSource;
    private GameDao gameDao;

    private GameEntity game;

    @BeforeEach
    void setUp() {
        dataSource = new TestH2DataSource();
        gameDao = new H2GameDao(dataSource);
        game = new GameEntity(
                null,
                "테스트 게임",
                BoardSetUpFormat.IN_ELEPHANT,
                BoardSetUpFormat.IN_ELEPHANT,
                Status.IN_PROGRESS,
                null
        );
        clearDatabase();
    }

    private void clearDatabase() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM move");
            stmt.execute("DELETE FROM game");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 게임_저장_후_ID가_반환되어야_함() {
        // Arrange
        // Act
        Integer id = gameDao.save(game);

        // Assert
        assertNotNull(id);
        assertTrue(id > 0);
    }

    @Test
    void 저장한_게임을_ID로_조회() {
        // Arrange
        Integer id = gameDao.save(game);

        // Act
        GameEntity found = gameDao.findById(id);

        // Assert
        assertNotNull(found);
        assertEquals("테스트 게임", found.name());
        assertEquals(id, found.id());
    }

    @Test
    void 게임_이름으로_조회() {
        // Arrange
        gameDao.save(game);

        // Act
        Optional<GameEntity> found = gameDao.findByName("테스트 게임");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("테스트 게임", found.get().name());
    }

    @Test
    void 게임_승자_업데이트() {
        // Arrange
        Integer id = gameDao.save(game);

        // Act
        gameDao.updateWinner(id, Side.CHO);
        GameEntity updated = gameDao.findById(id);

        // Assert
        assertEquals(Side.CHO, updated.winner());
    }
}
