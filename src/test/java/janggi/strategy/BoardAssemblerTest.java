package janggi.strategy;

import janggi.domain.Intersection;
import janggi.domain.Side;
import janggi.domain.piece.PieceType;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardAssemblerTest {

    @Test
    @DisplayName("공통 기물과 각 팀의 전략이 합쳐져 전체 보드를 생성한다.")
    void shouldAssembleFullBoard() {
        // given
        List<ArrangementStrategy> arrangementStrategies = List.of(
                new SangMaMaSang(Side.HAN),
                new MaSangMaSang(Side.CHO)
        );
        IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
        BoardAssembler assembler = BoardAssembler.of(arrangementStrategies, intersectionInitializer);

        // when
        Intersection[][] board = assembler.assemble();

        // then
        Assertions.assertThat(board[0][0].hasPiece(PieceType.CHA)).isTrue();
        Assertions.assertThat(board[9][8].hasPiece(PieceType.CHA)).isTrue();
        Assertions.assertThat(board[3][0].hasPiece(PieceType.JOLBYEOUNG)).isTrue();
        Assertions.assertThat(board[9][1].hasPiece(PieceType.MA)).isTrue();
        Assertions.assertThat(board[9][2].hasPiece(PieceType.SANG)).isTrue();
        Assertions.assertThat(board[4][0].hasPiece(PieceType.EMPTY)).isTrue();

        Assertions.assertThat(board[1][4].isPalace()).isTrue();
        Assertions.assertThat(board[0][3].isPalace()).isTrue();
        Assertions.assertThat(board[2][5].isPalace()).isTrue();
        Assertions.assertThat(board[8][4].isPalace()).isTrue();
        Assertions.assertThat(board[7][3].isPalace()).isTrue();
        Assertions.assertThat(board[9][5].isPalace()).isTrue();
    }

}
