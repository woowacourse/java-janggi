package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ByeongTest {

    private Byeong byeong = new Byeong(new Position(1, 4));

    @DisplayName("Byeong이 위로 움직이려고 할 경우 예외가 발생한다.")
    @Test
    void when_byeong_move_then_throw_exception() {
        assertThatThrownBy(() -> byeong.up())
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("해당 기물이 이동할 수 없는 방향입니다.");
    }

    @DisplayName("Byeong이 아래로 한 칸 움직일 경우, 행이 +1 되어야 한다.")
    @Test
    void when_byeong_move_then_column_plus_one() {
        byeong.down();

        Position expectedPosition = new Position(2, 4);
        Position currentPosition = byeong.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("Byeong이 좌측으로 한 칸 움직일 경우, 열이 -1 되어야 한다.")
    @Test
    void when_byeong_move_then_row_minus_one() {
        byeong.left();

        Position expectedPosition = new Position(1, 3);
        Position currentPosition = byeong.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("Byeong이 우측으로 한 칸 움직일 경우, 열이 +1 되어야 한다.")
    @Test
    void when_byeong_move_then_row_plus_one() {
        byeong.right();

        Position expectedPosition = new Position(1, 5);
        Position currentPosition = byeong.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @Test
    @DisplayName("Byeong이 우측으로 두 칸 움직일 경우, 열이 +2 되어야 한다.")
    void when_byeong_move_then_row_plus_two() {
        byeong.right();
        byeong.right();

        Position expectedPosition = new Position(1, 6);
        Position currentPosition = byeong.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @Test
    @DisplayName("Byeong이 10행 9열을 벗어나면 예외가 발생한다.")
    void Byeong이_10행_9열을_벗어나면_예외가_발생한다() {
        byeong.left();
        byeong.left();
        byeong.left();
        byeong.left();

        assertThatThrownBy(() -> byeong.left())
                .isInstanceOf(IllegalArgumentException.class);
    }

}
