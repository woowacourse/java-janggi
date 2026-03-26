package janggi.gimul;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.Team;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.PositionPath;
import janggi.position.Row;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JangTest {
    @DisplayName("이동 거리가 1칸 초과이면 예외가 발생한다.")
    @Test
    void getLegalPath_invalid() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SEVEN, Column.SEVEN);
        Jang jang = new Jang(Team.HAN);

        //when & then
        assertThatThrownBy(() -> jang.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @DisplayName("남쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveSouth() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SIX, Column.FIVE);
        Jang jang = new Jang(Team.CHO);

        //when
        PositionPath positionPath = jang.getLegalPath(from, to);

        //then
        assertThat(positionPath.getDestination())
                .isEqualTo(new Position(Row.SIX, Column.FIVE));
    }

    @DisplayName("북쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveNorth() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.EIGHT, Column.FIVE);
        Jang jang = new Jang(Team.CHO);

        //when
        PositionPath positionPath = jang.getLegalPath(from, to);

        //then
        assertThat(positionPath.getDestination())
                .isEqualTo(new Position(Row.EIGHT, Column.FIVE));
    }


    @DisplayName("동쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveEast() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SEVEN, Column.SIX);
        Jang jang = new Jang(Team.CHO);

        //when
        PositionPath positionPath = jang.getLegalPath(from, to);

        //then
        assertThat(positionPath.getDestination())
                .isEqualTo(new Position(Row.SEVEN, Column.SIX));
    }

    @DisplayName("서쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveWest() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SEVEN, Column.FOUR);
        Jang jang = new Jang(Team.CHO);

        //when
        PositionPath positionPath = jang.getLegalPath(from, to);

        //then
        assertThat(positionPath.getDestination())
                .isEqualTo(new Position(Row.SEVEN, Column.FOUR));
    }


    @DisplayName("경로 상에 다른 기물이 존재하면 false를 반환한다.")
    @Test
    void canPassThrough() {
        //given
        List<Gimul> gimulsOnPath = List.of(
                new Cha(Team.CHO)
        );
        Cha gimulAtTo = new Cha(Team.HAN);
        Jang jang = new Jang(Team.CHO);

        //when & then
        assertThat(jang.canPassThrough(gimulsOnPath, gimulAtTo))
                .isFalse();
    }

    @DisplayName("to에 있는 기물이 같은 팀이면 false를 반환한다.")
    @Test
    void canPassThrough_sameTeam() {
        //given
        List<Gimul> gimulsOnPath = List.of(
                new Cha(Team.CHO)
        );
        Cha gimulAtTo = new Cha(Team.CHO);
        Jang jang = new Jang(Team.CHO);

        //when & then
        assertThat(jang.canPassThrough(gimulsOnPath, gimulAtTo))
                .isFalse();
    }
}