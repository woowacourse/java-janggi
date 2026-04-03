package domain.state;

import domain.Board;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RunningTest {
    @Test
    void Running_상태에서_움직일_수_있어야_한다() {
        JanggiGame game = new Running(Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        Assertions.assertThatNoException().isThrownBy(() -> game.move(Position.of(1, 1), Position.of(2, 1)));
    }

    @Test
    void Running_상태에서_패스할_수_있어야_한다() {
        JanggiGame game = new Running(Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        Assertions.assertThatNoException().isThrownBy(() -> game.pass());
    }

    @Test
    void Running_상태에서_승자를_판단하면_예외가_발생해야_한다() {
        JanggiGame game = new Running(Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        Assertions.assertThatThrownBy(() -> game.judgeWinner()).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void Running_상태는_끝난_상태가_아니어야_한다() {
        JanggiGame game = new Running(Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        Assertions.assertThat(game.isFinished()).isFalse();
    }

    @Test
    void move가_끝나면_턴이_변경되어야_한다() {
        JanggiGame game = new Running(Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        game = game.move(Position.of(1, 1), Position.of(2, 1));
        Assertions.assertThat(game.getTurn()).isEqualTo(Team.HAN);
    }


    @Test
    void 턴을_쉬면_턴이_변경되어야_한다() {
        JanggiGame game = new Running(Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        game = game.pass();
        Assertions.assertThat(game.getTurn()).isEqualTo(Team.HAN);
    }
}