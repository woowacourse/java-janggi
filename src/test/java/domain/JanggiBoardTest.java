package domain;

import static domain.Fixtures.EIGHT_EIGHT;
import static domain.Fixtures.EIGHT_ONE;
import static domain.Fixtures.EIGHT_SEVEN;
import static domain.Fixtures.EIGHT_THREE;
import static domain.Fixtures.EIGHT_TWO;
import static domain.Fixtures.EIGHT_ZERO;
import static domain.Fixtures.FIVE_EIGHT;
import static domain.Fixtures.FIVE_FOUR;
import static domain.Fixtures.FIVE_NINE;
import static domain.Fixtures.FIVE_SEVEN;
import static domain.Fixtures.FIVE_SIX;
import static domain.Fixtures.FIVE_TWO;
import static domain.Fixtures.FOUR_FOUR;
import static domain.Fixtures.FOUR_NINE;
import static domain.Fixtures.FOUR_ONE;
import static domain.Fixtures.FOUR_ZERO;
import static domain.Fixtures.NINE_FIVE;
import static domain.Fixtures.NINE_FOUR;
import static domain.Fixtures.NINE_ONE;
import static domain.Fixtures.NINE_SEVEN;
import static domain.Fixtures.NINE_ZERO;
import static domain.Fixtures.ONE_FIVE;
import static domain.Fixtures.ONE_FOUR;
import static domain.Fixtures.ONE_ONE;
import static domain.Fixtures.ONE_SEVEN;
import static domain.Fixtures.ONE_SIX;
import static domain.Fixtures.ONE_ZERO;
import static domain.Fixtures.SEVEN_FIVE;
import static domain.Fixtures.SEVEN_FOUR;
import static domain.Fixtures.SEVEN_ONE;
import static domain.Fixtures.SEVEN_SEVEN;
import static domain.Fixtures.SEVEN_SIX;
import static domain.Fixtures.SEVEN_ZERO;
import static domain.Fixtures.SIX_ONE;
import static domain.Fixtures.SIX_ZERO;
import static domain.Fixtures.THREE_EIGHT;
import static domain.Fixtures.THREE_FOUR;
import static domain.Fixtures.THREE_ONE;
import static domain.Fixtures.THREE_SEVEN;
import static domain.Fixtures.THREE_THREE;
import static domain.Fixtures.THREE_ZERO;
import static domain.Fixtures.TWO_EIGHT;
import static domain.Fixtures.TWO_FIVE;
import static domain.Fixtures.TWO_FOUR;
import static domain.Fixtures.TWO_ONE;
import static domain.Fixtures.TWO_THREE;
import static domain.Fixtures.TWO_ZERO;
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
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

        // then
        assertThat(janggiBoard.getJanggiBoard().size())
                .isEqualTo(90);
    }

    @ParameterizedTest
    @MethodSource("providePlaceAndPiece")
    void 장기_기물의_초기_위치를_저장한다(JanggiPosition position, Piece piece) {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

        // when & then
        assertThat(janggiBoard.getPieceFrom(position)).isInstanceOf(piece.getClass());
    }

    private static Stream<Arguments> providePlaceAndPiece() {
        return Stream.of(
                Arguments.of(FIVE_NINE, new General(Side.CHO)),
                Arguments.of(ONE_ZERO, new Chariot(Side.CHO)),
                Arguments.of(NINE_ZERO, new Chariot(Side.CHO)),
                Arguments.of(TWO_EIGHT, new Cannon(Side.CHO)),
                Arguments.of(EIGHT_EIGHT, new Cannon(Side.CHO)),
                Arguments.of(ONE_SEVEN, new Soldier(Side.CHO)),
                Arguments.of(THREE_SEVEN, new Soldier(Side.CHO)),
                Arguments.of(FIVE_SEVEN, new Soldier(Side.CHO)),
                Arguments.of(SEVEN_SEVEN, new Soldier(Side.CHO)),
                Arguments.of(NINE_SEVEN, new Soldier(Side.CHO)),
                Arguments.of(FOUR_ZERO, new Guard(Side.CHO)),
                Arguments.of(SIX_ZERO, new Guard(Side.CHO)),
                Arguments.of(TWO_ZERO, new Horse(Side.CHO)),
                Arguments.of(EIGHT_ZERO, new Horse(Side.CHO)),
                Arguments.of(THREE_ZERO, new Elephant(Side.CHO)),
                Arguments.of(SEVEN_ZERO, new Elephant(Side.CHO)),
                Arguments.of(FIVE_TWO, new General(Side.HAN)),
                Arguments.of(ONE_ONE, new Chariot(Side.HAN)),
                Arguments.of(NINE_ONE, new Chariot(Side.HAN)),
                Arguments.of(TWO_THREE, new Cannon(Side.HAN)),
                Arguments.of(EIGHT_THREE, new Cannon(Side.HAN)),
                Arguments.of(ONE_FOUR, new Soldier(Side.HAN)),
                Arguments.of(THREE_FOUR, new Soldier(Side.HAN)),
                Arguments.of(FIVE_FOUR, new Soldier(Side.HAN)),
                Arguments.of(SEVEN_FOUR, new Soldier(Side.HAN)),
                Arguments.of(NINE_FOUR, new Soldier(Side.HAN)),
                Arguments.of(FOUR_ONE, new Guard(Side.HAN)),
                Arguments.of(SIX_ONE, new Guard(Side.HAN)),
                Arguments.of(TWO_ONE, new Horse(Side.HAN)),
                Arguments.of(EIGHT_ONE, new Horse(Side.HAN)),
                Arguments.of(THREE_ONE, new Elephant(Side.HAN)),
                Arguments.of(SEVEN_ONE, new Elephant(Side.HAN))
        );
    }

    @Test
    void 기물을_이동하며_마주치는_장애물을_확인할_수_있다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

        // when & then
        assertThatThrownBy(() -> janggiBoard.move(ONE_ZERO, ONE_SIX))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    class 기물을_이동시킬_수_있다 {
        @Test
        void General을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(FIVE_NINE, FIVE_EIGHT);

            // then
            assertThat(janggiBoard.getPieceFrom(FIVE_EIGHT)).isInstanceOf(General.class);
        }

        @Test
        void Horse를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(TWO_ZERO, THREE_EIGHT);

            // then
            assertThat(janggiBoard.getPieceFrom(THREE_EIGHT)).isInstanceOf(Horse.class);
        }

        @Test
        void 사를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(FOUR_ZERO, FOUR_NINE);

            // then
            assertThat(janggiBoard.getPieceFrom(FOUR_NINE)).isInstanceOf(Guard.class);
        }

        @Test
        void 상을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(FIVE_SEVEN, FIVE_SIX);
            janggiBoard.move(THREE_ZERO, FIVE_SEVEN);

            // then
            assertThat(janggiBoard.getPieceFrom(FIVE_SEVEN)).isInstanceOf(Elephant.class);
        }

        @Test
        void 졸을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(ONE_SEVEN, ONE_SIX);

            // then
            assertThat(janggiBoard.getPieceFrom(ONE_SIX)).isInstanceOf(Soldier.class);
        }

        @Test
        void 병을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(ONE_FOUR, ONE_FIVE);

            // then
            assertThat(janggiBoard.getPieceFrom(ONE_FIVE)).isInstanceOf(Soldier.class);
        }

        @Test
        void 차를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(NINE_SEVEN, EIGHT_SEVEN);
            janggiBoard.move(NINE_ZERO, NINE_FIVE);

            // then
            assertThat(janggiBoard.getPieceFrom(NINE_FIVE)).isInstanceOf(Chariot.class);
        }

        @Test
        void 차를_오른쪽으로_이동시킬_수_있다() {
            // given

            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());
            janggiBoard.move(TWO_ZERO, THREE_EIGHT);

            // when
            janggiBoard.move(ONE_ZERO, TWO_ZERO);

            // then
            assertThat(janggiBoard.getPieceFrom(TWO_ZERO)).isInstanceOf(Chariot.class);
        }

        @Test
        void 포는_기물을_하나_뛰어넘어서_이동할_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(THREE_FOUR, FOUR_FOUR);
            janggiBoard.move(TWO_ONE, THREE_THREE);
            janggiBoard.move(THREE_THREE, TWO_FIVE);

            janggiBoard.move(TWO_EIGHT, TWO_FOUR);

            // then
            assertThat(janggiBoard.getPieceFrom(TWO_FOUR)).isInstanceOf(Cannon.class);
        }
    }

    @Test
    void 포는_포를_뛰어넘을_수_없다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

        // when & then
        assertThatThrownBy(() -> janggiBoard.move(EIGHT_EIGHT, EIGHT_TWO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_기물을_두_개_이상_뛰어넘을_수_없다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

        // when
        janggiBoard.move(FIVE_NINE, FIVE_EIGHT);
        janggiBoard.move(EIGHT_EIGHT, THREE_EIGHT);

        // then
        assertThatThrownBy(() -> janggiBoard.move(THREE_EIGHT, THREE_THREE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 기물은_다른_기물을_잡아서_잡힌_기물의_상태를_바꿀_수_있다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

        // when
        janggiBoard.move(SEVEN_SEVEN, SEVEN_SIX);
        janggiBoard.move(SEVEN_SIX, SEVEN_FIVE);

        Piece pieceInDanger = janggiBoard.getPieceFrom(SEVEN_FOUR);
        janggiBoard.move(SEVEN_FIVE, SEVEN_FOUR);

        // then
        assertThat(pieceInDanger.getState()).isInstanceOf(Captured.class);
    }

    @Test
    void 포는_포를_잡을_수_없다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

        // when
        janggiBoard.move(THREE_FOUR, TWO_FOUR);

        // then
        assertThatThrownBy(() -> janggiBoard.move(TWO_EIGHT, TWO_THREE))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
