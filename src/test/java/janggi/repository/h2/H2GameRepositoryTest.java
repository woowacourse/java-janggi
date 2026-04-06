package janggi.repository.h2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import janggi.domain.side.Side;
import janggi.entity.GameEntity;
import janggi.entity.SetUpEntity;
import janggi.entity.Status;
import janggi.repository.GameRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// H2GameRepositoryTest.java
class H2GameRepositoryTest {

    private TestH2DataSource dataSource;
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        dataSource = new TestH2DataSource();
        gameRepository = new H2GameRepository(dataSource);
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
        GameEntity game = new GameEntity(
                null,
                "테스트 게임",
                SetUpEntity.IN_ELEPHANT,
                SetUpEntity.IN_ELEPHANT,
                Status.IN_PROGRESS,
                null
        );

        // Act
        Integer id = gameRepository.save(game);

        // Assert
        assertNotNull(id);
        assertTrue(id > 0);
    }

    @Test
    void 저장한_게임을_ID로_조회() {
        // Arrange
        GameEntity game = new GameEntity(
                null,
                "테스트 게임",
                SetUpEntity.IN_ELEPHANT,
                SetUpEntity.IN_ELEPHANT,
                Status.IN_PROGRESS,
                null
        );
        Integer id = gameRepository.save(game);

        // Act
        GameEntity found = gameRepository.findById(id);

        // Assert
        assertNotNull(found);
        assertEquals("테스트 게임", found.name());
        assertEquals(id, found.id());
    }

    @Test
    void 게임_이름으로_조회() {
        // Arrange
        GameEntity game = new GameEntity(
                null,
                "테스트 게임",
                SetUpEntity.IN_ELEPHANT,
                SetUpEntity.IN_ELEPHANT,
                Status.IN_PROGRESS,
                null
        );
        gameRepository.save(game);

        // Act
        Optional<GameEntity> found = gameRepository.findByName("테스트 게임");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("테스트 게임", found.get().name());
    }

    @Test
    void 게임_승자_업데이트() {
        // Arrange
        GameEntity game = new GameEntity(
                null,
                "테스트 게임",
                SetUpEntity.IN_ELEPHANT,
                SetUpEntity.IN_ELEPHANT,
                Status.IN_PROGRESS,
                null
        );
        Integer id = gameRepository.save(game);

        // Act
        gameRepository.updateWinner(id, Side.CHO);
        GameEntity updated = gameRepository.findById(id);

        // Assert
        assertEquals(Side.CHO, updated.winner());
    }
}
