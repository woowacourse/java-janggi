package domain.board;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SelectedPositionsTest {

    @Nested
    class Invalid {

        @DisplayName("이동하는 기물의 위치와 이동시키려는 위치가 동일하면 예외가 발생한다.")
        @Test
        void create() {
            assertThatThrownBy(() -> new SelectedPositions(new BoardPosition(0, 0), new BoardPosition(0, 0)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("기물을 같은 위치로 이동시킬 수 없습니다.");
        }
    }
}
