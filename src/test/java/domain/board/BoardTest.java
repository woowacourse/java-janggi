package domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.coordinate.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class BoardTest {

    private final BasicBoardInitializer basicBoardInitializer = new BasicBoardInitializer();

    @Test
    @DisplayName("장기판을 생성한다.")
    void BoardInitializeTest() {
        // given - when - then
        assertDoesNotThrow(() -> new Board(basicBoardInitializer.initialize(), basicBoardInitializer.createTopology()));
    }

    @Test
    @DisplayName("장기판 범위 내의 좌표 입력은 정상 작동한다.")
    void moveTest() {
        // given
        Board board = new Board(basicBoardInitializer.initialize(), basicBoardInitializer.createTopology());
        Position start = new Position(9, 0);
        Position destination = new Position(8, 0);

        // when - then
        assertDoesNotThrow(() -> board.move(start, destination));
    }

    @Test
    @DisplayName("좌표 입력은 행은 0부터 8 열은 0 부터 9 범위여야 한다.")
    void isInvalid_True_Test() {
        // when - then
        assertDoesNotThrow(() -> new Position(4, 4));
    }

    @Test
    @DisplayName("0부터 9 범위를 넘어간 열 좌표 입력은 예외를 발생한다.")
    void isInvalid_Col_Test() {
        // when - then
        assertThatThrownBy(() -> new Position(10, 4))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("0부터 8 범위를 넘어간 행 좌표 입력은 예외를 발생한다.")
    void isInvalid_Row_Test() {
        // when - then
        assertThatThrownBy(() -> new Position(4, 9))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("0부터 9 범위를 넘어간 열 좌표 입력에 대한 이동은 예외를 발생한다.")
    void boardRange_Col_Error_Test() {
        // when - then
        assertThatThrownBy(() -> new Position(10, 4))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("0부터 8 범위를 넘어간 행 좌표 입력에 대한 이동은 예외를 발생한다.")
    void boardRange_Row_Error_Test() {
        // when - then
        assertThatThrownBy(() -> new Position(4, 9))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("음수 좌표 입력은 예외에 대한 이동은 발생한다.")
    void boardRange_Negative_Error_Test() {
        // when - then
        assertThatThrownBy(() -> new Position(-1, 4))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
