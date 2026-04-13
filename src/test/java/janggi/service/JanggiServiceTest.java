package janggi.service;

import janggi.domain.JanggiGame;
import janggi.domain.Team;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.dto.GameInfo;
import janggi.repository.FakeGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static janggi.domain.board.PieceSetup.OUTER_ELEPHANT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class JanggiServiceTest {

    private JanggiService service;

    @BeforeEach
    void setUp() {
        service = new JanggiService(new FakeGameRepository());
    }

    @DisplayName("새 게임을 생성하면 ID를 반환한다.")
    @Test
    void 새_게임을_생성하면_ID를_반환한다() {
        // when
        long gameId = service.createGame(OUTER_ELEPHANT, OUTER_ELEPHANT);

        // then
        assertThat(gameId).isEqualTo(1L);
    }


    @DisplayName("저장된 게임을 불러올 수 있다.")
    @Test
    void 저장된_게임을_불러올_수_있다() {
        // given
        long gameId = service.createGame(OUTER_ELEPHANT, OUTER_ELEPHANT);

        // when
        JanggiGame game = service.loadGame(gameId);

        // then
        assertAll(
                () -> assertThat(game.getBoard()).hasSize(90),
                () -> assertThat(game.getCurrentTeam()).isEqualTo(Team.FIRST_TURN)
        );
    }

    @DisplayName("턴을 진행하면 현재 팀이 전환된다.")
    @Test
    void 턴을_진행하면_현재_팀이_전환된다() {
        // given
        long gameId = service.createGame(OUTER_ELEPHANT, OUTER_ELEPHANT);
        Movement movement = new Movement(Position.from("71"), Position.from("61"));

        // when
        service.playTurn(gameId, movement);

        // then
        JanggiGame game = service.loadGame(gameId);
        assertThat(game.getCurrentTeam()).isEqualTo(Team.HAN);
    }

    @DisplayName("전체 게임 목록 조회 테스트")
    @Test
    void 게임_목록을_조회할_수_있다() {
        // given
        service.createGame(OUTER_ELEPHANT, OUTER_ELEPHANT);
        service.createGame(OUTER_ELEPHANT, OUTER_ELEPHANT);

        // when
        List<GameInfo> games = service.findAllGames();

        // then
        assertThat(games).hasSize(2);
    }

    @DisplayName("게임 삭제 테스트")
    @Test
    void 게임을_삭제할_수_있다() {
        // given
        long gameId = service.createGame(OUTER_ELEPHANT, OUTER_ELEPHANT);

        // when
        service.deleteGame(gameId);

        // then
        assertThat(service.findAllGames()).isEmpty();
    }

    @DisplayName("존재하지 않는 게임을 불러오면 예외가 발생한다.")
    @Test
    void 존재하지_않는_게임을_불러오면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> service.loadGame(999))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("존재하지 않는 게임을 삭제하면 예외가 발생한다.")
    @Test
    void 존재하지_않는_게임을_삭제하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> service.deleteGame(999))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
