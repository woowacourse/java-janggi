package janggi.gimul;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.linearMove.Cha;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import janggi.model.position.Row;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChaTest {

    @DisplayName("같은 행이나 열에 위치해있지 않으면 예외가 발생한다.")
    @Test
    void getLegalPath_invalidPath() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.TWO, Column.FIVE);
        Cha cha = new Cha(Team.CHO);

        //when & then
        assertThatThrownBy(() -> cha.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로는 이동할 수 없습니다.");
    }

    @DisplayName("from과 to가 같으면 예외가 발생한다.")
    @Test
    void getLegalPath_not_move() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.SIX, Column.THREE);
        Cha cha = new Cha(Team.CHO);

        //when & then
        assertThatThrownBy(() -> cha.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로는 이동할 수 없습니다.");
    }

    @DisplayName("현재 기물이 궁성 영역에 있을때, 간선 경로를 가져 올 수 있다.")
    @Test
    void getLegalPath_inPalace_cho() {
        //given
        Position from = new Position(Row.EIGHT, Column.SIX);
        Position middle = new Position(Row.NINE, Column.FIVE);
        Position to = new Position(Row.HAN_BACK, Column.FOUR);
        Cha cha = new Cha(Team.HAN);

        //when & then
        PositionPath positionPath = cha.getLegalPath(from, to);
        assertThat(positionPath.getDestination()).isEqualTo(middle);
    }

    @DisplayName("현재 기물이 궁성 영역에 있을때, 간선 경로를 가져 올 수 있다.")
    @Test
    void getLegalPath_inPalace_han() {
        //given
        Position from = new Position(Row.THREE, Column.SIX);
        Position middle = new Position(Row.TWO, Column.FIVE);
        Position to = new Position(Row.ONE, Column.FOUR);
        Cha cha = new Cha(Team.CHO);

        //when & then
        PositionPath positionPath = cha.getLegalPath(from, to);
        assertThat(positionPath.getDestination()).isEqualTo(middle);
    }

    @DisplayName("궁성 대각선 선 위에 있지 않으면 대각선으로 이동할 수 없다.")
    @Test
    void getLegalPath_palace_diagonal_invalid() {
        Position from = new Position(Row.NINE, Column.FOUR);
        Position to = new Position(Row.EIGHT, Column.FIVE);
        Cha cha = new Cha(Team.CHO);

        assertThatThrownBy(() -> cha.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로는 이동할 수 없습니다.");
    }

    @DisplayName("같은 행이면 이동할 수 있다.")
    @Test
    void getLegalPath_sameRow() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.SIX, Column.FIVE);
        Cha cha = new Cha(Team.CHO);

        //when
        PositionPath positionPath = cha.getLegalPath(from, to);

        //then
        assertThat(positionPath.getDestination())
                .isEqualTo(new Position(Row.SIX, Column.FOUR));
    }

    @DisplayName("같은 열이면 이동할 수 있다.")
    @Test
    void getLegalPath_sameColumn() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.NINE, Column.THREE);
        Cha cha = new Cha(Team.CHO);

        //when
        PositionPath positionPath = cha.getLegalPath(from, to);

        //then
        assertThat(positionPath.getDestination())
                .isEqualTo(new Position(Row.EIGHT, Column.THREE));
    }

    @DisplayName("경로 상에 기물이 없으면 true를 반환한다.")
    @Test
    void canPassThrough_empty_path() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of();
        Cha cha = new Cha(Team.CHO);

        //when & then
        assertThat(cha.canPassThrough(gimulsOnPath, Optional.empty()))
                .isTrue();
    }

    @DisplayName("경로 상에 기물이 없고 목적지에 상대 기물이 있으면 true를 반환한다.")
    @Test
    void canPassThrough_enemy_at_destination() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of();
        Cha gimulAtTo = new Cha(Team.HAN);
        Cha cha = new Cha(Team.CHO);

        //when & then
        assertThat(cha.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
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
        Cha cha = new Cha(Team.CHO);

        //when & then
        assertThat(cha.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
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
        Cha cha = new Cha(Team.CHO);

        //when & then
        assertThat(cha.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
                .isFalse();
    }

    @DisplayName("차의 점수는 13점이다.")
    @Test
    void getScore() {
        Cha cha = new Cha(Team.CHO);
        assertThat(cha.getScore()).isEqualTo(new Score(13));
    }

    @DisplayName("차는 넘어갈 수 있다.")
    @Test
    void canBeJumpedOver() {
        Cha cha = new Cha(Team.CHO);
        assertThat(cha.canBeJumpedOver()).isTrue();
    }

    @DisplayName("차는 잡아야할 왕이 아니다.")
    @Test
    void isKing() {
        Cha cha = new Cha(Team.CHO);
        assertThat(cha.isKing()).isFalse();
    }
}
