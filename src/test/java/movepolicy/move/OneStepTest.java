package movepolicy.move;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pieces.Side;
import position.Position;

class OneStepTest {

    private static final Position DEFAULT = new Position(4, 4);

    @Nested
    @DisplayName("초 기준으로 한 칸 이동한다")
    class ChoMove {

        @Test
        void 앞으로_이동한다() {
            // given
            OneStep step = OneStep.FORWARD;
            // when
            Position moved = step.move(DEFAULT, Side.CHO);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.CHO.forwardDelta()));
        }

        @Test
        void 뒤로_이동한다() {
            // given
            OneStep step = OneStep.BACK;
            // when
            Position moved = step.move(DEFAULT, Side.CHO);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.CHO.backDelta()));
        }

        @Test
        void 좌로_이동한다() {
            // given
            OneStep step = OneStep.LEFT;
            // when
            Position moved = step.move(DEFAULT, Side.CHO);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.CHO.leftDelta()));
        }

        @Test
        void 우로_이동한다() {
            // given
            OneStep step = OneStep.RIGHT;
            // when
            Position moved = step.move(DEFAULT, Side.CHO);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.CHO.rightDelta()));
        }

        @Test
        void 좌전으로_이동한다() {
            // given
            OneStep step = OneStep.LEFT_FORWARD;
            // when
            Position moved = step.move(DEFAULT, Side.CHO);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.CHO.leftForwardDelta()));
        }

        @Test
        void 우전으로_이동한다() {
            // given
            OneStep step = OneStep.RIGHT_FORWARD;
            // when
            Position moved = step.move(DEFAULT, Side.CHO);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.CHO.rightForwardDelta()));
        }

        @Test
        void 우후로_이동한다() {
            // given
            OneStep step = OneStep.RIGHT_BACK;
            // when
            Position moved = step.move(DEFAULT, Side.CHO);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.CHO.rightBackDelta()));
        }

        @Test
        void 좌후로_이동한다() {
            // given
            OneStep step = OneStep.LEFT_BACK;
            // when
            Position moved = step.move(DEFAULT, Side.CHO);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.CHO.leftBackDelta()));
        }
    }

    @Nested
    @DisplayName("한 기준으로 한 칸 이동한다")
    class HanMove {

        @Test
        void 앞으로_이동한다() {
            // given
            OneStep step = OneStep.FORWARD;
            // when
            Position moved = step.move(DEFAULT, Side.HAN);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.HAN.forwardDelta()));
        }

        @Test
        void 뒤로_이동한다() {
            // given
            OneStep step = OneStep.BACK;
            // when
            Position moved = step.move(DEFAULT, Side.HAN);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.HAN.backDelta()));
        }

        @Test
        void 좌로_이동한다() {
            // given
            OneStep step = OneStep.LEFT;
            // when
            Position moved = step.move(DEFAULT, Side.HAN);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.HAN.leftDelta()));
        }

        @Test
        void 우로_이동한다() {
            // given
            OneStep step = OneStep.RIGHT;
            // when
            Position moved = step.move(DEFAULT, Side.HAN);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.HAN.rightDelta()));
        }

        @Test
        void 좌전으로_이동한다() {
            // given
            OneStep step = OneStep.LEFT_FORWARD;
            // when
            Position moved = step.move(DEFAULT, Side.HAN);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.HAN.leftForwardDelta()));
        }

        @Test
        void 우전으로_이동한다() {
            // given
            OneStep step = OneStep.RIGHT_FORWARD;
            // when
            Position moved = step.move(DEFAULT, Side.HAN);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.HAN.rightForwardDelta()));
        }

        @Test
        void 우후로_이동한다() {
            // given
            OneStep step = OneStep.RIGHT_BACK;
            // when
            Position moved = step.move(DEFAULT, Side.HAN);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.HAN.rightBackDelta()));
        }

        @Test
        void 좌후로_이동한다() {
            // given
            OneStep step = OneStep.LEFT_BACK;
            // when
            Position moved = step.move(DEFAULT, Side.HAN);
            // then
            assertThat(moved).isEqualTo(DEFAULT.move(Side.HAN.leftBackDelta()));
        }
    }
}