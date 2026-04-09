package janggi.gimul;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.byeong.Byeong;
import janggi.model.gimul.linearMove.Cha;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import janggi.model.position.Row;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ByeongTest {

    @DisplayName("초나라일때, 북쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveNorth() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SIX, Column.FIVE);
        Byeong byeong = new Byeong(Team.CHO);

        //when
        PositionPath positionPath = byeong.getLegalPath(from, to);

        //then
        assertThat(positionPath.stream().count())
                .isEqualTo(0);
    }

    @DisplayName("한나라일때, 남쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveSouth() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.EIGHT, Column.FIVE);
        Byeong byeong = new Byeong(Team.HAN);

        //when
        PositionPath positionPath = byeong.getLegalPath(from, to);

        //then
        assertThat(positionPath.stream().count())
                .isEqualTo(0);
    }

    @DisplayName("동쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveEast() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SEVEN, Column.SIX);
        Byeong byeong = new Byeong(Team.CHO);

        //when
        PositionPath positionPath = byeong.getLegalPath(from, to);

        //then
        assertThat(positionPath.stream().count())
                .isEqualTo(0);
    }

    @DisplayName("서쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveWest() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SEVEN, Column.FOUR);
        Byeong byeong = new Byeong(Team.CHO);

        //when
        PositionPath positionPath = byeong.getLegalPath(from, to);

        //then
        assertThat(positionPath.stream().count())
                .isEqualTo(0);
    }

    @DisplayName("현재 기물이 궁성 영역에 있을때, 간선 경로를 가져 올 수 있다.")
    @Test
    void getLegalPath_inPalace_cho() {
        //given
        Position from = new Position(Row.EIGHT, Column.SIX);
        Position to = new Position(Row.NINE, Column.FIVE);
        Byeong byeong = new Byeong(Team.HAN);

        //when & then
        PositionPath positionPath = byeong.getLegalPath(from, to);
        assertThat(positionPath.stream().count())
                .isEqualTo(0);
    }

    @DisplayName("현재 기물이 궁성 영역에 있을때, 간선 경로를 가져 올 수 있다.")
    @Test
    void getLegalPath_inPalace_han() {
        Position from = new Position(Row.THREE, Column.FOUR);
        Position to = new Position(Row.TWO, Column.FIVE);
        Byeong byeong = new Byeong(Team.CHO);

        PositionPath positionPath = byeong.getLegalPath(from, to);
        assertThat(positionPath.stream().count()).isEqualTo(0);
    }

    @DisplayName("궁성 대각선 선 위에 있지 않으면 대각선으로 이동할 수 없다.")
    @Test
    void getLegalPath_palace_not_diagonal() {
        Position from = new Position(Row.NINE, Column.FOUR);
        Position to = new Position(Row.EIGHT, Column.FIVE);
        Byeong byeong = new Byeong(Team.HAN);

        assertThatThrownBy(() -> byeong.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @DisplayName("대각선으로 이동하면 예외가 발생한다.")
    @Test
    void getLegalPath_diagonal() {
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SIX, Column.SIX);
        Byeong byeong = new Byeong(Team.CHO);

        assertThatThrownBy(() -> byeong.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @DisplayName("두 칸 이동하면 예외가 발생한다.")
    @Test
    void getLegalPath_twoStep() {
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.FIVE, Column.FIVE);
        Byeong byeong = new Byeong(Team.CHO);

        assertThatThrownBy(() -> byeong.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @DisplayName("초나라일때 남쪽으로 움직이면 예외가 발생한다.")
    @Test
    void getLegalPath_invalid_cho() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.EIGHT, Column.FIVE);
        Byeong byeong = new Byeong(Team.CHO);

        //when & then
        assertThatThrownBy(() -> byeong.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @DisplayName("한나라일때 북쪽으로 움직이면 예외가 발생한다.")
    @Test
    void getLegalPath_invalid_han() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SIX, Column.FIVE);
        Byeong byeong = new Byeong(Team.HAN);

        //when & then
        assertThatThrownBy(() -> byeong.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @DisplayName("경로 상에 기물이 없으면 true를 반환한다.")
    @Test
    void canPassThrough_empty_path() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of();
        Byeong byeong = new Byeong(Team.CHO);

        //when & then
        assertThat(byeong.canPassThrough(gimulsOnPath, Optional.empty()))
                .isTrue();
    }

    @DisplayName("경로 상에 기물이 없고 목적지에 상대 기물이 있으면 true를 반환한다.")
    @Test
    void canPassThrough_enemy_at_destination() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of();
        Cha gimulAtTo = new Cha(Team.HAN);
        Byeong byeong = new Byeong(Team.CHO);

        //when & then
        assertThat(byeong.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
                .isTrue();
    }

    @DisplayName("경로 상에 다른 기물이 존재하면 false를 반환한다.")
    @Test
    void canPassThrough_false() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of(
                new Cha(Team.CHO)
        );
        Cha gimulAtTo = new Cha(Team.HAN);
        Byeong byeong = new Byeong(Team.CHO);

        //when & then
        assertThat(byeong.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
                .isFalse();
    }

    @DisplayName("to에 있는 기물이 같은 팀이면 false를 반환한다.")
    @Test
    void canPassThrough_sameTeam() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of(
                new Cha(Team.CHO)
        );
        Cha gimulAtTo = new Cha(Team.CHO);
        Byeong byeong = new Byeong(Team.CHO);

        //when & then
        assertThat(byeong.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
                .isFalse();
    }

    @DisplayName("병의 점수는 13점이다.")
    @Test
    void getScore() {
        Byeong byeong = new Byeong(Team.CHO);
        assertThat(byeong.getScore()).isEqualTo(new Score(2));
    }

    @DisplayName("병은 넘어갈 수 있다.")
    @Test
    void canBeJumpedOver() {
        Byeong byeong = new Byeong(Team.CHO);
        assertThat(byeong.canBeJumpedOver()).isTrue();
    }

    @DisplayName("병은 잡아야할 왕이 아니다.")
    @Test
    void isKing() {
        Byeong byeong = new Byeong(Team.CHO);
        assertThat(byeong.isKing()).isFalse();
    }
}
