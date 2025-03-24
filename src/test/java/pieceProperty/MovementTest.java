package pieceProperty;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovementTest {

    @Test
    @DisplayName("위 움직임 계산 테스트")
    void calculateUpMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(4, 5));
    }

    @Test
    @DisplayName("아래 움직임 계산 테스트")
    void calculateDownMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(6, 5));
    }

    @Test
    @DisplayName("왼쪽 움직임 계산 테스트")
    void calculateLeftMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateLeftMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(5, 4));
    }

    @Test
    @DisplayName("오른쪽 움직임 계산 테스트")
    void calculateRightMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateRightMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(5, 6));
    }

    @Test
    @DisplayName("오른쪽 위 대각선 움직임 계산 테스트")
    void upCalculateRightMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateRightUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(4, 6));
    }

    @Test
    @DisplayName("왼쪽 위 대각선 움직임 계산 테스트")
    void upCalculateLeftMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateLeftUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(4, 4));
    }

    @Test
    @DisplayName("오른쪽 아래 대각선 움직임 계산 테스트")
    void downCalculateRightMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateRightDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(6, 6));
    }

    @Test
    @DisplayName("왼쪽 아래 대각선 움직임 계산 테스트")
    void downCalculateLeftMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateLeftDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(6, 4));
    }

    @Test
    @DisplayName("위_오른쪽 대각선 움직임 계산 테스트")
    void upRightUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateUpRightUPMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(3, 6));
    }

    @Test
    @DisplayName("위_왼쪽 위 대각선 움직임 계산 테스트")
    void upLeftUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateUpLeftUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(3, 4));
    }

    @Test
    @DisplayName("오른쪽_오른쪽 위 대각선 움직임 계산 테스트")
    void rightRightUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateRightRightUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(4, 7));
    }

    @Test
    @DisplayName("오른쪽_오른쪽 아래 대각선 움직임 계산 테스트")
    void rightRightDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateRightRightDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(6, 7));
    }

    @Test
    @DisplayName("아래_오른쪽 아래 대각선 움직임 계산 테스트")
    void downRightDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateDownRightDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(7, 6));
    }

    @Test
    @DisplayName("아래_왼쪽 아래 대각선 움직임 계산 테스트")
    void downLeftDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateDownLeftDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(7, 4));
    }

    @Test
    @DisplayName("왼쪽_왼쪽 위 대각선 움직임 계산 테스트")
    void leftLeftUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateLeftLeftUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(4, 3));
    }

    @Test
    @DisplayName("왼쪽_왼쪽 아래 대각선 움직임 계산 테스트")
    void leftLeftDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateLeftLeftDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(6, 3));
    }

    @Test
    @DisplayName("위_오른쪽 대각선_오른쪽 대각선 움직인 계산 테스트")
    void upRightUpRightUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateUpRightUpRightUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(2, 7));
    }

    @Test
    @DisplayName("위_왼쪽 대각선_왼쪽 대각선 움직인 계산 테스트")
    void upLeftUpLeftUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateUpLeftUpLeftUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(2, 3));
    }

    @Test
    @DisplayName("오른쪽_오른쪽 대각선_오른쪽 대각선 움직임 계산 테스트")
    void rightRightUpRightUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateRightRightUpRightUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(3, 8));
    }

    @Test
    @DisplayName("오른쪽_오른쪽 아래 대각선_오른쪽 아래 대각선 움직임 계산 테스트")
    void rightRightDownRightDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateRightRightDownRightDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(7, 8));
    }

    @Test
    @DisplayName("아래_오른쪽 아래 대각선_오른쪽 아래 대각선 움직임 계산 테스트")
    void downRightDownRightDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateDownRightDownRightDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(8, 7));
    }

    @Test
    @DisplayName("아래_왼쪽 아래 대각선_왼쪽 아래 대각선 움직임 계산 테스트")
    void downLeftDownLeftDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateDownLeftDownLeftDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(8, 3));
    }

    @Test
    @DisplayName("왼쪽_왼쪽 위 대각선_왼쪽 위 대각선 움직임 계산 테스트")
    void leftLeftUpLeftUPTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateLeftLeftUpLeftUpMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(3, 2));
    }

    @Test
    @DisplayName("왼쪽_왼쪽 아래 대각선_왼쪽 아래 대각선 움직임 계산 테스트")
    void leftLeftDownLeftDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = Movement.calculateLeftLeftDownLeftDownMovement(present);

        //then
        assertThat(future).isEqualTo(new Position(7, 2));
    }

}
