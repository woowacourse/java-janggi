package board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PositionTest {


    @Nested
    @DisplayName("포지션 생성")
    class Construct {

        @DisplayName("포지션을 생성하면 주어진 값으로 초기화된다.")
        @Test
        void construct1() {
            // given
            final int x = 1;
            final int y = 1;

            // when
            final var p = new Position(x, y);

            // then
            assertSoftly(s -> {
                s.assertThat(p.x()).isEqualTo(x);
                s.assertThat(p.y()).isEqualTo(x);
            });
        }
    }
}
