package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Team;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Test
    @DisplayName("장기 게임을 초기화하면 초기 상태의 보드와 CHO 팀의 턴으로 시작한다.")
    void initJanggiGame() {
        JanggiGame janggiGame = JanggiGame.init(SettingType.LEFT, SettingType.LEFT);

        assertThat(janggiGame.getTurn()).isEqualTo(Team.CHO);
        assertThat(janggiGame.getJanggiGameStatus()).isNotNull();
    }

    @Test
    @DisplayName("턴 넘기기를 하면 현재 턴이 상대 팀으로 변경된다.")
    void passTurn() {
        JanggiGame janggiGame = JanggiGame.init(SettingType.LEFT, SettingType.LEFT);

        janggiGame.passTurn();
        assertThat(janggiGame.getTurn()).isEqualTo(Team.HAN);

        janggiGame.passTurn();
        assertThat(janggiGame.getTurn()).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("기물을 이동하면 턴이 자동으로 넘어간다.")
    void executeMoveChangesTurn() {
        JanggiGame janggiGame = JanggiGame.init(SettingType.LEFT, SettingType.LEFT);
        Position start = Position.of(1, 1);
        Position destination = Position.of(3, 1);

        janggiGame.executeMove(start, destination);
        assertThat(janggiGame.getTurn()).isEqualTo(Team.HAN);
    }
}
