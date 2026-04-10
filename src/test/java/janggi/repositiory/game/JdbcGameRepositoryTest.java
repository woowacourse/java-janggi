package janggi.repositiory.game;

import janggi.domain.board.Board;
import janggi.domain.janggiGame.JanggiGame;
import janggi.domain.piece.Team;
import janggi.domain.vo.FinishStatus;
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
        Long id = gameRepository.save(new FinishStatus(false), Team.CHO);

        // then
        assertThat(id).isNotNull();
        assertThat(id).isPositive();
    }

    @Test
    void findLatest_테스트() {
        // given
        gameRepository.save(new FinishStatus(true), Team.HAN);
        Long latestId = gameRepository.save(new FinishStatus(false), Team.CHO);

        // when
        Optional<GameData> latest = gameRepository.findLatestGame();

        // then
        assertThat(latest).isPresent();
        assertThat(latest.get().gameId()).isEqualTo(latestId);
        assertThat(latest.get().currentTurn()).isEqualTo(Team.CHO);
    }

    @Test
    void update_테스트() {
        // given
        Long id = gameRepository.save(new FinishStatus(false), Team.CHO);
        JanggiGame janggiGame = new JanggiGame(new Board(), Team.HAN);

        // when
        gameRepository.update(id, janggiGame);

        // then
        GameData updated = gameRepository.findLatestGame().get();
        assertThat(updated.isFinished()).isTrue();
        assertThat(updated.currentTurn()).isEqualTo(Team.HAN);
    }
}