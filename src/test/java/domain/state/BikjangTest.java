package domain.state;

import domain.Board;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BikjangTest {
    private static final long ID = 0L;

    @Test
    void 움직이고_빅장이_아니면_Normal_상태가_되어야_한다() {
        Board board = Board.of(SettingType.INNER, SettingType.LEFT);
        board.move(Team.CHO, Position.of(2, 5), Position.of(2, 6));
        board.move(Team.HAN, Position.of(9, 5), Position.of(9, 6));

        JanggiGame game = new Bikjang(board, Team.HAN);

        Assertions.assertThat(game.move(Position.of(9, 6), Position.of(9, 5))).isInstanceOf(Playing.class);
    }

    @Test
    void 패스하면_상태가_Finished_상태가_되어야_한다() {
        JanggiGame game = new Bikjang(Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        Assertions.assertThat(game.pass()).isInstanceOf(Finished.class);
    }

    @Test
    void move_이후_빅장이_되면_Finished_상태가_되어야_한다() {
        Board board = Board.of(SettingType.INNER, SettingType.LEFT);
        board.move(Team.CHO, Position.of(2, 5), Position.of(2, 6));
        board.move(Team.HAN, Position.of(9, 5), Position.of(9, 6));

        JanggiGame game = new Bikjang(board, Team.CHO);
        game = game.move(Position.of(1, 1), Position.of(2, 1));

        Assertions.assertThat(game).isInstanceOf(Finished.class);
    }

    @Test
    void 승자를_판단하면_예외가_발생해야_한다() {
        JanggiGame game = new Playing(Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        Assertions.assertThatThrownBy(() -> game.judgeWinner()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 끝난_상태가_아니어야_한다() {
        JanggiGame game = new Playing(Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        Assertions.assertThat(game.isFinished()).isFalse();
    }

    @Test
    void move가_끝나면_턴이_변경되어야_한다() {
        JanggiGame game = new Playing(Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        game = game.move(Position.of(1, 1), Position.of(2, 1));
        Assertions.assertThat(game.getTurn()).isEqualTo(Team.HAN);
    }

    @Test
    void 턴을_쉬면_턴이_변경되어야_한다() {
        JanggiGame game = new Playing(Board.of(SettingType.LEFT, SettingType.LEFT), Team.CHO);
        game = game.pass();
        Assertions.assertThat(game.getTurn()).isEqualTo(Team.HAN);
    }


}
