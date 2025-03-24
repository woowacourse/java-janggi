package domain;

import static domain.Fixtures._EIGHT_EIGHT;
import static domain.Fixtures._EIGHT_FIVE;
import static domain.Fixtures._EIGHT_THREE;
import static domain.Fixtures._EIGHT_TWO;
import static domain.Fixtures._FIVE_NINE;
import static domain.Fixtures._FIVE_ONE;
import static domain.Fixtures._FIVE_SEVEN;
import static domain.Fixtures._FIVE_TWO;
import static domain.Fixtures._FOUR_FOUR;
import static domain.Fixtures._FOUR_ONE;
import static domain.Fixtures._FOUR_SEVEN;
import static domain.Fixtures._FOUR_THREE;
import static domain.Fixtures._FOUR_TWO;
import static domain.Fixtures._NINE_FIVE;
import static domain.Fixtures._NINE_FOUR;
import static domain.Fixtures._ONE_TWO;
import static domain.Fixtures._SEVEN_EIGHT;
import static domain.Fixtures._SEVEN_FIVE;
import static domain.Fixtures._SEVEN_NINE;
import static domain.Fixtures._SEVEN_ONE;
import static domain.Fixtures._SEVEN_SEVEN;
import static domain.Fixtures._SEVEN_THREE;
import static domain.Fixtures._SEVEN_TWO;
import static domain.Fixtures._SIX_FIVE;
import static domain.Fixtures._SIX_ONE;
import static domain.Fixtures._SIX_SEVEN;
import static domain.Fixtures._THREE_THREE;
import static domain.Fixtures._THREE_TWO;
import static domain.Fixtures._TWO_EIGHT;
import static domain.Fixtures._ZERO_FOUR;
import static domain.Fixtures._ZERO_NINE;
import static domain.Fixtures._ZERO_ONE;
import static domain.Fixtures._ZERO_THREE;
import static domain.Fixtures._ZERO_TWO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Piece;
import domain.piece.Side;
import domain.piece.state.Captured;
import domain.piece.궁;
import domain.piece.마;
import domain.piece.사;
import domain.piece.상;
import domain.piece.졸병;
import domain.piece.차;
import domain.piece.포;
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
    void 장기_기물의_초기_위치를_저장한다(JanggiPosition position, Piece piece) {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when & then
        assertThat(janggiBoard.getPieceFrom(position)).isInstanceOf(piece.getClass());
    }

    private static Stream<Arguments> providePlaceAndPiece() {
        return Stream.of(
                Arguments.of(new JanggiPosition(9, 5), new 궁(Side.CHO)),
                Arguments.of(new JanggiPosition(0, 1), new 차(Side.CHO)),
                Arguments.of(new JanggiPosition(0, 9), new 차(Side.CHO)),
                Arguments.of(new JanggiPosition(8, 2), new 포(Side.CHO)),
                Arguments.of(new JanggiPosition(8, 8), new 포(Side.CHO)),
                Arguments.of(new JanggiPosition(7, 1), new 졸병(Side.CHO)),
                Arguments.of(new JanggiPosition(7, 3), new 졸병(Side.CHO)),
                Arguments.of(new JanggiPosition(7, 5), new 졸병(Side.CHO)),
                Arguments.of(new JanggiPosition(7, 7), new 졸병(Side.CHO)),
                Arguments.of(new JanggiPosition(7, 9), new 졸병(Side.CHO)),
                Arguments.of(new JanggiPosition(0, 4), new 사(Side.CHO)),
                Arguments.of(new JanggiPosition(0, 6), new 사(Side.CHO)),
                Arguments.of(new JanggiPosition(0, 2), new 마(Side.CHO)),
                Arguments.of(new JanggiPosition(0, 8), new 마(Side.CHO)),
                Arguments.of(new JanggiPosition(0, 3), new 상(Side.CHO)),
                Arguments.of(new JanggiPosition(0, 7), new 상(Side.CHO)),
                Arguments.of(new JanggiPosition(2, 5), new 궁(Side.HAN)),
                Arguments.of(new JanggiPosition(1, 1), new 차(Side.HAN)),
                Arguments.of(new JanggiPosition(1, 9), new 차(Side.HAN)),
                Arguments.of(new JanggiPosition(3, 2), new 포(Side.HAN)),
                Arguments.of(new JanggiPosition(3, 8), new 포(Side.HAN)),
                Arguments.of(new JanggiPosition(4, 1), new 졸병(Side.HAN)),
                Arguments.of(new JanggiPosition(4, 3), new 졸병(Side.HAN)),
                Arguments.of(new JanggiPosition(4, 5), new 졸병(Side.HAN)),
                Arguments.of(new JanggiPosition(4, 7), new 졸병(Side.HAN)),
                Arguments.of(new JanggiPosition(4, 9), new 졸병(Side.HAN)),
                Arguments.of(new JanggiPosition(1, 4), new 사(Side.HAN)),
                Arguments.of(new JanggiPosition(1, 6), new 사(Side.HAN)),
                Arguments.of(new JanggiPosition(1, 2), new 마(Side.HAN)),
                Arguments.of(new JanggiPosition(1, 8), new 마(Side.HAN)),
                Arguments.of(new JanggiPosition(1, 3), new 상(Side.HAN)),
                Arguments.of(new JanggiPosition(1, 7), new 상(Side.HAN))
        );
    }

    @Test
    void 기물을_이동하며_마주치는_장애물을_확인할_수_있다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when & then
        assertThatThrownBy(() -> janggiBoard.move(_ZERO_ONE, _SIX_ONE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    class 기물을_이동시킬_수_있다 {
        @Test
        void 궁을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(_NINE_FIVE, _EIGHT_FIVE);

            // then
            assertThat(janggiBoard.getPieceFrom(_EIGHT_FIVE)).isInstanceOf(궁.class);
        }

        @Test
        void 마를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(_ZERO_TWO, _EIGHT_THREE);

            // then
            assertThat(janggiBoard.getPieceFrom(_EIGHT_THREE)).isInstanceOf(마.class);
        }

        @Test
        void 사를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(_ZERO_FOUR, _NINE_FOUR);

            // then
            assertThat(janggiBoard.getPieceFrom(_NINE_FOUR)).isInstanceOf(사.class);
        }

        @Test
        void 상을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(_SEVEN_FIVE, _SIX_FIVE);
            janggiBoard.move(_ZERO_THREE, _SEVEN_FIVE);

            // then
            assertThat(janggiBoard.getPieceFrom(_SEVEN_FIVE)).isInstanceOf(상.class);
        }

        @Test
        void 졸을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(_SEVEN_ONE, _SIX_ONE);

            // then
            assertThat(janggiBoard.getPieceFrom(_SIX_ONE)).isInstanceOf(졸병.class);
        }

        @Test
        void 병을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(_FOUR_ONE, _FIVE_ONE);

            // then
            assertThat(janggiBoard.getPieceFrom(_FIVE_ONE)).isInstanceOf(졸병.class);
        }

        @Test
        void 차를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(_SEVEN_NINE, _SEVEN_EIGHT);
            janggiBoard.move(_ZERO_NINE, _FIVE_NINE);

            // then
            assertThat(janggiBoard.getPieceFrom(_FIVE_NINE)).isInstanceOf(차.class);
        }

        @Test
        void 차를_오른쪽으로_이동시킬_수_있다() {
            // given

            JanggiBoard janggiBoard = new JanggiBoard();
            janggiBoard.move(_ZERO_TWO, _EIGHT_THREE);

            // when
            janggiBoard.move(_ZERO_ONE, _ZERO_TWO);

            // then
            assertThat(janggiBoard.getPieceFrom(_ZERO_TWO)).isInstanceOf(차.class);
        }

        @Test
        void 포는_기물을_하나_뛰어넘어서_이동할_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(_FOUR_THREE, _FOUR_FOUR);
            janggiBoard.move(_ONE_TWO, _THREE_THREE);
            janggiBoard.move(_THREE_THREE, _FIVE_TWO);

            janggiBoard.move(_EIGHT_TWO, _FOUR_TWO);

            // then
            assertThat(janggiBoard.getPieceFrom(_FOUR_TWO)).isInstanceOf(포.class);
        }
    }

    @Test
    void 포는_포를_뛰어넘을_수_없다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when & then
        assertThatThrownBy(() -> janggiBoard.move(_EIGHT_EIGHT, _TWO_EIGHT))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 포는_기물을_두_개_이상_뛰어넘을_수_없다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when
        janggiBoard.move(_NINE_FIVE, _EIGHT_FIVE);
        janggiBoard.move(_EIGHT_EIGHT, _EIGHT_THREE);

        // then
        assertThatThrownBy(() -> janggiBoard.move(_EIGHT_THREE, _THREE_THREE))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 기물은_다른_기물을_잡아서_잡힌_기물의_상태를_바꿀_수_있다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when
        janggiBoard.move(_SEVEN_SEVEN, _SIX_SEVEN);
        janggiBoard.move(_SIX_SEVEN, _FIVE_SEVEN);

        Piece pieceInDanger = janggiBoard.getPieceFrom(_FOUR_SEVEN);
        janggiBoard.move(_FIVE_SEVEN, _FOUR_SEVEN);

        // then
        assertThat(pieceInDanger.getState()).isInstanceOf(Captured.class);
    }

    @Test
    void 포는_포를_잡을_수_없다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when
        janggiBoard.move(_SEVEN_THREE, _SEVEN_TWO);

        // then
        assertThatThrownBy(() -> janggiBoard.move(_EIGHT_TWO, _THREE_TWO))
                .isInstanceOf(IllegalStateException.class);
    }
}
