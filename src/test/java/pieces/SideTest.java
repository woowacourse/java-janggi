package pieces;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SideTest {

    @Nested
    @DisplayName("한나라인지 여부를 반환한다")
    class IsHan {

        @Test
        void 한나라이면_참을_반환한다() {
            // given
            Side side = Side.HAN;
            // when & then
            assertThat(side.isHan()).isTrue();
        }

        @Test
        void 한나라가_아니면_거짓을_반환한다() {
            // given
            Side side = Side.CHO;
            // when & then
            assertThat(side.isHan()).isFalse();
        }
    }

    @Nested
    @DisplayName("초나라인지 여부를 반환한다")
    class IsCho {

        @Test
        void 초나라이면_참을_반환한다() {
            // given
            Side side = Side.CHO;
            // when & then
            assertThat(side.isCho()).isTrue();
        }

        @Test
        void 초나라가_아니면_거짓을_반환한다() {
            // given
            Side side = Side.HAN;
            // when & then
            assertThat(side.isCho()).isFalse();
        }
    }
}
