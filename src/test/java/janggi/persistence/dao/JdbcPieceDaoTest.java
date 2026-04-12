package janggi.persistence.dao;

import janggi.config.ConnectionPool;
import janggi.config.PooledConnection;
import janggi.config.TestConnectionPool;
import janggi.config.TestDBInitializer;
import janggi.persistence.entity.PieceEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcPieceDaoTest {
    private static ConnectionPool pool;
    private PieceDao pieceDao;

    @BeforeAll
    static void setUpPool() {
        pool = TestConnectionPool.create();
        TestDBInitializer.initSchema(pool);
    }

    @BeforeEach
    void setUp() {
        pieceDao = new JdbcPieceDao(pool);
        TestDBInitializer.clearAll(pool);
    }

    @Test
    void 기물을_저장하고_조회할_수_있다() {
        List<PieceEntity> pieces = List.of(
                new PieceEntity(0L, "game-1", "차", "CHO", 0, 0),
                new PieceEntity(0L, "game-1", "마", "CHO", 0, 1)
        );

        try (PooledConnection pooled = pool.getPooledConnection()) {
            pieceDao.createAll(pooled.getConnection(), pieces);
        }

        List<PieceEntity> result = pieceDao.findByGameId("game-1");
        assertThat(result).hasSize(2);
    }

    @Test
    void 게임ID로_기물을_삭제할_수_있다() {
        List<PieceEntity> pieces = List.of(
                new PieceEntity(0L, "game-1", "차", "CHO", 0, 0)
        );

        try (PooledConnection pooled = pool.getPooledConnection()) {
            pieceDao.createAll(pooled.getConnection(), pieces);
            pieceDao.deleteByGameId(pooled.getConnection(), "game-1");
        }

        assertThat(pieceDao.findByGameId("game-1")).isEmpty();
    }

    @AfterAll
    static void tearDown() {
        pool.shutdown();
    }
}
