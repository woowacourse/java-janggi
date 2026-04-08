package janggi.repositiory.game;

import janggi.domain.piece.Team;
import janggi.repositiory.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcGameRepositoryTest extends RepositoryTest {
    private JdbcGameRepository gameRepository;

    @BeforeEach
    void setUp() {
        gameRepository = new JdbcGameRepository(dataSource);
    }

    @Test
    void save_테스트() {
        // when
        Long id = gameRepository.save(false, Team.CHO);

        // then
        assertThat(id).isNotNull();
        assertThat(id).isPositive();
    }

    @Test
    void findLatest_테스트() {
        // given
        gameRepository.save(true, Team.HAN);
        Long expectedId = gameRepository.save(false, Team.CHO);

        // when
        Optional<GameData> latest = gameRepository.findLatestOngoingGame();

        // then
        assertThat(latest).isPresent();
        assertThat(latest.get().gameId()).isEqualTo(expectedId);
        assertThat(latest.get().currentTurn()).isEqualTo(Team.CHO);
    }

    @Test
    void update_테스트() {
        // given
        Long id = gameRepository.save(false, Team.CHO);

        // when
        gameRepository.update(id, true, Team.HAN);

        // then
        GameData updated = gameRepository.findLatestOngoingGame().get();
        assertThat(updated.isFinished()).isTrue();
        assertThat(updated.currentTurn()).isEqualTo(Team.HAN);
    }
}