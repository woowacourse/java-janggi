package janggi.gimul;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.Column;
import janggi.Path;
import janggi.Position;
import janggi.Row;
import janggi.Team;
import java.util.List;
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

    @DisplayName("같은 행이면 이동할 수 있다.")
    @Test
    void getLegalPath_sameRow() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.SIX, Column.FIVE);
        Cha cha = new Cha(Team.CHO);

        //when
        Path path = cha.getLegalPath(from, to);

        //then
        assertThat(path.getDestination())
                .isEqualTo(new Position(Row.SIX, Column.FIVE));
    }

    @DisplayName("같은 열이면 이동할 수 있다.")
    @Test
    void getLegalPath_sameColumn() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.NINE, Column.THREE);
        Cha cha = new Cha(Team.CHO);

        //when
        Path path = cha.getLegalPath(from, to);

        //then
        assertThat(path.getDestination())
                .isEqualTo(new Position(Row.NINE, Column.THREE));
    }

    @DisplayName("경로 상에 다른 기물이 존재하면 false를 반환한다.")
    @Test
    void canPassThrough() {
        //given
        List<Gimul> gimuls = List.of(
                new Cha(Team.CHO),
                new Cha(Team.HAN)
        );
        Cha cha = new Cha(Team.CHO);

        //when & then
        assertThat(cha.canPassThrough(gimuls))
                .isFalse();
    }
}