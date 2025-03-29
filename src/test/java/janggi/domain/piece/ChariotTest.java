package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("차 테스트")
class ChariotTest {

    Dynasty currentTurnDynasty = Dynasty.HAN;
    Piece chariot = new Chariot(currentTurnDynasty);

    @DisplayName("차가 가는 방향에 기물이 없다면 이동할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "4, 4, 4, 1",
            "4, 4, 1, 4",
            "4, 4, 8, 4",
            "4, 4, 4, 8"
    })
    void canMove(int x1, int y1, int x2, int y2) {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());

        // when
        boolean canMove = chariot.canMove(janggiBoard, currentTurnDynasty, new Point(x1, y1), new Point(x2, y2));

        // then
        assertThat(canMove).isTrue();
    }

    @DisplayName("궁성 내에서는 대각선으로 이동가능하다.")
    @ParameterizedTest
    @CsvSource({
            "1, 4, 3, 6",
            "1, 6, 3, 4"
    })
    void isMovable_InPalace(int startX, int startY, int endX, int endY) {
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(2, 5), new Horse(Dynasty.CHU)
        ));
        boolean canMove = chariot.canMove(janggiBoard, currentTurnDynasty, new Point(startX, startY),
                new Point(endX, endY));

        assertThat(canMove)
                .isTrue();
    }

    @DisplayName("차가 규칙 상 갈 수 없는 목적지는 갈 수 없다.")
    @Test
    void isNotMovable_WhenImpossibleEndPoint() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());

        // when, then
        assertThatThrownBy(() -> chariot.canMove(janggiBoard, currentTurnDynasty, new Point(1, 1), new Point(2, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("차가 가는 방향에 기물이 있다면 이동할 수 없다.")
    @Test
    void isNotMovable_WhenPieceInPath() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(1, 2), new Horse(Dynasty.HAN)
        ));

        // when, then
        assertThatThrownBy(() -> chariot.canMove(janggiBoard, currentTurnDynasty, new Point(1, 1), new Point(1, 4)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("목적지에 상대편의 기물이 있는 경우에는 갈 수 있다.")
    @Test
    void isNotMovable_WhenOtherPieceInEndPoint() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(1, 4), new Horse(Dynasty.CHU)
        ));

        // when
        boolean canMove = chariot.canMove(janggiBoard, currentTurnDynasty, new Point(1, 1), new Point(1, 4));

        // then
        assertThat(canMove)
                .isTrue();
    }

    @DisplayName("같은 나라인지 확인한다")
    @ParameterizedTest
    @CsvSource({
            "HAN, HAN, true",
            "CHU, CHU, true",
            "HAN, CHU, false"
    })
    void isSameDynasty(Dynasty pieceDynasty, Dynasty compareDynasty, boolean expected) {
        // given
        Chariot dynastyChariot = new Chariot(pieceDynasty);

        // when
        assertThat(dynastyChariot.isSameDynasty(compareDynasty))
                .isSameAs(expected);
    }

    @DisplayName("같은 피스인지 확인한다")
    @Test
    void isEqualPieceType_True() {
        assertThat(chariot.isEqualPieceType(PieceType.CHARIOT))
                .isTrue();
    }

    @DisplayName("다른 피스인지 확인한다")
    @Test
    void isEqualPieceType_False() {
        assertThat(chariot.isEqualPieceType(PieceType.CANNON))
                .isFalse();
    }
}