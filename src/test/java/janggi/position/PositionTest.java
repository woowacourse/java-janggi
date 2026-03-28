package janggi.position;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import janggi.model.position.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {

    @DisplayName("같은 행이면 true를 반환한다.")
    @Test
    void isSameRow() {
        //given
        Position position = new Position(Row.SIX, Column.THREE);
        Position other = new Position(Row.SIX, Column.FIVE);

        //when & then
        assertThat(position.isSameRow(other))
                .isTrue();
    }

    @DisplayName("같은 열이면 true를 반환한다.")
    @Test
    void isSameColumn() {
        //given
        Position position = new Position(Row.SIX, Column.THREE);
        Position other = new Position(Row.FIVE, Column.THREE);

        //when & then
        assertThat(position.isSameColumn(other))
                .isTrue();
    }

    @DisplayName("입력받은 거리만큼 수평 이동하는 경로를 반환한다.")
    @Test
    void moveHorizontal() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);

        //when & then
        PositionPath positionPath = from.moveHorizontal(3);
        assertThat(positionPath.getDestination()).isEqualTo(new Position(Row.SIX, Column.SIX));
    }

    @DisplayName("다른 위치가 들어올때 수직 이동 경로를 반환한다.")
    @Test
    void moveVertical() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);

        //when & then
        PositionPath positionPath = from.moveVertical(3);
        assertThat(positionPath.getDestination()).isEqualTo(new Position(Row.NINE, Column.THREE));
    }
}
