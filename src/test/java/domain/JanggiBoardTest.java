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
    void 장기_기물의_초기_위치를_저장한다(Position position, Piece piece) {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when & then
        assertThat(janggiBoard.getPieceFrom(position)).isInstanceOf(piece.getClass());
    }

    private static Stream<Arguments> providePlaceAndPiece() {
        return Stream.of(
                Arguments.of(new Position(9, 5), new 궁(Side.초)),
                Arguments.of(new Position(0, 1), new 차(Side.초)),
                Arguments.of(new Position(0, 9), new 차(Side.초)),
                Arguments.of(new Position(8, 2), new 포(Side.초)),
                Arguments.of(new Position(8, 8), new 포(Side.초)),
                Arguments.of(new Position(7, 1), new 졸병(Side.초)),
                Arguments.of(new Position(7, 3), new 졸병(Side.초)),
                Arguments.of(new Position(7, 5), new 졸병(Side.초)),
                Arguments.of(new Position(7, 7), new 졸병(Side.초)),
                Arguments.of(new Position(7, 9), new 졸병(Side.초)),
                Arguments.of(new Position(0, 4), new 사(Side.초)),
                Arguments.of(new Position(0, 6), new 사(Side.초)),
                Arguments.of(new Position(0, 2), new 마(Side.초)),
                Arguments.of(new Position(0, 8), new 마(Side.초)),
                Arguments.of(new Position(0, 3), new 상(Side.초)),
                Arguments.of(new Position(0, 7), new 상(Side.초)),
                Arguments.of(new Position(2, 5), new 궁(Side.한)),
                Arguments.of(new Position(1, 1), new 차(Side.한)),
                Arguments.of(new Position(1, 9), new 차(Side.한)),
                Arguments.of(new Position(3, 2), new 포(Side.한)),
                Arguments.of(new Position(3, 8), new 포(Side.한)),
                Arguments.of(new Position(4, 1), new 졸병(Side.한)),
                Arguments.of(new Position(4, 3), new 졸병(Side.한)),
                Arguments.of(new Position(4, 5), new 졸병(Side.한)),
                Arguments.of(new Position(4, 7), new 졸병(Side.한)),
                Arguments.of(new Position(4, 9), new 졸병(Side.한)),
                Arguments.of(new Position(1, 4), new 사(Side.한)),
                Arguments.of(new Position(1, 6), new 사(Side.한)),
                Arguments.of(new Position(1, 2), new 마(Side.한)),
                Arguments.of(new Position(1, 8), new 마(Side.한)),
                Arguments.of(new Position(1, 3), new 상(Side.한)),
                Arguments.of(new Position(1, 7), new 상(Side.한))
        );
    }

    @Test
    void 보드판_밖을_벗어나면_예외를_발생시킨다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when & then
        assertThatThrownBy(() -> janggiBoard.move(new Position(9, 8), new Position(0, 10)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 기물을_이동하며_마주치는_장애물을_확인할_수_있다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when & then
        assertThatThrownBy(() -> janggiBoard.move(new Position(0, 1), new Position(6, 1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    class 기물을_이동시킬_수_있다 {
        @Test
        void 궁을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(new Position(9, 5), new Position(8, 5));

            // then
            assertThat(janggiBoard.getPieceFrom(new Position(8, 5))).isInstanceOf(궁.class);
        }

        @Test
        void 마를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(new Position(0, 2), new Position(8, 3));

            // then
            assertThat(janggiBoard.getPieceFrom(new Position(8, 3))).isInstanceOf(마.class);
        }

        @Test
        void 사를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(new Position(0, 4), new Position(9, 4));

            // then
            assertThat(janggiBoard.getPieceFrom(new Position(9, 4))).isInstanceOf(사.class);
        }

        @Test
        void 상을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(new Position(7, 5), new Position(6, 5));
            janggiBoard.move(new Position(0, 3), new Position(7, 5));

            // then
            assertThat(janggiBoard.getPieceFrom(new Position(7, 5))).isInstanceOf(상.class);
        }

        @Test
        void 졸을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(new Position(7, 1), new Position(6, 1));

            // then
            assertThat(janggiBoard.getPieceFrom(new Position(6, 1))).isInstanceOf(졸병.class);
        }

        @Test
        void 병을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(new Position(4, 1), new Position(5, 1));

            // then
            assertThat(janggiBoard.getPieceFrom(new Position(5, 1))).isInstanceOf(졸병.class);
        }

        @Test
        void 차를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(new Position(7, 9), new Position(7, 8));
            janggiBoard.move(new Position(0, 9), new Position(5, 9));

            // then
            assertThat(janggiBoard.getPieceFrom(new Position(5, 9))).isInstanceOf(차.class);
        }

        @Test
        void 차를_오른쪽으로_이동시킬_수_있다() {
            // given

            JanggiBoard janggiBoard = new JanggiBoard();
            janggiBoard.move(new Position(0, 2), new Position(8, 3));

            // when
            janggiBoard.move(new Position(0, 1), new Position(0, 2));

            // then
            assertThat(janggiBoard.getPieceFrom(new Position(0, 2))).isInstanceOf(차.class);
        }

        @Test
        void 포는_기물을_하나_뛰어넘어서_이동할_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(new Position(4, 3), new Position(4, 4));
            janggiBoard.move(new Position(1, 2), new Position(3, 3));
            janggiBoard.move(new Position(3, 3), new Position(5, 2));

            janggiBoard.move(new Position(8, 2), new Position(4, 2));

            // then
            assertThat(janggiBoard.getPieceFrom(new Position(4, 2))).isInstanceOf(포.class);
        }
    }

    @Test
    void 포는_포를_뛰어넘을_수_없다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when & then
        assertThatThrownBy(() -> janggiBoard.move(new Position(8, 8), new Position(2, 8)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 포는_기물을_두_개_이상_뛰어넘을_수_없다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when
        janggiBoard.move(new Position(9, 5), new Position(8, 5));
        janggiBoard.move(new Position(8, 8), new Position(8, 3));

        // then
        assertThatThrownBy(() -> janggiBoard.move(new Position(8, 3), new Position(3, 3)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 기물은_다른_기물을_잡아서_잡힌_기물의_상태를_바꿀_수_있다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when
        janggiBoard.move(new Position(7, 7), new Position(6, 7));
        janggiBoard.move(new Position(6, 7), new Position(5, 7));

        Piece pieceInDanger = janggiBoard.getPieceFrom(new Position(4, 7));
        janggiBoard.move(new Position(5, 7), new Position(4, 7));

        // then
        assertThat(pieceInDanger.getStatus()).isEqualTo(PieceStatus.CAPTURED);
    }

    @Test
    void 포는_포를_잡을_수_없다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when
        janggiBoard.move(new Position(7, 3), new Position(7, 2));

        // then
        assertThatThrownBy(() -> janggiBoard.move(new Position(8, 2), new Position(3, 2)))
                .isInstanceOf(IllegalStateException.class);
    }
}
