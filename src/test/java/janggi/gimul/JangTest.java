package janggi.gimul;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.linearMove.Cha;
import janggi.model.gimul.palace.Jang;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import janggi.model.position.Row;
import java.util.List;
import java.util.Optional;
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

    @DisplayName("경로 상에 기물이 없으면 true를 반환한다.")
    @Test
    void canPassThrough_empty_path() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of();
        Jang jang = new Jang(Team.CHO);

        //when & then
        assertThat(jang.canPassThrough(gimulsOnPath, Optional.empty()))
                .isTrue();
    }

    @DisplayName("경로 상에 기물이 없고 목적지에 상대 기물이 있으면 true를 반환한다.")
    @Test
    void canPassThrough_enemy_at_destination() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of();
        Cha gimulAtTo = new Cha(Team.HAN);
        Jang jang = new Jang(Team.CHO);

        //when & then
        assertThat(jang.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
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
        Jang jang = new Jang(Team.CHO);

        //when & then
        assertThat(jang.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
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
        Jang jang = new Jang(Team.CHO);

        //when & then
        assertThat(jang.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
                .isFalse();
    }

    @DisplayName("궁성 밖으로 이동하면 예외가 발생한다.")
    @Test
    void getLegalPath_outsidePalace() {
        Position from = new Position(Row.EIGHT, Column.FIVE);
        Position to = new Position(Row.SEVEN, Column.FIVE);
        Jang jang = new Jang(Team.CHO);

        assertThatThrownBy(() -> jang.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @DisplayName("궁성 내에서 한칸 이동한다.")
    @Test
    void getLegalPath_insidePalace() {
        Position from = new Position(Row.HAN_BACK, Column.FIVE);
        Position to = new Position(Row.NINE, Column.FIVE);
        Jang jang = new Jang(Team.CHO);

        PositionPath positionPath = jang.getLegalPath(from, to);
        assertThat(positionPath.stream().count()).isEqualTo(0);
    }

    @DisplayName("궁성 내에서 대각선으로 이동한다.")
    @Test
    void getLegalPath_palace_diagonal() {
        Position from = new Position(Row.HAN_BACK, Column.FOUR);
        Position to = new Position(Row.NINE, Column.FIVE);
        Jang jang = new Jang(Team.CHO);

        PositionPath positionPath = jang.getLegalPath(from, to);
        assertThat(positionPath.stream().count()).isEqualTo(0);
    }

    @DisplayName("장군의 점수는 0점이다.")
    @Test
    void getScore() {
        Jang jang = new Jang(Team.CHO);
        assertThat(jang.getScore()).isEqualTo(new Score(0));
    }

    @DisplayName("장군은 넘어갈 수 있다.")
    @Test
    void canBeJumpedOver() {
        Jang jang = new Jang(Team.CHO);
        assertThat(jang.canBeJumpedOver()).isTrue();
    }

    @DisplayName("장군은 잡아야할 왕이다.")
    @Test
    void isKing() {
        Jang jang = new Jang(Team.CHO);
        assertThat(jang.isKing()).isTrue();
    }
}
