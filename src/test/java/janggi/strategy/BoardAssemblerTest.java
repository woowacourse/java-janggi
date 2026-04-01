package janggi.strategy;

import janggi.domain.Side;
import janggi.domain.piece.Cha;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Jolbyeong;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Sang;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardAssemblerTest {

    @Test
    @DisplayName("공통 기물과 각 팀의 전략이 합쳐져 전체 보드를 생성한다.")
    void shouldAssembleFullBoard() {
        // given
        List<ArrangementStrategy> arrangementStrategies = List.of(new SangMaMaSang(Side.HAN),
                new MaSangMaSang(Side.CHO));
        BoardAssembler assembler = BoardAssembler.from(arrangementStrategies);

        // when
        Piece[][] board = assembler.assemble();

        // then
        // 1. 공통 기물 검증
        Assertions.assertThat(board[0][0]).isInstanceOf(Cha.class);
        Assertions.assertThat(board[9][8]).isInstanceOf(Cha.class);
        Assertions.assertThat(board[3][0]).isInstanceOf(Jolbyeong.class);

        // 2. 전략 기물 검증 (전략이 실제로 동작했는지 확인)
        Assertions.assertThat(board[9][1]).isInstanceOf(Ma.class);   // MaSangMaSang의 첫 번째 마
        Assertions.assertThat(board[9][2]).isInstanceOf(Sang.class); // MaSangMaSang의 첫 번째 상

        Assertions.assertThat(board[4][0]).isInstanceOf(EmptyPiece.class);
    }

}
