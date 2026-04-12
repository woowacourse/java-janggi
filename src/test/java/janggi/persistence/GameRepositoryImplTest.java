package janggi.persistence;

import janggi.domain.Janggi;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.FormationStrategyFactory;
import janggi.persistence.dao.FakeGameDao;
import janggi.persistence.dao.FakePieceDao;
import janggi.persistence.mapper.GameMapper;
import janggi.persistence.mapper.PieceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class GameRepositoryImplTest {
    private GameRepositoryImpl repository;
    private FakeGameDao fakeGameDao;

    @BeforeEach
    void setUp() {
        fakeGameDao = new FakeGameDao();
        repository = new GameRepositoryImpl(
                fakeGameDao,
                new FakePieceDao(),
                new GameMapper(),
                new PieceMapper()
        );
    }

    @Test
    void 게임을_저장하고_조회할_수_있다() {
        Janggi janggi = createTestJanggi();

        String gameId = repository.save(null, "테스트게임", janggi);

        Optional<Janggi> result = repository.findById(gameId);
        assertThat(result).isPresent();
    }

    @Test
    void 게임을_삭제하면_조회되지_않는다() {
        Janggi janggi = createTestJanggi();

        String gameId = repository.save(null, "삭제게임", janggi);
        repository.deleteById(null, gameId);

        Optional<Janggi> result = repository.findById(gameId);
        assertThat(result).isEmpty();
    }

    @Test
    void 같은_게임을_두번_조회하면_캐시에서_반환한다() {
        Janggi janggi = createTestJanggi();

        String gameId = repository.save(null, "캐시게임", janggi);

        repository.findById(gameId);
        repository.findById(gameId);

        assertThat(fakeGameDao.getFindByIdCount()).isZero();
    }

    private Janggi createTestJanggi() {
        return Janggi.start(
                BoardFactory.create(
                        FormationStrategyFactory.from(1),
                        FormationStrategyFactory.from(1))
        );
    }
}