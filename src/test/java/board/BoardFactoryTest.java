package board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardFactoryTest {

    @Nested
    @DisplayName("보드 초기화 생성")
    class Construct {

        @DisplayName("보드 사이즈가 올바르게 생성된다.")
        @Test
        void boardFactory() {
            // given
            final int expected = 32;
            final BoardFactory factory = new BoardFactory();

            // when
            final Board board = factory.generateBoard();

            // then
            Assertions.assertThat(board.getMap()).hasSize(expected);
        }
    }
}
