package janggi.position;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.model.Team;
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

    @DisplayName("현재 위치가 궁성영역 안이라면 True이다.")
    @Test
    void isInPalace_true() {
        //given
        Position palacePosition = new Position(Row.EIGHT, Column.FOUR);

        //when & then
        assertThat(palacePosition.isInPalace()).isTrue();
    }

    @DisplayName("현재 위치가 궁성영역 밖이라면 False이다.")
    @Test
    void isInPalace_false() {
        //given
        Position palacePosition = new Position(Row.EIGHT, Column.ONE);

        //when & thenT
        assertThat(palacePosition.isInPalace()).isFalse();
    }

    @DisplayName("초나라 궁성 안에 있으면 true를 반환한다.")
    @Test
    void isInPalaceOf_cho() {
        Position position = new Position(Row.NINE, Column.FIVE);
        assertThat(position.isInPalaceOf(Team.CHO)).isTrue();
    }

    @DisplayName("한나라 궁성 안에 있으면 false를 반환한다.")
    @Test
    void isInPalaceOf_cho_false() {
        Position position = new Position(Row.TWO, Column.FIVE);
        assertThat(position.isInPalaceOf(Team.CHO)).isFalse();
    }

    @DisplayName("한나라 궁성 안에 있으면 true를 반환한다.")
    @Test
    void isInPalaceOf_han() {
        Position position = new Position(Row.TWO, Column.FIVE);
        assertThat(position.isInPalaceOf(Team.HAN)).isTrue();
    }

    @DisplayName("궁성 대각선 위에 있으면 true를 반환한다.")
    @Test
    void isOnPalaceDiagonal_true() {
        Position position = new Position(Row.NINE, Column.FIVE);
        assertThat(position.isOnPalaceDiagonal()).isTrue();
    }

    @DisplayName("궁성 대각선 위에 있지 않으면 false를 반환한다.")
    @Test
    void isOnPalaceDiagonal_false() {
        Position position = new Position(Row.NINE, Column.FOUR);
        assertThat(position.isOnPalaceDiagonal()).isFalse();
    }
}
