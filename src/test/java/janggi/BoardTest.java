package janggi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Nested
    @DisplayName("장기 판 초기화 테스트")
    class InitBoardTest {

        @Test
        @DisplayName("장기 말들을 가진 장기판을 생성할 수 있다.")
        void createBoard() {
            Board board = Board.init();

            assertThat(board.getPieces()).hasSize(32);
        }
    }
}
