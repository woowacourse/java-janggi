package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class JanggiBoardTest {

    @Test
    void _9_10_보드판을_생성할_수_있다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // then
        assertThat(janggiBoard.getJanggiBoard().size())
                .isEqualTo(90);
    }

    @ParameterizedTest
    @MethodSource("providePlaceAndPiece")
    void 장기_기물의_초기_위치를_저장한다(int row, int column, Piece piece) {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when & then
        assertThat(janggiBoard.getPieceFrom(row, column)).isInstanceOf(piece.getClass());
    }

    private static Stream<Arguments> providePlaceAndPiece() {
        return Stream.of(
                Arguments.of(9, 5, new 궁(Side.초)),
                Arguments.of(0, 1, new 차(Side.초)),
                Arguments.of(0, 9, new 차(Side.초)),
                Arguments.of(8, 2, new 포(Side.초)),
                Arguments.of(8, 8, new 포(Side.초)),
                Arguments.of(7, 1, new 졸병(Side.초)),
                Arguments.of(7, 3, new 졸병(Side.초)),
                Arguments.of(7, 5, new 졸병(Side.초)),
                Arguments.of(7, 7, new 졸병(Side.초)),
                Arguments.of(7, 9, new 졸병(Side.초)),
                Arguments.of(0, 4, new 사(Side.초)),
                Arguments.of(0, 6, new 사(Side.초)),
                Arguments.of(0, 2, new 마(Side.초)),
                Arguments.of(0, 8, new 마(Side.초)),
                Arguments.of(0, 3, new 상(Side.초)),
                Arguments.of(0, 7, new 상(Side.초)),
                Arguments.of(2, 5, new 궁(Side.한)),
                Arguments.of(1, 1, new 차(Side.한)),
                Arguments.of(1, 9, new 차(Side.한)),
                Arguments.of(3, 2, new 포(Side.한)),
                Arguments.of(3, 8, new 포(Side.한)),
                Arguments.of(4, 1, new 졸병(Side.한)),
                Arguments.of(4, 3, new 졸병(Side.한)),
                Arguments.of(4, 5, new 졸병(Side.한)),
                Arguments.of(4, 7, new 졸병(Side.한)),
                Arguments.of(4, 9, new 졸병(Side.한)),
                Arguments.of(1, 4, new 사(Side.한)),
                Arguments.of(1, 6, new 사(Side.한)),
                Arguments.of(1, 2, new 마(Side.한)),
                Arguments.of(1, 8, new 마(Side.한)),
                Arguments.of(1, 3, new 상(Side.한)),
                Arguments.of(1, 7, new 상(Side.한))
        );
    }

    @Test
    void 보드판_밖을_벗어나면_예외를_발생시킨다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when & then
        assertThatThrownBy(() -> janggiBoard.move(9, 8, 0, 10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 기물을_이동하며_마주치는_장애물을_확인할_수_있다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when & then
        assertThatThrownBy(() -> janggiBoard.move(0, 1, 6, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    class 기물을_이동시킬_수_있다 {
        @Test
        void 궁을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(9, 5, 8, 5);

            // then
            assertThat(janggiBoard.getPieceFrom(8, 5)).isInstanceOf(궁.class);
        }

        @Test
        void 마를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(0, 2, 8, 3);

            // then
            assertThat(janggiBoard.getPieceFrom(8, 3)).isInstanceOf(마.class);
        }

        @Test
        void 사를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(0, 4, 9, 4);

            // then
            assertThat(janggiBoard.getPieceFrom(9, 4)).isInstanceOf(사.class);
        }

        @Test
        void 상을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(0, 3, 7, 5);

            // then
            assertThat(janggiBoard.getPieceFrom(7, 5)).isInstanceOf(상.class);
        }

        @Test
        void 졸을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(7, 1, 6, 1);

            // then
            assertThat(janggiBoard.getPieceFrom(6, 1)).isInstanceOf(졸병.class);
        }

        @Test
        void 병을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(4, 1, 5, 1);

            // then
            assertThat(janggiBoard.getPieceFrom(5, 1)).isInstanceOf(졸병.class);
        }

        @Test
        void 차를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(0, 9, 4, 9);

            // then
            assertThat(janggiBoard.getPieceFrom(4, 9)).isInstanceOf(차.class);
        }

        @Test
        void 차를_오른쪽으로_이동시킬_수_있다() {
            // given

            JanggiBoard janggiBoard = new JanggiBoard();
            janggiBoard.move(0, 2, 8, 3);

            // when
            janggiBoard.move(0, 1, 0, 2);

            // then
            assertThat(janggiBoard.getPieceFrom(0, 2)).isInstanceOf(차.class);
        }

        @Test
        void 포는_기물을_하나_뛰어넘어서_이동할_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(4, 3, 4, 2);
            janggiBoard.move(1, 2, 3, 3);
            janggiBoard.move(3, 3, 5, 2);

            janggiBoard.move(8, 2, 4, 2);

            // then
            assertThat(janggiBoard.getPieceFrom(4, 2)).isInstanceOf(포.class);
        }
    }

    @Test
    void 포는_포를_뛰어넘을_수_없다() {

    }

    @Test
    void 포는_기물을_두_개_이상_뛰어넘을_수_없다() {

    }
}
