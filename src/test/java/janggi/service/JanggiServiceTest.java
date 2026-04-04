package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.Board;
import janggi.model.Janggi;
import janggi.model.Team;
import janggi.model.initializer.LeftSidedTableSetting;
import janggi.repository.InMemoryGameRepository;
import janggi.repository.InMemoryGimulRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiServiceTest {

    JanggiService janggiService;

    @BeforeEach
    void beforeEach() {
        janggiService = new JanggiService(
                new InMemoryGameRepository(),
                new InMemoryGimulRepository()
        );
    }

    @DisplayName("새 게임을 생성하고 ID를 반환한다.")
    @Test
    void createGame() {
        Long gameId = janggiService.createGame("테스트게임", Team.CHO);
        assertThat(gameId).isNotNull();
    }

    @DisplayName("게임 이름 목록을 반환한다.")
    @Test
    void findAllGameNames() {
        janggiService.createGame("게임1", Team.CHO);
        janggiService.createGame("게임2", Team.HAN);

        assertThat(janggiService.findAllGameNames())
                .containsExactlyInAnyOrder("게임1", "게임2");
    }

    @DisplayName("게임 이름으로 ID를 반환한다.")
    @Test
    void findIdByName() {
        janggiService.createGame("테스트게임", Team.CHO);
        Long gameId = janggiService.findIdByName("테스트게임");
        assertThat(gameId).isNotNull();
    }

    @DisplayName("게임을 저장하고 불러올 수 있다.")
    @Test
    void saveAndLoad() {
        Long gameId = janggiService.createGame("테스트게임", Team.CHO);
        Board board = new LeftSidedTableSetting().init();
        janggiService.save(gameId, board, Team.HAN);

        Janggi janggi = janggiService.loadGameByName("테스트게임");
        assertThat(janggi).isNotNull();
        assertThat(janggi.isGameOver()).isFalse();
    }

    @DisplayName("게임을 삭제하면 게임 목록에서 사라진다.")
    @Test
    void deleteGame() {
        Long gameId = janggiService.createGame("테스트게임", Team.CHO);
        janggiService.deleteGame(gameId);

        assertThat(janggiService.findAllGameNames())
                .doesNotContain("테스트게임");
    }
}
