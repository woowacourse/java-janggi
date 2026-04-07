package domain.board;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceTest {
    @Nested
    class 궁성_여부_테스트 {
        Position hanPalacePosition = new Position(3, 9);
        Position choPalacePosition = new Position(3, 2);

        @Test
        void 해당_좌표가_초의_궁성인지_반환한다() {
            assertThat(Palace.isChoPalace(choPalacePosition)).isTrue();
            assertThat(Palace.isChoPalace(hanPalacePosition)).isFalse();
        }

        @Test
        void 해당_좌표가_한의_궁성인지_반환한다() {
            assertThat(Palace.isHanPalace(hanPalacePosition)).isTrue();
            assertThat(Palace.isHanPalace(choPalacePosition)).isFalse();
        }
    }
}
