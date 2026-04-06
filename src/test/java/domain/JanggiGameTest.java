package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Team;
import domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    private JanggiGame janggiGame;

    @BeforeEach
    void setUpGame() {
        janggiGame = JanggiGame.init(SettingType.LEFT, SettingType.LEFT);
    }


    @Test
    @DisplayName("장기 게임을 초기화하면 초기 상태의 보드와 CHO 팀의 턴으로 시작한다.")
    void initJanggiGame() {
        assertThat(janggiGame.getTurnOwnTeam()).isEqualTo(Team.CHO);
        assertThat(janggiGame.getJanggiGameStatus()).isNotNull();
    }

    @Test
    @DisplayName("턴 넘기기를 하면 현재 턴이 상대 팀으로 변경된다.")
    void passTurn() {
        janggiGame.passTurn();
        assertThat(janggiGame.getTurnOwnTeam()).isEqualTo(Team.HAN);

        janggiGame.passTurn();
        assertThat(janggiGame.getTurnOwnTeam()).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("기물을 이동하면 턴이 자동으로 넘어간다.")
    void executeMoveChangesTurn() {
        Position start = Position.of(1, 1);
        Position destination = Position.of(3, 1);

        janggiGame.executeMove(start, destination);
        assertThat(janggiGame.getTurnOwnTeam()).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("상대방의 왕을 잡으면 게임이 종료되고 승자가 결정된다.")
    void gameEndsWhenKingIsCaptured() {
        // given
        janggiGame.executeMove(Position.of(4, 1), Position.of(4, 2));
        janggiGame.passTurn();
        janggiGame.executeMove(Position.of(1, 1), Position.of(7, 1));
        janggiGame.passTurn();
        janggiGame.executeMove(Position.of(7, 1), Position.of(9, 1));
        janggiGame.passTurn();
        janggiGame.executeMove(Position.of(9, 1), Position.of(9, 5));

        // then
        assertThat(janggiGame.isFinished()).isTrue();
        assertThat(janggiGame.getWinner()).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("게임이 종료된 후에는 기물을 이동할 수 없다.")
    void cannotMoveAfterGameFinished() {
        // given
        janggiGame.executeMove(Position.of(4, 1), Position.of(4, 2));
        janggiGame.passTurn();
        janggiGame.executeMove(Position.of(1, 1), Position.of(7, 1));
        janggiGame.passTurn();
        janggiGame.executeMove(Position.of(7, 1), Position.of(9, 1));
        janggiGame.passTurn();
        janggiGame.executeMove(Position.of(9, 1), Position.of(9, 5));

        // when & then
        assertThatThrownBy(() -> janggiGame.executeMove(Position.of(1, 9), Position.of(2, 9)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(JanggiGameErrorMessage.ALREADY_END.getMessage());
    }

    @Test
    @DisplayName("게임 턴을 넘기면 게임 내부의 Context가 바뀐다")
    void passTurn_success_with_NewContext() {
        //given
        GameContext prevContext = janggiGame.getContext();

        //when
        janggiGame.passTurn();
        GameContext newContext = janggiGame.getContext();

        //then
        assertThat(prevContext.hashCode() != newContext.hashCode()).isTrue();
    }

    @Test
    @DisplayName("게임을 강제 종료 하면 게임 내부의 Context가 새로운 것으로 교체된다")
    void forceQuit_success_with_NewContext() {
        //given
        GameContext prevContext = janggiGame.getContext();

        //when
        janggiGame.forceQuit();
        GameContext newContext = janggiGame.getContext();

        //then
        assertThat(prevContext.hashCode() != newContext.hashCode()).isTrue();
    }
}
