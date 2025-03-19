package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ElephantTest {

    private Piece elephant = new Elephant(new Position(5, 5), Team.RED);

    @DisplayName("상이 상단 + 좌측 대각으로 움직일 경우, 행은 -3, 열은 -2가 되어야 한다")
    @Test
    void when_elephant_move_up_left() {
        elephant.diagonalUpLeft();
        Position expectedPosition = new Position(2, 3);
        assertMove(expectedPosition);
    }

    @DisplayName("상이 상단 + 우측 대각으로 움직일 경우, 행은 -3, 열은 +2가 되어야 한다.")
    @Test
    void when_elephant_move_up_right() {
        elephant.diagonalUpRight();
        Position expectedPosition = new Position(2, 7);
        assertMove(expectedPosition);
    }

    @DisplayName("상이 좌측 + 상단 대각으로 움직일 경우, 행은 -2, 열은 -3가 되어야 한다.")
    @Test
    void when_elephant_move_left_up() {
        elephant.diagonalLeftUp();
        Position expectedPosition = new Position(3, 2);
        assertMove(expectedPosition);
    }

    @DisplayName("상이 좌측 + 하단 대각으로 움직일 경우, 행은 +2, 열은 -3가 되어야 한다.")
    @Test
    void when_elephant_move_left_down() {
        elephant.diagonalLeftDown();
        Position expectedPosition = new Position(7, 2);
        assertMove(expectedPosition);
    }

    @Test
    @DisplayName("상이 우측 + 상단 대각으로 움직일 경우, 행은 -2, 열은 +3이 되어야 한다.")
    void when_elephant_move_right_up() {
        elephant.diagonalRightUp();
        Position expectedPosition = new Position(3, 8);
        assertMove(expectedPosition);
    }

    @Test
    @DisplayName("상이 우측 + 하단 대각으로 움직일 경우, 행은 +2, 열은 +3이 되어야 한다.")
    void when_elephant_move_right_down() {
        elephant.diagonalRightDown();
        Position expectedPosition = new Position(7, 8);
        assertMove(expectedPosition);
    }

    @Test
    @DisplayName("상이 하단 + 좌측 대각으로 움직일 경우, 행은 +3, 열은 -2이 되어야 한다.")
    void when_elephant_move_down_left() {
        elephant.diagonalDownLeft();
        Position expectedPosition = new Position(8, 3);
        assertMove(expectedPosition);
    }

    @Test
    @DisplayName("상이 하단 + 우측 대각으로 움직일 경우, 행은 +3, 열은 +2이 되어야 한다.")
    void when_elephant_move_down_right() {
        elephant.diagonalDownRight();
        Position expectedPosition = new Position(8, 7);
        assertMove(expectedPosition);
    }

    private void assertMove(Position expectedPosition) {
        Position currentPosition = elephant.getPosition();
        assertThat(currentPosition).isEqualTo(expectedPosition);
    }

    @Test
    @DisplayName("상이 10행 9열을 벗어나 움직이려고 할 경우, 예외를 발생시켜야 한다")
    void when_out_array_then_throw_exception() {
        Piece elephant = new Elephant(new Position(2, 1), Team.RED);
        assertThatThrownBy(() -> elephant.diagonalLeftUp())
            .isInstanceOf(IllegalArgumentException.class);
    }
}
