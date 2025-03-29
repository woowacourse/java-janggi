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

@DisplayName("상 테스트")
class ElephantTest {

    Dynasty currentTurnDynasty = Dynasty.HAN;
    Piece elephant = new Elephant(currentTurnDynasty);

    @DisplayName("상이 가는 방향에 기물이 없다면 이동할 수 있다.")
    @Test
    void canMove() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());

        // when
        boolean canMove = elephant.canMove(janggiBoard, currentTurnDynasty, new Point(1, 1), new Point(4, 3));

        // then
        assertThat(canMove).isTrue();
    }

    @DisplayName("상이 규칙 상 갈 수 없는 목적지는 갈 수 없다.")
    @Test
    void isNotMovable_WhenImpossibleEndPoint() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());

        // then
        assertThatThrownBy(() -> elephant.canMove(janggiBoard, currentTurnDynasty, new Point(1, 1), new Point(4, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("상이 가는 방향에 기물이 있다면 이동할 수 없다.")
    @Test
    void isNotMovable_WhenPieceInPath() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(2, 1), new Horse(Dynasty.HAN)
        ));

        // then
        assertThatThrownBy(() -> elephant.canMove(janggiBoard, currentTurnDynasty, new Point(1, 1), new Point(4, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("목적지에 상대편의 기물이 있는 경우에는 갈 수 있다.")
    @Test
    void isNotMovable_WhenOtherPieceInEndPoint() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(4, 3), new Horse(Dynasty.CHU)
        ));

        // when
        boolean canMove = elephant.canMove(janggiBoard, currentTurnDynasty, new Point(1, 1), new Point(4, 3));

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
        Elephant dynastyElephant = new Elephant(pieceDynasty);

        // when
        assertThat(dynastyElephant.isSameDynasty(compareDynasty))
                .isSameAs(expected);
    }

    @DisplayName("같은 피스인지 확인한다")
    @Test
    void isEqualPieceType_True() {
        assertThat(elephant.isEqualPieceType(PieceType.ELEPHANT))
                .isTrue();
    }

    @DisplayName("다른 피스인지 확인한다")
    @Test
    void isEqualPieceType_False() {
        assertThat(elephant.isEqualPieceType(PieceType.CANNON))
                .isFalse();
    }
}