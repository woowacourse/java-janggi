package janggi.repository.h2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import janggi.domain.side.Side;
import janggi.entity.GameEntity;
import janggi.entity.MoveEntity;
import janggi.entity.Status;
import janggi.repository.GameRepository;
import janggi.repository.JdbcDataSource;
import janggi.repository.MoveRepository;
import janggi.view.BoardSetUpFormat;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class H2MoveRepositoryTest {
    private JdbcDataSource testDataSource;
    private GameRepository gameRepository;
    private MoveRepository moveRepository;

    @BeforeEach
    void setUp() {
        // 테스트용 DataSource 주입
        testDataSource = new TestH2DataSource();
        gameRepository = new H2GameRepository(testDataSource);
        moveRepository = new H2MoveRepository(testDataSource);  // ← 주입
        clearDatabase();
    }

    private void clearDatabase() {
        try (Connection conn = testDataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM move");
            stmt.execute("DELETE FROM game");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 이동_저장() {
        // Arrange
        GameEntity game = new GameEntity(
                null, "테스트",
                BoardSetUpFormat.IN_ELEPHANT,
                BoardSetUpFormat.IN_ELEPHANT,
                Status.IN_PROGRESS,
                null
        );
        Integer gameId = gameRepository.save(game);

        MoveEntity move = new MoveEntity(
                null, gameId, 1, Side.CHO, 0, 0, 1, 1
        );

        // Act
        moveRepository.save(move);

        // Assert
        MoveEntity found = moveRepository.findById(1);
        assertNotNull(found);
        assertEquals(gameId, found.gameId());
    }
}
