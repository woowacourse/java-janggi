package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HorseTest {

    private Piece horse = new Horse(new Position(5, 5), Team.RED);

    @DisplayName("마가 상단 + 좌측 대각으로 움직일 경우, 행은 -2, 열은 -1가 되어야 한다")
    @Test
    void when_horse_move_up_left() {
        horse.diagonalUpLeft();
        Position expectedPosition = new Position(3, 4);
        assertMove(expectedPosition);
    }

    @DisplayName("마가 상단 + 우측 대각으로 움직일 경우, 행은 -2, 열은 +1가 되어야 한다.")
    @Test
    void when_horse_move_up_right() {
        horse.diagonalUpRight();
        Position expectedPosition = new Position(3, 6);
        assertMove(expectedPosition);
    }

    @DisplayName("마가 좌측 + 상단 대각으로 움직일 경우, 행은 -1, 열은 -2가 되어야 한다.")
    @Test
    void when_horse_move_left_up() {
        horse.diagonalLeftUp();
        Position expectedPosition = new Position(4, 3);
        assertMove(expectedPosition);
    }

    @DisplayName("마가 좌측 + 하단 대각으로 움직일 경우, 행은 +1, 열은 -2가 되어야 한다.")
    @Test
    void when_horse_move_left_down() {
        horse.diagonalLeftDown();
        Position expectedPosition = new Position(6, 3);
        assertMove(expectedPosition);
    }

    @Test
    @DisplayName("마가 우측 + 상단 대각으로 움직일 경우, 행은 -1, 열은 +2이 되어야 한다.")
    void when_horse_move_right_up() {
        horse.diagonalRightUp();
        Position expectedPosition = new Position(4, 7);
        assertMove(expectedPosition);
    }

    @Test
    @DisplayName("마가 우측 + 하단 대각으로 움직일 경우, 행은 +1, 열은 +2이 되어야 한다.")
    void when_horse_move_right_down() {
        horse.diagonalRightDown();
        Position expectedPosition = new Position(6, 7);
        assertMove(expectedPosition);
    }

    @Test
    @DisplayName("마가 하단 + 좌측 대각으로 움직일 경우, 행은 +2, 열은 -1이 되어야 한다.")
    void when_horse_move_down_left() {
        horse.diagonalDownLeft();
        Position expectedPosition = new Position(7, 4);
        assertMove(expectedPosition);
    }

    @Test
    @DisplayName("마가 하단 + 우측 대각으로 움직일 경우, 행은 +2, 열은 +1이 되어야 한다.")
    void when_horse_move_down_right() {
        horse.diagonalDownRight();
        Position expectedPosition = new Position(7, 6);
        assertMove(expectedPosition);
    }

    private void assertMove(Position expectedPosition) {
        Position currentPosition = horse.getPosition();
        assertThat(currentPosition).isEqualTo(expectedPosition);
    }

    @Test
    @DisplayName("마가 10행 9열을 벗어나 움직이려고 할 경우, 예외를 발생시켜야 한다")
    void when_out_array_then_throw_exception() {
        Piece horse = new Horse(new Position(1, 1), Team.RED);
        assertThatThrownBy(() -> horse.diagonalLeftUp())
            .isInstanceOf(IllegalArgumentException.class);
    }

}
