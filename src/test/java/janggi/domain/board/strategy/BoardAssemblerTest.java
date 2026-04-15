package janggi.domain.board.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.board.strategy.ArrangementStrategy;
import janggi.domain.board.strategy.BoardAssembler;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardAssemblerTest {

    @Test
    @DisplayName("조립기는 전략을 실행한 후, 기물이 없는 나머지 빈 칸을 EmptyPiece로 채운다.")
    void shouldFillEmptySpaces() {
        // given
        ArrangementStrategy doNothingStrategy = (arrangement, pieceFactory) -> {};

        BoardAssembler assembler = BoardAssembler.from(List.of(doNothingStrategy, doNothingStrategy));

        // when
        Piece[][] board = assembler.assemble();

        // then
        assertThat(board[0][0]).isInstanceOf(EmptyPiece.class);
        assertThat(board[5][5]).isInstanceOf(EmptyPiece.class);
    }
}
