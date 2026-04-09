package janggi.gimul;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.diagonalMove.Ma;
import janggi.model.gimul.linearMove.Cha;
import janggi.model.gimul.linearMove.Pho;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import janggi.model.position.Row;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PhoTest {

    @DisplayName("같은 행이나 열에 위치해있지 않으면 예외가 발생한다.")
    @Test
    void getLegalPath_invalidPath() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.TWO, Column.FIVE);
        Pho pho = new Pho(Team.CHO);

        //when & then
        assertThatThrownBy(() -> pho.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로는 이동할 수 없습니다.");
    }

    @DisplayName("from과 to가 같으면 예외가 발생한다.")
    @Test
    void getLegalPath_not_move() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.SIX, Column.THREE);
        Pho pho = new Pho(Team.CHO);

        //when & then
        assertThatThrownBy(() -> pho.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로는 이동할 수 없습니다.");
    }

    @DisplayName("같은 행이면 이동할 수 있다.")
    @Test
    void getLegalPath_sameRow() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.SIX, Column.FIVE);
        Pho pho = new Pho(Team.CHO);

        //when
        PositionPath positionPath = pho.getLegalPath(from, to);

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
        Pho pho = new Pho(Team.CHO);

        //when
        PositionPath positionPath = pho.getLegalPath(from, to);

        //then
        assertThat(positionPath.getDestination())
                .isEqualTo(new Position(Row.EIGHT, Column.THREE));
    }

    @DisplayName("현재 기물이 궁성 영역에 있을때, 간선 경로를 가져 올 수 있다.")
    @Test
    void getLegalPath_inPalace_cho() {
        //given
        Position from = new Position(Row.EIGHT, Column.SIX);
        Position middle = new Position(Row.NINE, Column.FIVE);
        Position to = new Position(Row.HAN_BACK, Column.FOUR);
        Pho pho = new Pho(Team.HAN);

        //when & then
        PositionPath positionPath = pho.getLegalPath(from, to);
        assertThat(positionPath.getDestination()).isEqualTo(middle);
    }

    @DisplayName("현재 기물이 궁성 영역에 있을때, 간선 경로를 가져 올 수 있다.")
    @Test
    void getLegalPath_inPalace_han() {
        //given
        Position from = new Position(Row.THREE, Column.SIX);
        Position middle = new Position(Row.TWO, Column.FIVE);
        Position to = new Position(Row.ONE, Column.FOUR);
        Pho pho = new Pho(Team.CHO);

        //when & then
        PositionPath positionPath = pho.getLegalPath(from, to);
        assertThat(positionPath.getDestination()).isEqualTo(middle);
    }

    @DisplayName("궁성 대각선 선 위에 있지 않으면 대각선으로 이동할 수 없다.")
    @Test
    void getLegalPath_palace_diagonal_invalid() {
        Position from = new Position(Row.NINE, Column.FOUR);
        Position to = new Position(Row.EIGHT, Column.FIVE);
        Pho pho = new Pho(Team.CHO);

        assertThatThrownBy(() -> pho.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로는 이동할 수 없습니다.");
    }

    @DisplayName("경로 상에 포가 아닌 기물이 1개 있다면 true를 반환한다.")
    @Test
    void canPassThrough_true() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of(
                new Ma(Team.CHO)
        );
        Pho pho = new Pho(Team.CHO);

        //when & then
        assertThat(pho.canPassThrough(gimulsOnPath, Optional.empty()))
                .isTrue();
    }

    @DisplayName("경로 상에 포가 아닌 기물이 1개 있고, 목적지에 상대 기물이 있으면 true를 반환한다.")
    @Test
    void canPassThrough_enemy_at_destination() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of(
                new Ma(Team.CHO)
        );
        Pho pho = new Pho(Team.CHO);
        Cha gimulAtTo = new Cha(Team.HAN);

        //when & then
        assertThat(pho.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
                .isTrue();
    }

    @DisplayName("경로에 기물이 없으면 false를 반환한다.")
    @Test
    void canPassThrough_Empty() {
        //given
        Pho pho = new Pho(Team.CHO);

        //when & then
        assertThat(pho.canPassThrough(List.of(), Optional.empty()))
                .isFalse();
    }

    @DisplayName("경로에 포가 있으면 false를 반환한다.")
    @Test
    void canPassThrough_pho() {
        //given
        Pho pho = new Pho(Team.CHO);

        //when & then
        assertThat(pho.canPassThrough(List.of(new Pho(Team.CHO)), Optional.empty()))
                .isFalse();
    }

    @DisplayName("to에 있는 기물이 같은 팀이면 false를 반환한다.")
    @Test
    void canPassThrough_sameTeam() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of(
                new Cha(Team.CHO)
        );
        Pho gimulAtTo = new Pho(Team.CHO);
        Pho pho = new Pho(Team.CHO);

        //when & then
        assertThat(pho.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
                .isFalse();
    }

    @DisplayName("포가 포를 잡으려 하면 false를 반환한다.")
    @Test
    void canPassThrough_phoAtDestination() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of(new Cha(Team.CHO));
        Pho gimulAtTo = new Pho(Team.HAN);
        Pho pho = new Pho(Team.CHO);

        //when & then
        assertThat(pho.canPassThrough(gimulsOnPath, Optional.of(gimulAtTo)))
                .isFalse();
    }

    @DisplayName("경로에 기물이 하나 있고 목적지가 비어있으면 true를 반환한다.")
    @Test
    void canPassThrough_oneGimulOnPath() {
        //given
        List<AbstractGimul> gimulsOnPath = List.of(new Cha(Team.CHO));
        Pho pho = new Pho(Team.CHO);

        //when & then
        assertThat(pho.canPassThrough(gimulsOnPath, Optional.empty()))
                .isTrue();
    }

    @DisplayName("포의 점수는 7점이다.")
    @Test
    void getScore() {
        Pho pho = new Pho(Team.CHO);
        assertThat(pho.getScore()).isEqualTo(new Score(7));
    }

    @DisplayName("포는 넘어갈 수 없다.")
    @Test
    void canBeJumpedOver() {
        Pho pho = new Pho(Team.CHO);
        assertThat(pho.canBeJumpedOver()).isFalse();
    }

    @DisplayName("포는 잡아야할 왕이 아니다.")
    @Test
    void isKing() {
        Pho pho = new Pho(Team.CHO);
        assertThat(pho.isKing()).isFalse();
    }
}
