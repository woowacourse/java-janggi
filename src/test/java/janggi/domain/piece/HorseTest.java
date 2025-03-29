package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("마 테스트")
class HorseTest {

    Dynasty currentTurnDynasty = Dynasty.HAN;
    Piece horse = new Horse(currentTurnDynasty);

    @DisplayName("마가 가는 방향에 기물이 없다면 이동할 수 있다.")
    @Test
    void canMove() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());

        // when
        boolean canMove = horse.canMove(janggiBoard, currentTurnDynasty, new Point(1, 1), new Point(2, 3));

        // then
        assertThat(canMove).isTrue();
    }

    @DisplayName("마가 규칙 상 갈 수 없는 목적지는 갈 수 없다.")
    @Test
    void isNotMovable_WhenImpossibleEndPoint() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());

        // then
        assertThatThrownBy(() -> horse.canMove(janggiBoard, currentTurnDynasty, new Point(1, 1), new Point(2, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("마가 가는 방향에 기물이 있다면 이동할 수 없다.")
    @Test
    void isNotMovable_WhenPieceInPath() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(1, 2), new Horse(Dynasty.HAN)
        ));

        // then
        assertThatThrownBy(() -> horse.canMove(janggiBoard, currentTurnDynasty, new Point(1, 1), new Point(2, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("목적지에 상대편의 기물이 있는 경우에는 갈 수 있다.")
    @Test
    void isNotMovable_WhenOtherPieceInEndPoint() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(2, 3), new Horse(Dynasty.CHU)
        ));

        // when
        boolean canMove = horse.canMove(janggiBoard, currentTurnDynasty, new Point(1, 1), new Point(2, 3));

        // then
        assertThat(canMove)
                .isTrue();
    }
}