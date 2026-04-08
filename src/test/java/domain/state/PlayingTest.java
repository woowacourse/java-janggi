package domain.state;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import domain.Board;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayingTest {
    private static final long ID = 0L;

    @Test
    void 움직이고_상태가_변경되지_않으면_유지되어야_한다() {
        JanggiGame game = new Playing(ID, Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        Assertions.assertThat(game.move(Position.of(1, 1), Position.of(2, 1))).isInstanceOf(Playing.class);
    }

    @Test
    void 패스하면_상태가_유지되어야_한다() {
        JanggiGame game = new Playing(ID, Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        Assertions.assertThat(game.pass()).isInstanceOf(Playing.class);
    }

    @Test
    void 승자를_판단하면_예외가_발생해야_한다() {
        JanggiGame game = new Playing(ID, Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        Assertions.assertThatThrownBy(() -> game.judgeWinner()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 끝난_상태가_아니어야_한다() {
        JanggiGame game = new Playing(ID, Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        Assertions.assertThat(game.isFinished()).isFalse();
    }

    @Test
    void move가_끝나면_턴이_변경되어야_한다() {
        JanggiGame game = new Playing(ID, Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        game = game.move(Position.of(1, 1), Position.of(2, 1));
        Assertions.assertThat(game.getTurn()).isEqualTo(Team.HAN);
    }

    @Test
    void 턴을_쉬면_턴이_변경되어야_한다() {
        JanggiGame game = new Playing(ID, Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        game = game.pass();
        Assertions.assertThat(game.getTurn()).isEqualTo(Team.HAN);
    }

    @Test
    void move_이후_빅장이_되면_빅장_상태가_되어야_한다() {
        Board mockBoard = mock(Board.class);

        when(mockBoard.isBikjang()).thenReturn(true);

        JanggiGame game = new Playing(ID, mockBoard, Team.CHO);
        game = game.move(Position.of(1, 1), Position.of(2, 1));

        Assertions.assertThat(game).isInstanceOf(Bikjang.class);
    }
}
