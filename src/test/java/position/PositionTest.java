package position;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pieces.Side;

class PositionTest {

    private static final int ONE_SPACE = 1;
    private static final Position DEFAULT_POSITION = new Position(1, 1);;

    @Nested
    @DisplayName("초나라의 이동을 검증한다")
    class ChoMoving {

        @Test
        void 초나라가_앞으로_이동하면_ROW가_1_증가한다() {
            // given
            Side side = Side.CHO;
            Position before = DEFAULT_POSITION;
            // when
            Position after = before.moveForward(side);
            // then
            assertThat(after.row()).isEqualTo(before.row() + ONE_SPACE);
        }

        @Test
        void 초나라가_뒤로_이동하면_ROW가_1_감소한다() {
            // given
            Side side = Side.CHO;
            Position before = DEFAULT_POSITION;
            // when
            Position after = before.moveBack(side);
            // then
            assertThat(after.row()).isEqualTo(before.row() - ONE_SPACE);
        }

        @Test
        void 초나라가_좌측으로_이동하면_COLUMN이_1_감소한다() {
            // given
            Side side = Side.CHO;
            Position before = DEFAULT_POSITION;
            // when
            Position after = before.moveLeft(side);
            // then
            assertThat(after.column()).isEqualTo(before.column() - ONE_SPACE);
        }

        @Test
        void 초나라가_우측으로_이동하면_COLUMN이_1_증가한다() {
            // given
            Side side = Side.CHO;
            Position before = DEFAULT_POSITION;
            // when
            Position after = before.moveRight(side);
            // then
            assertThat(after.column()).isEqualTo(before.column() + ONE_SPACE);
        }
    }

    @Nested
    @DisplayName("한나라의 이동을 검증한다")
    class HanMoving {

        @Test
        void 한나라가_앞으로_이동하면_ROW가_1_감소한다() {
            // given
            Side side = Side.HAN;
            Position before = DEFAULT_POSITION;
            // when
            Position after = before.moveForward(side);
            // then
            assertThat(after.row()).isEqualTo(before.row() - ONE_SPACE);
        }

        @Test
        void 한나라가_뒤로_이동하면_ROW가_1_증가한다() {
            // given
            Side side = Side.HAN;
            Position before = DEFAULT_POSITION;
            // when
            Position after = before.moveBack(side);
            // then
            assertThat(after.row()).isEqualTo(before.row() + ONE_SPACE);
        }

        @Test
        void 한나라가_좌측으로_이동하면_COLUMN이_1_증가한다() {
            // given
            Side side = Side.HAN;
            Position before = DEFAULT_POSITION;
            // when
            Position after = before.moveLeft(side);
            // then
            assertThat(after.column()).isEqualTo(before.column() + ONE_SPACE);
        }

        @Test
        void 한나라가_우측으로_이동하면_COLUMN이_1_감소한다() {
            // given
            Side side = Side.HAN;
            Position before = DEFAULT_POSITION;
            // when
            Position after = before.moveRight(side);
            // then
            assertThat(after.column()).isEqualTo(before.column() - ONE_SPACE);
        }
    }
}
