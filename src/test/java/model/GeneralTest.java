package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import model.piece.Pieces;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GeneralTest {

    private General general = new General(new Position(1, 4), Team.RED);

    @DisplayName("General이 위로 한 칸 움직일 경우, 행이 -1 되어야 한다.")
    @Test
    void when_general_move_then_column_minus_one() {
        general.up(1);

        Position expectedPosition = new Position(0, 4);
        Position currentPosition = general.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("General이 아래로 한 칸 움직일 경우, 행이 +1 되어야 한다.")
    @Test
    void when_general_move_then_column_plus_one() {
        general.down(1);

        Position expectedPosition = new Position(2, 4);
        Position currentPosition = general.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("General이 좌측으로 한 칸 움직일 경우, 열이 -1 되어야 한다.")
    @Test
    void when_general_move_then_row_minus_one() {
        general.left(1);

        Position expectedPosition = new Position(1, 3);
        Position currentPosition = general.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("General이 우측으로 한 칸 움직일 경우, 열이 +1 되어야 한다.")
    @Test
    void when_general_move_then_row_plus_one() {
        general.right(1);

        Position expectedPosition = new Position(1, 5);
        Position currentPosition = general.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @Test
    @DisplayName("General이 우측으로 두 칸 움직일 경우, 열이 +2 되어야 한다.")
    void when_general_move_then_row_plus_two() {
        general.right(1);
        general.right(1);

        Position expectedPosition = new Position(1, 6);
        Position currentPosition = general.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @Test
    @DisplayName("General이 10행 9열을 벗어나면 예외가 발생한다.")
    void General이_10행_9열을_벗어나면_예외가_발생한다() {
        general.up(1);

        assertThatThrownBy(() -> general.up(1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    @DisplayName("왕이 두 칸 이동하려고 하는 경우, 예외를 발생해야 한다.")
    class GeneralCanMoveOnlyOne {

        @Test
        @DisplayName("왼쪽으로 두 칸 이동하는 경우, 예외를 발생시켜야 한다.")
        void when_general_move_left_amount_over_one_then_throw_exception() {
            assertThatThrownBy(() -> general.left(2))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("오른쪽으로 두 칸 이동하는 경우, 예외를 발생시켜야 한다.")
        void when_general_move_right_amount_over_one_then_throw_exception() {
            assertThatThrownBy(() -> general.right(2))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("아래로 두 칸 이동하는 경우, 예외를 발생시켜야 한다.")
        void when_general_move_down_amount_over_one_then_throw_exception() {
            assertThatThrownBy(() -> general.down(2))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("아래로 두 칸 이동하는 경우, 예외를 발생시켜야 한다.")
        void when_general_move_up_amount_over_one_then_throw_exception() {
            assertThatThrownBy(() -> general.up(2))
                .isInstanceOf(IllegalArgumentException.class);
        }
        @DisplayName("General up, down, left, right 움직임 테스트")
        @Nested
        class GeneralMoving {

            Pieces pieces = Pieces.createAndInit();

            @Test
            @DisplayName("up_테스트")
            void up_case() {
                Piece piece = pieces.findPiece(new Position(1, 4));
                piece.up(1);
            }
        }
    }
}
