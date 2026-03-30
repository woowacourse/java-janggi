package janggi.model.gimul;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Team;
import janggi.model.board.PositionPath;
import janggi.model.gimul.straightMove.Cha;
import janggi.model.gimul.straightMove.Pho;
import janggi.model.board.position.Column;
import janggi.model.board.position.Position;
import janggi.model.board.position.Row;
import java.util.List;
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
                .hasMessage("직선 관계에 위치해 있지 않습니다.");
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
                .hasMessage("직선 관계에 위치해 있지 않습니다.");
    }

    @DisplayName("같은 행이면 이동할 수 있다.")
    @Test
    void getLegalPath_sameRow() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.SIX, Column.FIVE);
        Pho pho = new Pho(Team.CHO);

        //when
        PositionPath path = pho.getLegalPath(from, to);

        //then
        assertThat(path.isEmpty())
                .isFalse();
    }

    @DisplayName("같은 열이면 이동할 수 있다.")
    @Test
    void getLegalPath_sameColumn() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.NINE, Column.THREE);
        Pho pho = new Pho(Team.CHO);

        //when
        PositionPath path = pho.getLegalPath(from, to);

        //then
        assertThat(path.isEmpty())
                .isFalse();
    }

    @DisplayName("아무런 기물이 없으면 예외가 발생한다.")
    @Test
    void canPassThrough_Empty() {
        //given
        Pho pho = new Pho(Team.CHO);

        //when & then
        assertThat(pho.canPassThrough(List.of(), null))
                .isFalse();
    }

    @DisplayName("포함된 기물이 포이면 예외가 발생한다.")
    @Test
    void canPassThrough_pho() {
        //given
        Pho pho = new Pho(Team.CHO);

        //when & then
        assertThat(pho.canPassThrough(List.of(new Pho(Team.CHO)), null))
                .isFalse();
    }

    @DisplayName("to에 있는 기물이 같은 팀이면 false를 반환한다.")
    @Test
    void canPassThrough_sameTeam() {
        //given
        List<Piece> gimulsOnPath = List.of(
                new Cha(Team.CHO)
        );
        Pho gimulAtTo = new Pho(Team.CHO);
        Pho pho = new Pho(Team.CHO);

        //when & then
        assertThat(pho.canPassThrough(gimulsOnPath, gimulAtTo))
                .isFalse();
    }
}