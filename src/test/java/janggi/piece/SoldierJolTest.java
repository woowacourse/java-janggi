package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Board;
import janggi.board.point.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierJolTest {

    @DisplayName("졸은 뒤로 갈 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenMoveBackward() {
        // given
        Board board = new Board();
        SoldierJol soldierJol = new SoldierJol(board);

        // when & then
        assertThatCode(() -> soldierJol.validateMove(new Point(4, 4), new Point(4, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("졸은 뒤로 갈 수 없으며, 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
    }

    @DisplayName("졸은 대각선으로 움직일 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenMoveDiagonal() {
        // given
        Board board = new Board();
        SoldierJol soldierJol = new SoldierJol(board);

        // when & then
        assertThatCode(() -> soldierJol.validateMove(new Point(4, 4), new Point(5, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("졸은 뒤로 갈 수 없으며, 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
    }

    @DisplayName("졸은 앞 또는 양 옆으로 한 칸만 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "3, 4",
            "5, 4",
            "4, 5"
    })
    void validateMoveTest(int toX, int toY) {
        // given
        Board board = new Board();
        SoldierJol soldierJol = new SoldierJol(board);

        // when & then
        assertThatCode(() -> soldierJol.validateMove(new Point(4, 4), new Point(toX, toY)))
                .doesNotThrowAnyException();
    }

    @DisplayName("졸은 같은 진영의 기물을 잡을 수 없다.")
    @ParameterizedTest
    @CsvSource({
            "CHU, false",
            "HAN, true"
    })
    void canCaptureTest(Camp camp, boolean expected) {
        // given
        Board board = new Board();
        SoldierJol soldierJol = new SoldierJol(board);

        // when
        boolean canCapture = soldierJol.canCapture(new Horse(camp, board));

        // then
        assertThat(canCapture)
                .isSameAs(expected);
    }

    @DisplayName("졸은 같은 진영의 기물을 잡을 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchSameCamp() {
        // given
        Board board = new Board();
        SoldierJol soldierJol = new SoldierJol(board);

        // when & then
        assertThatCode(() -> soldierJol.validateCatch(new SoldierJol(board)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물을 잡을 수 없습니다.");
    }

    @DisplayName("졸이 정상적으로 생성되는지 테스트한다.")
    @Test
    void createTest() {
        // given
        Board board = new Board();

        // when & then
        assertThatCode(() -> new SoldierJol(board))
                .doesNotThrowAnyException();
    }

    @DisplayName("자신의 기물 형태를 반환한다.")
    @Test
    void getPieceSymbolTest() {
        // given
        Board board = new Board();
        SoldierJol soldierJol = new SoldierJol(board);

        // when
        PieceSymbol pieceSymbol = soldierJol.getPieceSymbol();

        // then
        assertThat(pieceSymbol)
                .isSameAs(PieceSymbol.SOLDIER_JOL);
    }
}
