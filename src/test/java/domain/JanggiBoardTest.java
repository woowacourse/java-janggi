package domain;

import static domain.Fixtures._EIGHT_EIGHT;
import static domain.Fixtures._EIGHT_FIVE;
import static domain.Fixtures._EIGHT_THREE;
import static domain.Fixtures._EIGHT_TWO;
import static domain.Fixtures._FIVE_NINE;
import static domain.Fixtures._FIVE_ONE;
import static domain.Fixtures._FIVE_SEVEN;
import static domain.Fixtures._FIVE_TWO;
import static domain.Fixtures._FOUR_FIVE;
import static domain.Fixtures._FOUR_FOUR;
import static domain.Fixtures._FOUR_NINE;
import static domain.Fixtures._FOUR_ONE;
import static domain.Fixtures._FOUR_SEVEN;
import static domain.Fixtures._FOUR_THREE;
import static domain.Fixtures._FOUR_TWO;
import static domain.Fixtures._NINE_FIVE;
import static domain.Fixtures._NINE_FOUR;
import static domain.Fixtures._ONE_EIGHT;
import static domain.Fixtures._ONE_FOUR;
import static domain.Fixtures._ONE_NINE;
import static domain.Fixtures._ONE_ONE;
import static domain.Fixtures._ONE_SEVEN;
import static domain.Fixtures._ONE_SIX;
import static domain.Fixtures._ONE_THREE;
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
import static domain.Fixtures._THREE_EIGHT;
import static domain.Fixtures._THREE_THREE;
import static domain.Fixtures._THREE_TWO;
import static domain.Fixtures._TWO_EIGHT;
import static domain.Fixtures._TWO_FIVE;
import static domain.Fixtures._ZERO_EIGHT;
import static domain.Fixtures._ZERO_FOUR;
import static domain.Fixtures._ZERO_NINE;
import static domain.Fixtures._ZERO_ONE;
import static domain.Fixtures._ZERO_SEVEN;
import static domain.Fixtures._ZERO_SIX;
import static domain.Fixtures._ZERO_THREE;
import static domain.Fixtures._ZERO_TWO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Side;
import domain.piece.Soldier;
import domain.piece.state.Captured;
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
                Arguments.of(_NINE_FIVE, new General(Side.CHO)),
                Arguments.of(_ZERO_ONE, new Chariot(Side.CHO)),
                Arguments.of(_ZERO_NINE, new Chariot(Side.CHO)),
                Arguments.of(_EIGHT_TWO, new Cannon(Side.CHO)),
                Arguments.of(_EIGHT_EIGHT, new Cannon(Side.CHO)),
                Arguments.of(_SEVEN_ONE, new Soldier(Side.CHO)),
                Arguments.of(_SEVEN_THREE, new Soldier(Side.CHO)),
                Arguments.of(_SEVEN_FIVE, new Soldier(Side.CHO)),
                Arguments.of(_SEVEN_SEVEN, new Soldier(Side.CHO)),
                Arguments.of(_SEVEN_NINE, new Soldier(Side.CHO)),
                Arguments.of(_ZERO_FOUR, new Guard(Side.CHO)),
                Arguments.of(_ZERO_SIX, new Guard(Side.CHO)),
                Arguments.of(_ZERO_TWO, new Horse(Side.CHO)),
                Arguments.of(_ZERO_EIGHT, new Horse(Side.CHO)),
                Arguments.of(_ZERO_THREE, new Elephant(Side.CHO)),
                Arguments.of(_ZERO_SEVEN, new Elephant(Side.CHO)),
                Arguments.of(_TWO_FIVE, new General(Side.HAN)),
                Arguments.of(_ONE_ONE, new Chariot(Side.HAN)),
                Arguments.of(_ONE_NINE, new Chariot(Side.HAN)),
                Arguments.of(_THREE_TWO, new Cannon(Side.HAN)),
                Arguments.of(_THREE_EIGHT, new Cannon(Side.HAN)),
                Arguments.of(_FOUR_ONE, new Soldier(Side.HAN)),
                Arguments.of(_FOUR_THREE, new Soldier(Side.HAN)),
                Arguments.of(_FOUR_FIVE, new Soldier(Side.HAN)),
                Arguments.of(_FOUR_SEVEN, new Soldier(Side.HAN)),
                Arguments.of(_FOUR_NINE, new Soldier(Side.HAN)),
                Arguments.of(_ONE_FOUR, new Guard(Side.HAN)),
                Arguments.of(_ONE_SIX, new Guard(Side.HAN)),
                Arguments.of(_ONE_TWO, new Horse(Side.HAN)),
                Arguments.of(_ONE_EIGHT, new Horse(Side.HAN)),
                Arguments.of(_ONE_THREE, new Elephant(Side.HAN)),
                Arguments.of(_ONE_SEVEN, new Elephant(Side.HAN))
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
        void General을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(_NINE_FIVE, _EIGHT_FIVE);

            // then
            assertThat(janggiBoard.getPieceFrom(_EIGHT_FIVE)).isInstanceOf(General.class);
        }

        @Test
        void Horse를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(_ZERO_TWO, _EIGHT_THREE);

            // then
            assertThat(janggiBoard.getPieceFrom(_EIGHT_THREE)).isInstanceOf(Horse.class);
        }

        @Test
        void 사를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(_ZERO_FOUR, _NINE_FOUR);

            // then
            assertThat(janggiBoard.getPieceFrom(_NINE_FOUR)).isInstanceOf(Guard.class);
        }

        @Test
        void 상을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(_SEVEN_FIVE, _SIX_FIVE);
            janggiBoard.move(_ZERO_THREE, _SEVEN_FIVE);

            // then
            assertThat(janggiBoard.getPieceFrom(_SEVEN_FIVE)).isInstanceOf(Elephant.class);
        }

        @Test
        void 졸을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(_SEVEN_ONE, _SIX_ONE);

            // then
            assertThat(janggiBoard.getPieceFrom(_SIX_ONE)).isInstanceOf(Soldier.class);
        }

        @Test
        void 병을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(_FOUR_ONE, _FIVE_ONE);

            // then
            assertThat(janggiBoard.getPieceFrom(_FIVE_ONE)).isInstanceOf(Soldier.class);
        }

        @Test
        void 차를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard();

            // when
            janggiBoard.move(_SEVEN_NINE, _SEVEN_EIGHT);
            janggiBoard.move(_ZERO_NINE, _FIVE_NINE);

            // then
            assertThat(janggiBoard.getPieceFrom(_FIVE_NINE)).isInstanceOf(Chariot.class);
        }

        @Test
        void 차를_오른쪽으로_이동시킬_수_있다() {
            // given

            JanggiBoard janggiBoard = new JanggiBoard();
            janggiBoard.move(_ZERO_TWO, _EIGHT_THREE);

            // when
            janggiBoard.move(_ZERO_ONE, _ZERO_TWO);

            // then
            assertThat(janggiBoard.getPieceFrom(_ZERO_TWO)).isInstanceOf(Chariot.class);
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
            assertThat(janggiBoard.getPieceFrom(_FOUR_TWO)).isInstanceOf(Cannon.class);
        }
    }

    @Test
    void 포는_포를_뛰어넘을_수_없다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard();

        // when & then
        assertThatThrownBy(() -> janggiBoard.move(_EIGHT_EIGHT, _TWO_EIGHT))
                .isInstanceOf(IllegalArgumentException.class);
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
                .isInstanceOf(IllegalArgumentException.class);
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
                .isInstanceOf(IllegalArgumentException.class);
    }
}
