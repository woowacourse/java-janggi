package janggi.strategy;


import janggi.domain.Side;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Sang;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SangMaSangMaStrategyTest {

    private static final SangMaSangMaStrategy STRATEGY = SangMaSangMaStrategy.getInstance();

    @Test
    @DisplayName("Han 팀의 Sang 객체와 Ma 객체를 SangMaSangMa의 위치에 생성해 넣어준다.")
    void shouldPlaceSangMaSangMaWhenSideHan() {
        // given
        Piece[][] grid = new Piece[10][9];
        Side side = Side.HAN;

        // when
        STRATEGY.place(grid, side);
        Piece leftSang = grid[0][1];
        Piece leftMa = grid[0][2];
        Piece rightSang = grid[0][6];
        Piece rightMa = grid[0][7];

        // then
        Assertions.assertThat(leftSang).isInstanceOf(Sang.class);
        Assertions.assertThat(leftMa).isInstanceOf(Ma.class);
        Assertions.assertThat(rightSang).isInstanceOf(Sang.class);
        Assertions.assertThat(rightMa).isInstanceOf(Ma.class);

        Assertions.assertThat(leftSang.isSameSide(side)).isTrue();
        Assertions.assertThat(leftMa.isSameSide(side)).isTrue();
        Assertions.assertThat(rightSang.isSameSide(side)).isTrue();
        Assertions.assertThat(rightMa.isSameSide(side)).isTrue();
    }

    @Test
    @DisplayName("Cho 팀의 Sang 객체와 Ma 객체를 SangMaSangMa의 위치에 생성해 넣어준다.")
    void shouldPlaceSangMaSangMaWhenSideCho() {
        // given
        Piece[][] grid = new Piece[10][9];
        Side side = Side.CHO;

        // when
        STRATEGY.place(grid, side);
        Piece leftSang = grid[9][1];
        Piece leftMa = grid[9][2];
        Piece rightSang = grid[9][6];
        Piece rightMa = grid[9][7];

        // then
        Assertions.assertThat(leftSang).isInstanceOf(Sang.class);
        Assertions.assertThat(leftMa).isInstanceOf(Ma.class);
        Assertions.assertThat(rightSang).isInstanceOf(Sang.class);
        Assertions.assertThat(rightMa).isInstanceOf(Ma.class);

        Assertions.assertThat(leftSang.isSameSide(side)).isTrue();
        Assertions.assertThat(leftMa.isSameSide(side)).isTrue();
        Assertions.assertThat(rightSang.isSameSide(side)).isTrue();
        Assertions.assertThat(rightMa.isSameSide(side)).isTrue();
    }
}
