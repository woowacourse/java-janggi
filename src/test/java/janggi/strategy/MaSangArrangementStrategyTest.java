package janggi.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.strategy.MaSangArrangementStrategy;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MaSangArrangementStrategyTest {

    private static final List<PieceType> MA_SANG_MA_SANG =
            List.of(PieceType.MA, PieceType.SANG, PieceType.MA, PieceType.SANG);
    private static final List<PieceType> SANG_MA_SANG_MA =
            List.of(PieceType.SANG, PieceType.MA, PieceType.SANG, PieceType.MA);
    private static final List<PieceType> SANG_MA_MA_SANG =
            List.of(PieceType.SANG, PieceType.MA, PieceType.MA, PieceType.SANG);
    private static final List<PieceType> MA_SANG_SANG_MA =
            List.of(PieceType.MA, PieceType.SANG, PieceType.SANG, PieceType.MA);

    @Test
    @DisplayName("전략의 place를 호출하면 차, 포, 궁, 사, 졸 등 '기본 기물'이 올바른 위치에 배치된다.")
    void shouldPlaceDefaultPieces() {
        // given
        Piece[][] board = new Piece[10][9];
        Side han = Side.HAN;
        Side cho = Side.CHO;
        MaSangArrangementStrategy hanStrategy = MaSangArrangementStrategy.of(han, MA_SANG_MA_SANG);
        MaSangArrangementStrategy choStrategy = MaSangArrangementStrategy.of(cho, MA_SANG_MA_SANG);

        // when
        hanStrategy.place(board);
        choStrategy.place(board);

        // then: 한팀
        Piece chaOfHan = board[0][0];
        assertThat(chaOfHan.getPieceType()).isEqualTo(PieceType.CHA);
        assertThat(chaOfHan.isSameSide(han)).isTrue();

        Piece saOfHan = board[0][3];
        assertThat(saOfHan.getPieceType()).isEqualTo(PieceType.SA);
        assertThat(saOfHan.isSameSide(han)).isTrue();

        Piece gungOfHan = board[1][4];
        assertThat(gungOfHan.getPieceType()).isEqualTo(PieceType.GUNG);
        assertThat(gungOfHan.isSameSide(han)).isTrue();

        Piece poOfHan = board[2][1];
        assertThat(poOfHan.getPieceType()).isEqualTo(PieceType.PO);
        assertThat(poOfHan.isSameSide(han)).isTrue();

        Piece jolbyeongOfHan = board[3][0];
        assertThat(jolbyeongOfHan.getPieceType()).isEqualTo(PieceType.BYEONG);
        assertThat(jolbyeongOfHan.isSameSide(han)).isTrue();


        // then: 초팀
        Piece chaOfCho = board[9][8];
        assertThat(chaOfCho.getPieceType()).isEqualTo(PieceType.CHA);
        assertThat(chaOfCho.isSameSide(cho)).isTrue();

        Piece gungOfCho = board[8][4];
        assertThat(gungOfCho.getPieceType()).isEqualTo(PieceType.GUNG);
        assertThat(gungOfCho.isSameSide(cho)).isTrue();

        Piece poOfCho = board[7][7];
        assertThat(poOfCho.getPieceType()).isEqualTo(PieceType.PO);
        assertThat(poOfCho.isSameSide(cho)).isTrue();

        Piece jolbyeongOfCho = board[6][8];
        assertThat(jolbyeongOfCho.getPieceType()).isEqualTo(PieceType.JOL);
        assertThat(jolbyeongOfCho.isSameSide(cho)).isTrue();
    }

    @Nested
    class MaSangMaSangTest {
        @Test
        @DisplayName("Han 팀의 Sang 객체와 Ma 객체를 MaSangMaSang의 위치에 생성해 넣어준다.")
        void shouldPlaceMaSangMaSangWhenSideHan() {
            // given
            Piece[][] grid = new Piece[10][9];
            Side side = Side.HAN;
            MaSangArrangementStrategy strategy = MaSangArrangementStrategy.of(side, MA_SANG_MA_SANG);

            // when
            strategy.place(grid);
            Piece leftMa = grid[0][1];
            Piece leftSang = grid[0][2];
            Piece rightMa = grid[0][6];
            Piece rightSang = grid[0][7];

            // then
            Assertions.assertThat(leftMa.getPieceType()).isEqualTo(PieceType.MA);
            Assertions.assertThat(leftSang.getPieceType()).isEqualTo(PieceType.SANG);
            Assertions.assertThat(rightMa.getPieceType()).isEqualTo(PieceType.MA);
            Assertions.assertThat(rightSang.getPieceType()).isEqualTo(PieceType.SANG);

            Assertions.assertThat(leftMa.isSameSide(side)).isTrue();
            Assertions.assertThat(leftSang.isSameSide(side)).isTrue();
            Assertions.assertThat(rightMa.isSameSide(side)).isTrue();
            Assertions.assertThat(rightSang.isSameSide(side)).isTrue();
        }

        @Test
        @DisplayName("Cho 팀의 Sang 객체와 Ma 객체를 MaSangMaSang의 위치에 생성해 넣어준다.")
        void shouldPlaceMaSangMaSangWhenSideCho() {
            // given
            Piece[][] grid = new Piece[10][9];
            Side side = Side.CHO;
            MaSangArrangementStrategy strategy = MaSangArrangementStrategy.of(side, MA_SANG_MA_SANG);

            // when
            strategy.place(grid);
            Piece leftMa = grid[9][1];
            Piece leftSang = grid[9][2];
            Piece rightMa = grid[9][6];
            Piece rightSang = grid[9][7];

            // then
            Assertions.assertThat(leftMa.getPieceType()).isEqualTo(PieceType.MA);
            Assertions.assertThat(leftSang.getPieceType()).isEqualTo(PieceType.SANG);
            Assertions.assertThat(rightMa.getPieceType()).isEqualTo(PieceType.MA);
            Assertions.assertThat(rightSang.getPieceType()).isEqualTo(PieceType.SANG);

            Assertions.assertThat(leftMa.isSameSide(side)).isTrue();
            Assertions.assertThat(leftSang.isSameSide(side)).isTrue();
            Assertions.assertThat(rightMa.isSameSide(side)).isTrue();
            Assertions.assertThat(rightSang.isSameSide(side)).isTrue();
        }
    }

    @Nested
    class SangMaSangMaTest {
        @Test
        @DisplayName("Han 팀의 Sang 객체와 Ma 객체를 SangMaSangMa의 위치에 생성해 넣어준다.")
        void shouldPlaceSangMaSangMaWhenSideHan() {
            // given
            Piece[][] grid = new Piece[10][9];
            Side side = Side.HAN;
            MaSangArrangementStrategy strategy = MaSangArrangementStrategy.of(side, SANG_MA_SANG_MA);

            // when
            strategy.place(grid);
            Piece leftSang = grid[0][1];
            Piece leftMa = grid[0][2];
            Piece rightSang = grid[0][6];
            Piece rightMa = grid[0][7];

            // then
            Assertions.assertThat(leftSang.getPieceType()).isEqualTo(PieceType.SANG);
            Assertions.assertThat(leftMa.getPieceType()).isEqualTo(PieceType.MA);
            Assertions.assertThat(rightSang.getPieceType()).isEqualTo(PieceType.SANG);
            Assertions.assertThat(rightMa.getPieceType()).isEqualTo(PieceType.MA);

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
            MaSangArrangementStrategy strategy = MaSangArrangementStrategy.of(side, SANG_MA_SANG_MA);

            // when
            strategy.place(grid);
            Piece leftSang = grid[9][1];
            Piece leftMa = grid[9][2];
            Piece rightSang = grid[9][6];
            Piece rightMa = grid[9][7];

            // then
            Assertions.assertThat(leftSang.getPieceType()).isEqualTo(PieceType.SANG);
            Assertions.assertThat(leftMa.getPieceType()).isEqualTo(PieceType.MA);
            Assertions.assertThat(rightSang.getPieceType()).isEqualTo(PieceType.SANG);
            Assertions.assertThat(rightMa.getPieceType()).isEqualTo(PieceType.MA);

            Assertions.assertThat(leftSang.isSameSide(side)).isTrue();
            Assertions.assertThat(leftMa.isSameSide(side)).isTrue();
            Assertions.assertThat(rightSang.isSameSide(side)).isTrue();
            Assertions.assertThat(rightMa.isSameSide(side)).isTrue();
        }
    }

    @Nested
    class SangMaMaSangTest {
        @Test
        @DisplayName("Han 팀의 Sang 객체와 Ma 객체를 SangMaMaSang의 위치에 생성해 넣어준다.")
        void shouldPlaceSangMaMaSangWhenSideHan() {
            // given
            Piece[][] grid = new Piece[10][9];
            Side side = Side.HAN;
            MaSangArrangementStrategy strategy = MaSangArrangementStrategy.of(side, SANG_MA_MA_SANG);

            // when
            strategy.place(grid);
            Piece leftSang = grid[0][1];
            Piece leftMa = grid[0][2];
            Piece rightMa = grid[0][6];
            Piece rightSang = grid[0][7];

            // then
            Assertions.assertThat(leftSang.getPieceType()).isEqualTo(PieceType.SANG);
            Assertions.assertThat(leftMa.getPieceType()).isEqualTo(PieceType.MA);
            Assertions.assertThat(rightMa.getPieceType()).isEqualTo(PieceType.MA);
            Assertions.assertThat(rightSang.getPieceType()).isEqualTo(PieceType.SANG);

            Assertions.assertThat(leftSang.isSameSide(side)).isTrue();
            Assertions.assertThat(leftMa.isSameSide(side)).isTrue();
            Assertions.assertThat(rightMa.isSameSide(side)).isTrue();
            Assertions.assertThat(rightSang.isSameSide(side)).isTrue();
        }

        @Test
        @DisplayName("Cho 팀의 Sang 객체와 Ma 객체를 SangMaMaSang의 위치에 생성해 넣어준다.")
        void shouldPlaceSangMaMaSangWhenSideCho() {
            // given
            Piece[][] grid = new Piece[10][9];
            Side side = Side.CHO;
            MaSangArrangementStrategy strategy = MaSangArrangementStrategy.of(side, SANG_MA_MA_SANG);

            // when
            strategy.place(grid);
            Piece leftSang = grid[9][1];
            Piece leftMa = grid[9][2];
            Piece rightMa = grid[9][6];
            Piece rightSang = grid[9][7];

            // then
            Assertions.assertThat(leftSang.getPieceType()).isEqualTo(PieceType.SANG);
            Assertions.assertThat(leftMa.getPieceType()).isEqualTo(PieceType.MA);
            Assertions.assertThat(rightMa.getPieceType()).isEqualTo(PieceType.MA);
            Assertions.assertThat(rightSang.getPieceType()).isEqualTo(PieceType.SANG);

            Assertions.assertThat(leftSang.isSameSide(side)).isTrue();
            Assertions.assertThat(leftMa.isSameSide(side)).isTrue();
            Assertions.assertThat(rightMa.isSameSide(side)).isTrue();
            Assertions.assertThat(rightSang.isSameSide(side)).isTrue();
        }
    }

    @Nested
    class MaSangSangMaTest {
        @Test
        @DisplayName("Han 팀의 Sang 객체와 Ma 객체를 MaSangSangMa의 위치에 생성해 넣어준다.")
        void shouldPlaceMaSangSangMaWhenSideHan() {
            // given
            Piece[][] grid = new Piece[10][9];
            Side side = Side.HAN;
            MaSangArrangementStrategy strategy = MaSangArrangementStrategy.of(side, MA_SANG_SANG_MA);

            // when
            strategy.place(grid);
            Piece leftMa = grid[0][1];
            Piece leftSang = grid[0][2];
            Piece rightSang = grid[0][6];
            Piece rightMa = grid[0][7];

            // then
            Assertions.assertThat(leftMa.getPieceType()).isEqualTo(PieceType.MA);
            Assertions.assertThat(leftSang.getPieceType()).isEqualTo(PieceType.SANG);
            Assertions.assertThat(rightSang.getPieceType()).isEqualTo(PieceType.SANG);
            Assertions.assertThat(rightMa.getPieceType()).isEqualTo(PieceType.MA);

            Assertions.assertThat(leftMa.isSameSide(side)).isTrue();
            Assertions.assertThat(leftSang.isSameSide(side)).isTrue();
            Assertions.assertThat(rightSang.isSameSide(side)).isTrue();
            Assertions.assertThat(rightMa.isSameSide(side)).isTrue();
        }

        @Test
        @DisplayName("Cho 팀의 Sang 객체와 Ma 객체를 MaSangSangMa의 위치에 생성해 넣어준다.")
        void shouldPlaceMaSangSangMaWhenSideCho() {
            // given
            Piece[][] grid = new Piece[10][9];
            Side side = Side.CHO;
            MaSangArrangementStrategy strategy = MaSangArrangementStrategy.of(side, MA_SANG_SANG_MA);

            // when
            strategy.place(grid);
            Piece leftMa = grid[9][1];
            Piece leftSang = grid[9][2];
            Piece rightSang = grid[9][6];
            Piece rightMa = grid[9][7];

            // then
            Assertions.assertThat(leftMa.getPieceType()).isEqualTo(PieceType.MA);
            Assertions.assertThat(leftSang.getPieceType()).isEqualTo(PieceType.SANG);
            Assertions.assertThat(rightSang.getPieceType()).isEqualTo(PieceType.SANG);
            Assertions.assertThat(rightMa.getPieceType()).isEqualTo(PieceType.MA);

            Assertions.assertThat(leftMa.isSameSide(side)).isTrue();
            Assertions.assertThat(leftSang.isSameSide(side)).isTrue();
            Assertions.assertThat(rightSang.isSameSide(side)).isTrue();
            Assertions.assertThat(rightMa.isSameSide(side)).isTrue();
        }
    }
}
