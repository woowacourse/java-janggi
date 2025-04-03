package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.domain.board.Point;
import janggi.domain.camp.Camp;
import janggi.domain.piece.type.MoveType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {

    @DisplayName("군인이 뒤로 움직일 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({"HAN, 2", "CHU, 0"})
    void shouldThrowException_WhenMoveBackward(Camp camp, int toY) {
        // given
        Soldier soldier = new Soldier(camp);
        Point from = new Point(0, 1);
        Point to = new Point(0, toY);

        // when & then
        assertThatCode(() -> soldier.validateMovementRule(MoveType.NORMAL, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("뒤로 갈 수 없습니다.");
    }

    @DisplayName("군인은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.")
    @ParameterizedTest
    @CsvSource({"HAN, 0, 1", "HAN, 2, 1", "HAN, 1, 0", "CHU, 0, 1", "CHU, 2, 1", "CHU, 1, 2"})
    void validMoveTest(Camp camp, int toX, int toY) {
        // given
        Soldier soldier = new Soldier(camp);
        Point from = new Point(1, 1);
        Point to = new Point(toX, toY);

        // when & then
        assertThatCode(() -> soldier.validateMovementRule(MoveType.NORMAL, from, to))
                .doesNotThrowAnyException();
    }

    @DisplayName("군인은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.")
    @ParameterizedTest
    @CsvSource({"HAN, 3, 1", "CHU, 0, 3"})
    void shouldThrowException_WhenInvalidMove(Camp camp, int toX, int toY) {
        // given
        Soldier soldier = new Soldier(camp);
        Point from = new Point(1, 1);
        Point to = new Point(toX, toY);

        // when & then
        assertThatCode(() -> soldier.validateMovementRule(MoveType.NORMAL, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
    }

    @DisplayName("같은 진영의 기물을 잡을 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchSameCamp() {
        // given
        Soldier soldier = new Soldier(Camp.HAN);

        // when & then
        assertThatCode(() -> soldier.validateCatch(new Soldier(Camp.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 진영의 기물을 잡을 수 없습니다.");
    }

    @DisplayName("군인은 궁성 안에서 대각선으로 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({"3, 2", "5, 2"})
    void palaceMoveTest(int toX, int toY) {
        // given
        Soldier soldier = new Soldier(Camp.CHU);
        Point from = new Point(4, 1);
        Point to = new Point(toX, toY);

        // when & then
        assertThatCode(() -> soldier.validateMovementRule(MoveType.PALACE, from, to))
                .doesNotThrowAnyException();
    }

    @DisplayName("군인은 궁성 안에서 앞 또는 양 옆, 대각선으로 한 칸만 움직이지 않는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenMoveBackwardInPalace() {
        // given
        Soldier soldier = new Soldier(Camp.CHU);
        Point from = new Point(4, 1);
        Point to = new Point(6, 3);

        // when & then
        assertThatCode(() -> soldier.validateMovementRule(MoveType.PALACE, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("졸은 궁성 안에서 앞 또는 양 옆, 대각선으로 한 칸만 움직여야 합니다.");
    }
}
