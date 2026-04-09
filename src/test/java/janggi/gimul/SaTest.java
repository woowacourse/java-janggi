package janggi.gimul;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.linearMove.Cha;
import janggi.model.gimul.palace.Sa;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import janggi.model.position.Row;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SaTest {

    @DisplayName("이동 거리가 1칸 초과이면 예외가 발생한다.")
    @Test
    void getLegalPath_invalid() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SEVEN, Column.SEVEN);
        Sa sa = new Sa(Team.HAN);

        //when & then
        assertThatThrownBy(() -> sa.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @DisplayName("경로 상에 기물이 없으면 true를 반환한다.")
    @Test
    void canPassThrough_empty_path() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of();
        Sa sa = new Sa(Team.CHO);

        //when & then
        assertThat(sa.canPassThrough(gimulsOnPath, Optional.empty()))
                .isTrue();
    }

    @DisplayName("경로 상에 기물이 없고 목적지에 상대 기물이 있으면 true를 반환한다.")
    @Test
    void canPassThrough_enemy_at_destination() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of();
        Cha gimulAtTo = new Cha(Team.HAN);
        Sa sa = new Sa(Team.CHO);

        //when & then
        assertThat(sa.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
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
        Sa sa = new Sa(Team.HAN);

        //when & then
        assertThat(sa.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
                .isFalse();
    }

    @DisplayName("to에 있는 기물이 같은 팀이면 false를 반환한다.")
    @Test
    void canPassThrough_sameTeam() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of(
                new Cha(Team.CHO)
        );
        Sa gimulAtTo = new Sa(Team.CHO);
        Sa sa = new Sa(Team.CHO);

        //when & then
        assertThat(sa.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
                .isFalse();
    }

    @DisplayName("궁성 밖으로 이동하면 예외가 발생한다.")
    @Test
    void getLegalPath_outsidePalace() {
        Position from = new Position(Row.EIGHT, Column.FIVE);
        Position to = new Position(Row.SEVEN, Column.FIVE);
        Sa sa = new Sa(Team.CHO);

        assertThatThrownBy(() -> sa.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @DisplayName("궁성 내에서 한칸 이동한다.")
    @Test
    void getLegalPath_insidePalace() {
        Position from = new Position(Row.HAN_BACK, Column.FIVE);
        Position to = new Position(Row.NINE, Column.FIVE);
        Sa sa = new Sa(Team.CHO);

        PositionPath positionPath = sa.getLegalPath(from, to);
        assertThat(positionPath.stream().count()).isEqualTo(0);
    }

    @DisplayName("궁성 내에서 대각선으로 이동한다.")
    @Test
    void getLegalPath_palace_diagonal() {
        Position from = new Position(Row.HAN_BACK, Column.FOUR);
        Position to = new Position(Row.NINE, Column.FIVE);
        Sa sa = new Sa(Team.CHO);

        PositionPath positionPath = sa.getLegalPath(from, to);
        assertThat(positionPath.stream().count()).isEqualTo(0);
    }

    @DisplayName("사의 점수는 3점이다.")
    @Test
    void getScore() {
        Sa sa = new Sa(Team.CHO);
        assertThat(sa.getScore()).isEqualTo(new Score(3));
    }

    @DisplayName("사는 넘어갈 수 있다.")
    @Test
    void canBeJumpedOver() {
        Sa sa = new Sa(Team.CHO);
        assertThat(sa.canBeJumpedOver()).isTrue();
    }

    @DisplayName("사는 잡아야할 왕이 아니다.")
    @Test
    void isKing() {
        Sa sa = new Sa(Team.CHO);
        assertThat(sa.isKing()).isFalse();
    }
}
