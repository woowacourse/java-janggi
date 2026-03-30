package janggi.model.board.position;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
                .isEqualTo(true);
    }

    @DisplayName("같은 열이면 true를 반환한다.")
    @Test
    void isSameCollumn() {
        //given
        Position position = new Position(Row.SIX, Column.THREE);
        Position other = new Position(Row.FIVE, Column.THREE);

        //when & then
        assertThat(position.isSameColumn(other))
                .isEqualTo(true);
    }

    @DisplayName("두 지점 사이의 가로/세로 거리 중 크키가 큰 값을 절대값으로 반환한다.")
    @Test
    void getDistanceTo() {
        //given
        Position position = new Position(Row.NINE, Column.FIVE);
        Position other = new Position(Row.SIX, Column.FOUR);

        //when & then
        assertThat(position.getDistanceTo(other))
                .isEqualTo(3);
    }
//
//    @DisplayName("입력받은 거리만큼 수평 이동하는 경로를 반환한다.")
//    @Test
//    void moveHorizontal() {
//        //given
//        Position from = new Position(Row.SIX, Column.THREE);
//
//        //when & then
//        MoveResult moveResult = from.moveHorizontal(3);
//        assertThat(moveResult.getTo()).isEqualTo(new Position(Row.SIX, Column.SIX));
//    }
//
//    @DisplayName("수평 이동 결과로 보드 밖으로 나가면 예외가 발생한다.")
//    @Test
//    void moveHorizontal_out() {
//        //given
//        Position from = new Position(Row.SIX, Column.THREE);
//
//        //when & then
//        assertThatThrownBy(() -> from.moveHorizontal(9))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("보드 밖으로는 이동할 수 없습니다.");
//    }
//
//    @DisplayName("다른 위치가 들어올때 수직 이동 경로를 반환한다.")
//    @Test
//    void moveVertical() {
//        //given
//        Position from = new Position(Row.SIX, Column.THREE);
//
//        //when & then
//        MoveResult moveResult = from.moveVertical(3);
//        assertThat(moveResult.getTo()).isEqualTo(new Position(Row.NINE, Column.THREE));
//    }
//
//    @DisplayName("수직 이동 결과로 보드 밖으로 나가면 예외가 발생한다.")
//    @Test
//    void moveVertical_out() {
//        //given
//        Position from = new Position(Row.SIX, Column.THREE);
//
//        //when & then
//        assertThatThrownBy(() -> from.moveVertical(10))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("보드 밖으로는 이동할 수 없습니다.");
//    }
}