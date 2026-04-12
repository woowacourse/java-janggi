package janggi.persistence.dao;

import janggi.config.ConnectionPool;
import janggi.config.PooledConnection;
import janggi.config.TestConnectionPool;
import janggi.config.TestDBInitializer;
import janggi.exception.DuplicateGameException;
import janggi.persistence.entity.GameEntity;
import janggi.persistence.entity.vo.Status;
import janggi.persistence.entity.vo.Turn;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JdbcGameDaoTest {
    private static ConnectionPool pool;
    private GameDao gameDao;

    @BeforeAll
    static void setUpPool() {
        pool = TestConnectionPool.create();
        TestDBInitializer.initSchema(pool);
    }

    @BeforeEach
    void setUp() {
        gameDao = new JdbcGameDao(pool);
        TestDBInitializer.clearAll(pool);
    }

    @Test
    void 게임을_저장하고_조회할_수_있다() {
        GameEntity entity = new GameEntity("id-1", "테스트게임", Status.PLAYING, Turn.CHO);

        try (PooledConnection pooled = pool.getPooledConnection()) {
            gameDao.create(pooled.getConnection(), entity);
        }

        Optional<GameEntity> result = gameDao.findById("id-1");

        assertThat(result).isPresent();
        assertThat(result.get().name()).isEqualTo("테스트게임");
    }

    @Test
    void 같은_이름으로_저장하면_예외가_발생한다() {
        GameEntity entity1 = new GameEntity("id-1", "같은이름", Status.PLAYING, Turn.CHO);
        GameEntity entity2 = new GameEntity("id-2", "같은이름", Status.PLAYING, Turn.CHO);

        try (PooledConnection pooled = pool.getPooledConnection()) {
            gameDao.create(pooled.getConnection(), entity1);

            assertThatThrownBy(() -> gameDao.create(pooled.getConnection(), entity2))
                    .isInstanceOf(DuplicateGameException.class);
        }
    }

    @Test
    void 게임을_삭제할_수_있다() {
        GameEntity entity = new GameEntity("id-1", "삭제게임", Status.PLAYING, Turn.CHO);

        try (PooledConnection pooled = pool.getPooledConnection()) {
            gameDao.create(pooled.getConnection(), entity);
            gameDao.deleteById(pooled.getConnection(), "id-1");
        }

        Optional<GameEntity> result = gameDao.findById("id-1");
        assertThat(result).isEmpty();
    }

    @Test
    void 모든_게임_이름을_조회할_수_있다() {
        try (PooledConnection pooled = pool.getPooledConnection()) {
            gameDao.create(pooled.getConnection(),
                    new GameEntity("id-1", "게임1", Status.PLAYING, Turn.CHO));
            gameDao.create(pooled.getConnection(),
                    new GameEntity("id-2", "게임2", Status.PLAYING, Turn.HAN));
        }

        List<String> names = gameDao.findAllNames();

        assertThat(names).containsExactlyInAnyOrder("게임1", "게임2");
    }

    @Test
    void 이름으로_게임ID를_조회할_수_있다() {
        try (PooledConnection pooled = pool.getPooledConnection()) {
            gameDao.create(pooled.getConnection(),
                    new GameEntity("id-1", "찾을게임", Status.PLAYING, Turn.CHO));
        }

        Optional<String> result = gameDao.findByName("찾을게임");

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo("id-1");
    }

    @Test
    void 게임_상태를_업데이트할_수_있다() {
        try (PooledConnection pooled = pool.getPooledConnection()) {
            gameDao.create(pooled.getConnection(),
                    new GameEntity("id-1", "업데이트게임", Status.PLAYING, Turn.CHO));
            gameDao.updateStatus(pooled.getConnection(), "id-1", Turn.HAN, Status.HAN_WIN);
        }

        Optional<GameEntity> result = gameDao.findById("id-1");

        assertThat(result).isPresent();
        assertThat(result.get().status()).isEqualTo(Status.HAN_WIN);
        assertThat(result.get().turn()).isEqualTo(Turn.HAN);
    }

    @AfterAll
    static void tearDown() {
        pool.shutdown();
    }
}