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
                new InMemoryGimulRepository(),
                new TransactionManager()
        );
    }

    @DisplayName("새 게임을 생성하고 ID를 반환한다.")
    @Test
    void createGame() {
        //given
        Long gameId = janggiService.createGame("테스트게임", Team.CHO);

        //when & then
        assertThat(gameId).isNotNull();
    }

    @DisplayName("현재 기존 게임이 존재하지 않는다면 false를 반환한다. ")
    @Test
    void existsGame_false() {
        //given & when & then
        assertThat(janggiService.existsGame()).isFalse();
    }

    @DisplayName("현재 기존 게임이 존재한다면 true를 반환한다. ")
    @Test
    void existsGame_true() {
        //given
        janggiService.createGame("game1", Team.CHO);

        //when & then
        assertThat(janggiService.existsGame()).isTrue();
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

        Long gameIdToLoad = janggiService.findIdByName("테스트게임");
        Janggi janggi = janggiService.loadJanggiGameById(gameIdToLoad);
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
