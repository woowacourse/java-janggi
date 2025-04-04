package board;

import domain.board.Board;
import domain.board.BoardFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import domain.piece.Country;
import domain.position.LineDirection;

public class BoardFactoryTest {

    @Nested
    @DisplayName("보드 초기화 생성")
    class Construct {

        @DisplayName("보드 사이즈가 올바르게 생성된다.")
        @Test
        void generateBoard() {
            // given
            final int expected = 32;
            final BoardFactory factory = new BoardFactory(Country.HAN, LineDirection.UP);
            // when

            final Board board = factory.generateBoard();

            // then
            Assertions.assertThat(board.getPieceMap()).hasSize(expected);
        }
    }
}
