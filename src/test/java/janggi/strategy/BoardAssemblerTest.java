package janggi.strategy;

import janggi.domain.piece.Cha;
import janggi.domain.piece.Jol;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Sang;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardAssemblerTest {

    @Test
    @DisplayName("공통 기물과 각 팀의 전략이 합쳐져 전체 보드를 생성한다.")
    void shouldAssembleFullBoard() {
        // given
        BoardAssembler assembler = new BoardAssembler();
        ArrangementStrategy choStrategy = new MaSangMaSang(); // 마-상-마-상 (1,2,6,7)
        ArrangementStrategy hanStrategy = new SangMaMaSang(); // 상-마-마-상 (1,2,6,7 다름)

        // when
        Piece[][] board = assembler.assemble(choStrategy, hanStrategy);

        // then
        // 1. 공통 기물 검증 (샘플)
        Assertions.assertThat(board[0][0]).isInstanceOf(Cha.class); // 한 차
        Assertions.assertThat(board[9][8]).isInstanceOf(Cha.class); // 초 차
        Assertions.assertThat(board[3][0]).isInstanceOf(Jol.class); // 한 졸

        // 2. 전략 기물 검증 (전략이 실제로 동작했는지 확인)
        // 초(Side.CHO)는 9번 행에 배치됨
        Assertions.assertThat(board[9][1]).isInstanceOf(Ma.class);   // MaSangMaSang의 첫 번째 마
        Assertions.assertThat(board[9][2]).isInstanceOf(Sang.class); // MaSangMaSang의 첫 번째 상

        // 3. 빈 칸 검증 (기물이 없어야 할 곳)
        Assertions.assertThat(board[4][0]).isNull(); // 장기판 중간은 비어있어야 함
    }

}
