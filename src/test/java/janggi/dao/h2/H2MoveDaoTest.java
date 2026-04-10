package janggi.dao.h2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import janggi.dao.GameDao;
import janggi.dao.JdbcDataSource;
import janggi.dao.MoveDao;
import janggi.dao.entity.GameEntity;
import janggi.dao.entity.MoveEntity;
import janggi.domain.game.Status;
import janggi.domain.piece.PieceType;
import janggi.domain.side.Side;
import janggi.view.BoardSetUpFormat;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class H2MoveDaoTest {
    private JdbcDataSource testDataSource;
    private GameDao gameDao;
    private MoveDao moveDao;

    @BeforeEach
    void setUp() {
        // 테스트용 DataSource 주입
        testDataSource = new TestH2DataSource();
        gameDao = new H2GameDao(testDataSource);
        moveDao = new H2MoveDao(testDataSource);  // ← 주입
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
        Integer gameId = gameDao.save(game);

        MoveEntity move = new MoveEntity(
                null, gameId, PieceType.SOLDIER, Side.CHO, 0, 0, 1, 1
        );

        // Act
        moveDao.save(move);

        // Assert
        MoveEntity found = moveDao.findById(1);
        assertNotNull(found);
        assertEquals(gameId, found.gameId());
    }
}
