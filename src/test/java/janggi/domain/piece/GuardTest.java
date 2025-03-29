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

@DisplayName("사 테스트")
class GuardTest {

    Dynasty currentTurnDynasty = Dynasty.HAN;
    Piece guard = new Guard(currentTurnDynasty);

    @DisplayName("사가 가는 방향에 기물이 없다면 이동할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "2, 5, 1, 4",
            "2, 5, 1, 5",
            "2, 5, 3, 5",
            "2, 5, 3, 5"
    })
    void canMove(int x1, int y1, int x2, int y2) {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());

        // when
        boolean canMove = guard.canMove(janggiBoard, currentTurnDynasty, new Point(x1, y1), new Point(x2, y2));

        // then
        assertThat(canMove).isTrue();
    }

    @DisplayName("사가 규칙 상 갈 수 없는 목적지는 갈 수 없다.")
    @Test
    void isNotMovable_WhenImpossibleEndPoint() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());

        // when, then
        assertThatThrownBy(() -> guard.canMove(janggiBoard, currentTurnDynasty, new Point(2, 4), new Point(2, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("목적지에 상대편의 기물이 있는 경우에는 갈 수 있다.")
    @Test
    void isNotMovable_WhenOtherPieceInEndPoint() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(2, 4), new Horse(Dynasty.CHU)
        ));

        // when
        boolean canMove = guard.canMove(janggiBoard, currentTurnDynasty, new Point(2, 5), new Point(2, 4));

        // then
        assertThat(canMove)
                .isTrue();
    }

    @DisplayName("사는 자신의 궁성안에서만 움직일 수 있다")
    @Test
    void isMovable_InPalace() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());

        // when
        boolean canMove = guard.canMove(janggiBoard, currentTurnDynasty, new Point(2, 5), new Point(2, 4));

        // then
        assertThat(canMove)
                .isTrue();
    }

    @DisplayName("사는 자신의 궁성밖에서는 움직일 수 없다")
    @Test
    void isNotMovable_InPalace() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());

        // then
        assertThatThrownBy(() -> guard.canMove(janggiBoard, currentTurnDynasty, new Point(1,4), new Point(1, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
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
        Guard dynastyGuard = new Guard(pieceDynasty);

        // when
        assertThat(dynastyGuard.isSameDynasty(compareDynasty))
                .isSameAs(expected);
    }

    @DisplayName("같은 피스인지 확인한다")
    @Test
    void isEqualPieceType_True() {
        assertThat(guard.isEqualPieceType(PieceType.GUARD))
                .isTrue();
    }

    @DisplayName("다른 피스인지 확인한다")
    @Test
    void isEqualPieceType_False() {
        assertThat(guard.isEqualPieceType(PieceType.CANNON))
                .isFalse();
    }
}