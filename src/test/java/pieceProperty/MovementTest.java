package pieceProperty;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovementTest {

    @Test
    @DisplayName("위 움직임 계산 테스트")
    void upMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.upMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(4, 5));
    }

    @Test
    @DisplayName("아래 움직임 계산 테스트")
    void downMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.downMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(6, 5));
    }

    @Test
    @DisplayName("왼쪽 움직임 계산 테스트")
    void leftMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.leftMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(5, 4));
    }

    @Test
    @DisplayName("오른쪽 움직임 계산 테스트")
    void rightMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.rightMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(5, 6));
    }

    @Test
    @DisplayName("오른쪽 위 대각선 움직임 계산 테스트")
    void upRightMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.rightUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(4, 6));
    }

    @Test
    @DisplayName("왼쪽 위 대각선 움직임 계산 테스트")
    void upLeftMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.leftUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(4, 4));
    }

    @Test
    @DisplayName("오른쪽 아래 대각선 움직임 계산 테스트")
    void downRightMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.rightDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(6, 6));
    }

    @Test
    @DisplayName("왼쪽 아래 대각선 움직임 계산 테스트")
    void downLeftMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.leftDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(6, 4));
    }

    @Test
    @DisplayName("위_오른쪽 대각선 움직임 계산 테스트")
    void upRightUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.upRightUPMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(3, 6));
    }

    @Test
    @DisplayName("위_왼쪽 위 대각선 움직임 계산 테스트")
    void upLeftUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.upLeftUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(3, 4));
    }

    @Test
    @DisplayName("오른쪽_오른쪽 위 대각선 움직임 계산 테스트")
    void rightRightUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.rightRightUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(4, 7));
    }

    @Test
    @DisplayName("오른쪽_오른쪽 아래 대각선 움직임 계산 테스트")
    void rightRightDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.rightRightDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(6, 7));
    }

    @Test
    @DisplayName("아래_오른쪽 아래 대각선 움직임 계산 테스트")
    void downRightDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.downRightDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(7, 6));
    }

    @Test
    @DisplayName("아래_왼쪽 아래 대각선 움직임 계산 테스트")
    void downLeftDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.downLeftDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(7, 4));
    }

    @Test
    @DisplayName("왼쪽_왼쪽 위 대각선 움직임 계산 테스트")
    void leftLeftUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.leftLeftUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(4, 3));
    }

    @Test
    @DisplayName("왼쪽_왼쪽 아래 대각선 움직임 계산 테스트")
    void leftLeftDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.leftLeftDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(6, 3));
    }

    @Test
    @DisplayName("위_오른쪽 대각선_오른쪽 대각선 움직인 계산 테스트")
    void upRightUpRightUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.upRightUpRightUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(2, 7));
    }

    @Test
    @DisplayName("위_왼쪽 대각선_왼쪽 대각선 움직인 계산 테스트")
    void upLeftUpLeftUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.upLeftUpLeftUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(2, 3));
    }

    @Test
    @DisplayName("오른쪽_오른쪽 대각선_오른쪽 대각선 움직임 계산 테스트")
    void rightRightUpRightUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.rightRightUpRightUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(3, 8));
    }

    @Test
    @DisplayName("오른쪽_오른쪽 아래 대각선_오른쪽 아래 대각선 움직임 계산 테스트")
    void rightRightDownRightDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.rightRightDownRightDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(7, 8));
    }

    @Test
    @DisplayName("아래_오른쪽 아래 대각선_오른쪽 아래 대각선 움직임 계산 테스트")
    void downRightDownRightDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.downRightDownRightDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(8, 7));
    }

    @Test
    @DisplayName("아래_왼쪽 아래 대각선_왼쪽 아래 대각선 움직임 계산 테스트")
    void downLeftDownLeftDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.downLeftDownLeftDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(8, 3));
    }

    @Test
    @DisplayName("왼쪽_왼쪽 위 대각선_왼쪽 위 대각선 움직임 계산 테스트")
    void leftLeftUpLeftUPTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.leftLeftUpLeftUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(3, 2));
    }

    @Test
    @DisplayName("왼쪽_왼쪽 아래 대각선_왼쪽 아래 대각선 움직임 계산 테스트")
    void leftLeftDownLeftDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.leftLeftDownLeftDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(7, 2));
    }

}
