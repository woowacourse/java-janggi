package pieceProperty;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PositionTest {

    @DisplayName("위치는 행과 열의 위치 정보를 가진다.")
    @Test
    void locationCreate() {
        //given
        Position position = new Position(1, 6);

        //when - then
        assertThat(position.getRow()).isEqualTo(1);
        assertThat(position.getCol()).isEqualTo(6);
    }

    @Test
    @DisplayName("움직임 계산 테스트")
    void upMovementTest() {
        //given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateMovement(-1, 0);

        //then
        assertThat(future).isEqualTo(new Position(4, 5));
    }

    @Test
    @DisplayName("행, 열 변화율 계산 테스트")
    void calculateDRowDColTest() {
        Position position = new Position(5, 5);
        Position position1 = new Position(4, 3);

        assertThat(position.calculateDRow(position1)).isEqualTo(1);
        assertThat(position.calculateDCol(position1)).isEqualTo(2);
    }

    @Test
    @DisplayName("위 움직임 계산 테스트")
    void calculateUpMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateUpMovement();

        //then
        assertThat(future).isEqualTo(new Position(4, 5));
    }

    @Test
    @DisplayName("아래 움직임 계산 테스트")
    void calculateDownMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateDownMovement();

        //then
        assertThat(future).isEqualTo(new Position(6, 5));
    }

    @Test
    @DisplayName("왼쪽 움직임 계산 테스트")
    void calculateLeftMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateLeftMovement();

        //then
        assertThat(future).isEqualTo(new Position(5, 4));
    }

    @Test
    @DisplayName("오른쪽 움직임 계산 테스트")
    void calculateRightMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateRightMovement();

        //then
        assertThat(future).isEqualTo(new Position(5, 6));
    }

    @Test
    @DisplayName("오른쪽 위 대각선 움직임 계산 테스트")
    void upCalculateRightMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateRightUpMovement();

        //then
        assertThat(future).isEqualTo(new Position(4, 6));
    }

    @Test
    @DisplayName("왼쪽 위 대각선 움직임 계산 테스트")
    void upCalculateLeftMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateLeftUpMovement();

        //then
        assertThat(future).isEqualTo(new Position(4, 4));
    }

    @Test
    @DisplayName("오른쪽 아래 대각선 움직임 계산 테스트")
    void downCalculateRightMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateRightDownMovement();

        //then
        assertThat(future).isEqualTo(new Position(6, 6));
    }

    @Test
    @DisplayName("왼쪽 아래 대각선 움직임 계산 테스트")
    void downCalculateLeftMovementTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateLeftDownMovement();

        //then
        assertThat(future).isEqualTo(new Position(6, 4));
    }

    @Test
    @DisplayName("위_오른쪽 대각선 움직임 계산 테스트")
    void upRightUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateUpRightUPMovement();

        //then
        assertThat(future).isEqualTo(new Position(3, 6));
    }

    @Test
    @DisplayName("위_왼쪽 위 대각선 움직임 계산 테스트")
    void upLeftUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateUpLeftUpMovement();

        //then
        assertThat(future).isEqualTo(new Position(3, 4));
    }

    @Test
    @DisplayName("오른쪽_오른쪽 위 대각선 움직임 계산 테스트")
    void rightRightUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateRightRightUpMovement();

        //then
        assertThat(future).isEqualTo(new Position(4, 7));
    }

    @Test
    @DisplayName("오른쪽_오른쪽 아래 대각선 움직임 계산 테스트")
    void rightRightDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateRightRightDownMovement();

        //then
        assertThat(future).isEqualTo(new Position(6, 7));
    }

    @Test
    @DisplayName("아래_오른쪽 아래 대각선 움직임 계산 테스트")
    void downRightDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateDownRightDownMovement();

        //then
        assertThat(future).isEqualTo(new Position(7, 6));
    }

    @Test
    @DisplayName("아래_왼쪽 아래 대각선 움직임 계산 테스트")
    void downLeftDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateDownLeftDownMovement();

        //then
        assertThat(future).isEqualTo(new Position(7, 4));
    }

    @Test
    @DisplayName("왼쪽_왼쪽 위 대각선 움직임 계산 테스트")
    void leftLeftUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateLeftLeftUpMovement();

        //then
        assertThat(future).isEqualTo(new Position(4, 3));
    }

    @Test
    @DisplayName("왼쪽_왼쪽 아래 대각선 움직임 계산 테스트")
    void leftLeftDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateLeftLeftDownMovement();

        //then
        assertThat(future).isEqualTo(new Position(6, 3));
    }

    @Test
    @DisplayName("위_오른쪽 대각선_오른쪽 대각선 움직인 계산 테스트")
    void upRightUpRightUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateUpRightUpRightUpMovement();

        //then
        assertThat(future).isEqualTo(new Position(2, 7));
    }

    @Test
    @DisplayName("위_왼쪽 대각선_왼쪽 대각선 움직인 계산 테스트")
    void upLeftUpLeftUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateUpLeftUpLeftUpMovement();

        //then
        assertThat(future).isEqualTo(new Position(2, 3));
    }

    @Test
    @DisplayName("오른쪽_오른쪽 대각선_오른쪽 대각선 움직임 계산 테스트")
    void rightRightUpRightUpTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateRightRightUpRightUpMovement();

        //then
        assertThat(future).isEqualTo(new Position(3, 8));
    }

    @Test
    @DisplayName("오른쪽_오른쪽 아래 대각선_오른쪽 아래 대각선 움직임 계산 테스트")
    void rightRightDownRightDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateRightRightDownRightDownMovement();

        //then
        assertThat(future).isEqualTo(new Position(7, 8));
    }

    @Test
    @DisplayName("아래_오른쪽 아래 대각선_오른쪽 아래 대각선 움직임 계산 테스트")
    void downRightDownRightDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateDownRightDownRightDownMovement();

        //then
        assertThat(future).isEqualTo(new Position(8, 7));
    }

    @Test
    @DisplayName("아래_왼쪽 아래 대각선_왼쪽 아래 대각선 움직임 계산 테스트")
    void downLeftDownLeftDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateDownLeftDownLeftDownMovement();

        //then
        assertThat(future).isEqualTo(new Position(8, 3));
    }

    @Test
    @DisplayName("왼쪽_왼쪽 위 대각선_왼쪽 위 대각선 움직임 계산 테스트")
    void leftLeftUpLeftUPTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateLeftLeftUpLeftUpMovement();

        //then
        assertThat(future).isEqualTo(new Position(3, 2));
    }

    @Test
    @DisplayName("왼쪽_왼쪽 아래 대각선_왼쪽 아래 대각선 움직임 계산 테스트")
    void leftLeftDownLeftDownTest() {
        // given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateLeftLeftDownLeftDownMovement();

        //then
        assertThat(future).isEqualTo(new Position(7, 2));
    }

    @Test
    @DisplayName("초나라 궁성 판단")
    void isInChoPalace() {
        //given
        Position position = new Position(7, 3);
        Position position1 = new Position(9, 6);

        //when - then
        assertThat(position.isInChoPalace()).isTrue();
        assertThat(position1.isInChoPalace()).isFalse();
    }

    @Test
    @DisplayName("한나라 궁성 판단")
    void isInHanPalace() {
        //given
        Position position = new Position(0, 3);
        Position position1 = new Position(2, 6);

        //when - then
        assertThat(position.isInHanPalace()).isTrue();
        assertThat(position1.isInHanPalace()).isFalse();
    }

    @DisplayName("한나라 한 칸 움직임 기물 테스트")
    @ParameterizedTest
    @MethodSource("providePositionForPalaceTest")
    void isOneStepDiagonalMoveForHanOmniDirectionMoverTest(Position start, Position end) {
        assertThat(start.isOneStepDiagonalMoveForHanOmniDirectionMover(end)).isTrue();
    }

    private static Stream<Arguments> providePositionForPalaceTest() {
        return Stream.of(
                Arguments.of(new Position(0, 3), new Position(1, 4)),
                Arguments.of(new Position(0, 5), new Position(1, 4)),
                Arguments.of(new Position(2, 3), new Position(1, 4)),
                Arguments.of(new Position(2, 5), new Position(1, 4)),

                Arguments.of(new Position(1, 4), new Position(0, 3)),
                Arguments.of(new Position(1, 4), new Position(0, 5)),
                Arguments.of(new Position(1, 4), new Position(2, 3)),
                Arguments.of(new Position(1, 4), new Position(2, 5))
        );
    }

    @DisplayName("초나라 한 칸 움직임 기물 테스트")
    @ParameterizedTest
    @MethodSource("providePositionForChoPalaceTest")
    void isOneStepDiagonalMoveForChoOmniDirectionMoverTest(Position start, Position end) {
        assertThat(start.isOneStepDiagonalMoveForChoOmniDirectionMover(end)).isTrue();
    }

    private static Stream<Arguments> providePositionForChoPalaceTest() {
        return Stream.of(
                Arguments.of(new Position(7, 3), new Position(8, 4)),
                Arguments.of(new Position(7, 5), new Position(8, 4)),
                Arguments.of(new Position(9, 3), new Position(8, 4)),
                Arguments.of(new Position(9, 5), new Position(8, 4)),

                Arguments.of(new Position(8, 4), new Position(7, 3)),
                Arguments.of(new Position(8, 4), new Position(7, 5)),
                Arguments.of(new Position(8, 4), new Position(9, 3)),
                Arguments.of(new Position(8, 4), new Position(9, 5))
        );
    }

}
