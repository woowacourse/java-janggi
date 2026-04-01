package domain;

import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {
    private static final Position ALLY_CHA = Position.of(1, 1);
    private static final Position ALLY_JOL = Position.of(4, 1);
    private static final Position ENEMY_JOL = Position.of(7, 1);
    private static final Position EMPTY_SPACE = Position.of(5, 1);


    @Test
    void 적군을_움직이는_경우_예외가_발생해야_한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);

        Assertions.assertThatThrownBy(() -> board.move(Team.CHO, ENEMY_JOL, Position.of(5, 1))).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 빈_공간을_이동시키는_경우_예외가_발생해야_한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);

        Assertions.assertThatThrownBy(() -> board.move(Team.HAN, EMPTY_SPACE, Position.of(5, 1))).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 이동_위치에_아군이_있는_경우_예외가_발생해야_한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);

        Assertions.assertThatThrownBy(() -> board.move(Team.CHO, ALLY_CHA, ALLY_JOL)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 이동된_경우_정상적으로_적용되어야_한다() {
        Position destination = Position.of(5, 1);
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        board.move(Team.CHO, ALLY_JOL, destination);

        Assertions.assertThatNoException().isThrownBy(() -> board.getPieceOrThrowException(destination));
    }
}
