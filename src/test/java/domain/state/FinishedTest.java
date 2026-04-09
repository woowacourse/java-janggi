package domain.state;

import domain.Board;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class FinishedTest {
    private static final long ID = 0L;

    @Test
    void Finished_상태에서_움직이면_예외가_발생해야_한다() {
        JanggiGame game = new Finished(Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        Assertions.assertThatThrownBy(() -> game.move(Position.of(1, 1), Position.of(2, 2))).isInstanceOf(
                IllegalStateException.class);
    }

    @Test
    void Finished_상태에서_패스하면_예외가_발생해야_한다() {
        JanggiGame game = new Finished(Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        Assertions.assertThatThrownBy(() -> game.pass()).isInstanceOf(
                IllegalStateException.class);
    }

    @Test
    void Finished_상태에서_승자를_반환받을_수_있다() {
        JanggiGame game = new Finished(Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        Assertions.assertThatNoException().isThrownBy(() -> game.judgeWinner());

    }

    @Test
    void Finished_상태는_끝난_상태여야한다() {
        JanggiGame game = new Finished(Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        Assertions.assertThat(game.isFinished()).isTrue();
    }

}