package janggi.strategy;


import janggi.domain.Side;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Sang;
import janggi.domain.strategy.arrangement.ArrangementStrategy;
import janggi.domain.strategy.arrangement.MaSangMaSang;
import janggi.support.TestPiece;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MaSangMaSangTest {

    @Test
    @DisplayName("Han 팀의 Sang 객체와 Ma 객체를 MaSangMaSang의 위치에 생성해 넣어준다.")
    void shouldPlaceMaSangMaSangWhenSideHan() {
        // given
        Piece[][] grid = new Piece[10][9];
        Side side = Side.HAN;
        ArrangementStrategy strategy = new MaSangMaSang(side);
        Piece expectedSidePiece = new TestPiece(side);

        // when
        strategy.place(grid);
        Piece leftMa = grid[0][1];
        Piece leftSang = grid[0][2];
        Piece rightMa = grid[0][6];
        Piece rightSang = grid[0][7];

        // then
        Assertions.assertThat(leftMa).isInstanceOf(Ma.class);
        Assertions.assertThat(leftSang).isInstanceOf(Sang.class);
        Assertions.assertThat(rightMa).isInstanceOf(Ma.class);
        Assertions.assertThat(rightSang).isInstanceOf(Sang.class);

        Assertions.assertThat(leftMa.isSameSide(expectedSidePiece)).isTrue();
        Assertions.assertThat(leftSang.isSameSide(expectedSidePiece)).isTrue();
        Assertions.assertThat(rightMa.isSameSide(expectedSidePiece)).isTrue();
        Assertions.assertThat(rightSang.isSameSide(expectedSidePiece)).isTrue();
    }

    @Test
    @DisplayName("Cho 팀의 Sang 객체와 Ma 객체를 MaSangMaSang의 위치에 생성해 넣어준다.")
    void shouldPlaceMaSangMaSangWhenSideCho() {
        // given
        Piece[][] grid = new Piece[10][9];
        Side side = Side.CHO;
        ArrangementStrategy strategy = new MaSangMaSang(side);
        Piece expectedSidePiece = new TestPiece(side);

        // when
        strategy.place(grid);
        Piece leftMa = grid[9][1];
        Piece leftSang = grid[9][2];
        Piece rightMa = grid[9][6];
        Piece rightSang = grid[9][7];

        // then
        Assertions.assertThat(leftMa).isInstanceOf(Ma.class);
        Assertions.assertThat(leftSang).isInstanceOf(Sang.class);
        Assertions.assertThat(rightMa).isInstanceOf(Ma.class);
        Assertions.assertThat(rightSang).isInstanceOf(Sang.class);

        Assertions.assertThat(leftMa.isSameSide(expectedSidePiece)).isTrue();
        Assertions.assertThat(leftSang.isSameSide(expectedSidePiece)).isTrue();
        Assertions.assertThat(rightMa.isSameSide(expectedSidePiece)).isTrue();
        Assertions.assertThat(rightSang.isSameSide(expectedSidePiece)).isTrue();
    }
}
