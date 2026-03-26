package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.strategy.BoardAssembler;
import janggi.strategy.MaSangMaSang;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("보드가 전략에 맞춰 정상적으로 생성된다.")
    void shouldReturnBoardWithSelectedArrangementStrategy() {
        BoardAssembler assembler = BoardAssembler.of(new MaSangMaSang(), new MaSangMaSang());
        Board board = Board.create(assembler);
        List<List<Piece>> pieces = board.to2DArray();

        Piece[][] assemble = assembler.assemble();

        for (int row = 0; row < assemble.length; row++) {
            for (int col = 0; col < assemble[row].length; col++) {
                Piece result = assemble[row][col];
                Piece expectedPiece = pieces.get(row).get(col);
                Assertions.assertThat(result).isInstanceOf(expectedPiece.getClass());
                Assertions.assertThat(result.isSameSide(expectedPiece)).isTrue();

            }
        }
    }
}
