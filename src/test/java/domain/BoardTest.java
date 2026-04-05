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
    public static final double CHO_BASE_SCORE = 72.0;
    public static final double HAN_BASE_SCORE = 73.5;
    public static final int BYEONG_SCORE = 2;


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

    @Test
    void 왕이_하나만_남은_경우_살아남은_왕의_팀이_반환되어야_한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        board.move(Team.CHO, Position.of(1, 2), Position.of(3, 3));
        board.move(Team.CHO, Position.of(3, 2), Position.of(3, 5));
        board.move(Team.HAN, Position.of(7, 5), Position.of(7, 6));
        board.move(Team.CHO, Position.of(3, 5), Position.of(9, 5));

        Assertions.assertThat(board.judgeResult()).isEqualTo(Team.CHO);
    }

    @Test
    void 왕이_둘_다_살아있는_경우_점수로_승패를_판단한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        Team team = board.judgeResult();
        Assertions.assertThat(team).isEqualTo(Team.HAN);
    }

    @Test
    void 왕이_하나만_살아남은_경우_참을_반환해야_한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        board.move(Team.CHO, Position.of(1, 2), Position.of(3, 3));
        board.move(Team.CHO, Position.of(3, 2), Position.of(3, 5));
        board.move(Team.HAN, Position.of(7, 5), Position.of(7, 6));
        board.move(Team.CHO, Position.of(3, 5), Position.of(9, 5));

        Assertions.assertThat(board.isAnyJangDead()).isTrue();
    }

    @Test
    void 왕이_둘_다_살아남은_경우_거짓을_반환해야_한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        Assertions.assertThat(board.isAnyJangDead()).isFalse();
    }


    @Test
    void 서로_아무_기물도_잡히지_않았다면_기본_점수여야_한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        Assertions.assertThat(board.getScoreByTeam(Team.CHO)).isEqualTo(CHO_BASE_SCORE);
        Assertions.assertThat(board.getScoreByTeam(Team.HAN)).isEqualTo(HAN_BASE_SCORE);
    }


    @Test
    void 특정_기물이_잡혔다면_그_점수만큼_차감되어야_한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        board.move(Team.CHO, Position.of(1, 2), Position.of(3, 3));
        board.move(Team.CHO, Position.of(3, 2), Position.of(3, 5));
        board.move(Team.CHO, Position.of(3, 5), Position.of(7, 5));
        Assertions.assertThat(board.getScoreByTeam(Team.HAN)).isEqualTo(HAN_BASE_SCORE - BYEONG_SCORE);
    }

    @Test
    void 장_사이에_아무런_기물이_없으면_빅장으로_처리한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        board.move(Team.CHO, Position.of(4, 5), Position.of(4, 6));
        board.move(Team.HAN, Position.of(7, 5), Position.of(7, 6));
        Assertions.assertThat(board.isBikjang()).isTrue();
    }

    @Test
    void 장이_같은_열에_없으면_빅장으로_처리하지_않는다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        board.move(Team.CHO, Position.of(2, 5), Position.of(2, 6));
        Assertions.assertThat(board.isBikjang()).isFalse();
    }

    @Test
    void 장_사이에_기물이_있으면_빅장으로_처리하지_않는다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        Assertions.assertThat(board.isBikjang()).isFalse();
    }
}

