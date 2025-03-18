package janggi;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.piece.Piece;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BoardTest {

    @Nested
    @DisplayName("보드 초기화 테스트")
    class InitBoardTest {

        @Test
        @DisplayName("보드가 초기화 되면 32개의 기물을 가지고 있다.")
        void create32PiecesWhenStart() {
            // when
            Board board = Board.init();

            // then
            int expected = 32;
            assertThat(board.getBoard()).hasSize(expected);
        }

        @ParameterizedTest
        @DisplayName("보드가 초기화 되면 2개의 진영이 16개의 기물을 가지고 있다.")
        @CsvSource(value = {"RED", "BLUE"})
        void createEachSide16PiecesWhenStart(Side side) {
            // when
            Board board = Board.init();

            // then
            List<Piece> pieces = board.getBoard()
                    .values()
                    .stream()
                    .toList();
            List<Piece> piecesOfSide = pieces.stream()
                    .filter(piece -> piece.getSide() == side)
                    .toList();
            int expected = 16;
            assertThat(piecesOfSide).hasSize(expected);
        }
    }
}
