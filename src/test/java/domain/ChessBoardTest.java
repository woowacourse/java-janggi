package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ChessBoardTest {

    @Test
    void _9_10_보드판을_생성할_수_있다() {
        // given
        ChessBoard chessBoard = new ChessBoard();

        // then
        assertThat(chessBoard.getBoard().size())
                .isEqualTo(90);
    }
}
