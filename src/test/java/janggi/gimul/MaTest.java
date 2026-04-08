package janggi.gimul;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.diagonalMove.Ma;
import janggi.model.gimul.linearMove.Cha;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import janggi.model.position.Row;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MaTest {

    @DisplayName("상하 또는 좌우로 한칸을 간 후에 같은 방향의 대각선으로 한칸 이동한다.")
    @Test
    void getLegalPath() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.FIVE, Column.SIX);
        Ma ma = new Ma(Team.CHO);

        //when
        PositionPath positionPath = ma.getLegalPath(from, to);

        //then
        assertThat(positionPath.getDestination())
                .isEqualTo(new Position(Row.SIX, Column.FIVE));
    }

    @DisplayName("좌우로 한칸을 간 후에 같은 방향의 대각선으로 한칸 이동한다.")
    @Test
    void getLegalPath_horizontalDominant() {
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SIX, Column.SEVEN);
        Ma ma = new Ma(Team.CHO);

        PositionPath positionPath = ma.getLegalPath(from, to);

        assertThat(positionPath.getDestination())
                .isEqualTo(new Position(Row.SEVEN, Column.SIX));
    }

    @DisplayName("행과 열의 거리가 각각 (1,2) 혹은 (2,1)이 아니면 예외가 발생한다.")
    @Test
    void getLegalPath_invalid() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.FOUR, Column.SIX);
        Ma ma = new Ma(Team.CHO);

        //when & then
        assertThatThrownBy(() -> ma.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @DisplayName("경로 상에 기물이 없으면 true를 반환한다.")
    @Test
    void canPassThrough_empty_path() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of();
        Ma ma = new Ma(Team.CHO);

        //when & then
        assertThat(ma.canPassThrough(gimulsOnPath, Optional.empty()))
                .isTrue();
    }

    @DisplayName("경로 상에 기물이 없고 목적지에 상대 기물이 있으면 true를 반환한다.")
    @Test
    void canPassThrough_enemy_at_destination() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of();
        Cha gimulAtTo = new Cha(Team.HAN);
        Ma ma = new Ma(Team.CHO);

        //when & then
        assertThat(ma.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
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

        Ma ma = new Ma(Team.CHO);

        //when & then
        assertThat(ma.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
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
        Ma ma = new Ma(Team.CHO);

        //when & then
        assertThat(ma.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
                .isFalse();
    }

    @DisplayName("같은 팀이면 true를 반환한다.")
    @Test
    void isSameTeam() {
        //given
        Ma ma = new Ma(Team.CHO);

        //when & then
        assertThat(ma.isSameTeam(Team.CHO)).isTrue();
        assertThat(ma.isSameTeam(Team.HAN)).isFalse();
    }

    @DisplayName("마의 점수는 5점이다.")
    @Test
    void getScore() {
        Ma ma = new Ma(Team.CHO);
        assertThat(ma.getScore()).isEqualTo(new Score(5));
    }

    @DisplayName("마는 점프할 수 있다.")
    @Test
    void isJumpable() {
        Ma ma = new Ma(Team.CHO);
        assertThat(ma.isJumpable()).isTrue();
    }

    @DisplayName("마는 잡아야할 왕이 아니다.")
    @Test
    void isKing() {
        Ma ma = new Ma(Team.CHO);
        assertThat(ma.isKing()).isFalse();
    }
}
