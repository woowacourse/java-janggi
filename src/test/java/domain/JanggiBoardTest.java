package domain;

import static domain.Fixtures.EIGHT_EIGHT;
import static domain.Fixtures.EIGHT_FIVE;
import static domain.Fixtures.EIGHT_THREE;
import static domain.Fixtures.EIGHT_TWO;
import static domain.Fixtures.FIVE_NINE;
import static domain.Fixtures.FIVE_ONE;
import static domain.Fixtures.FIVE_SEVEN;
import static domain.Fixtures.FIVE_TWO;
import static domain.Fixtures.FOUR_FIVE;
import static domain.Fixtures.FOUR_FOUR;
import static domain.Fixtures.FOUR_NINE;
import static domain.Fixtures.FOUR_ONE;
import static domain.Fixtures.FOUR_SEVEN;
import static domain.Fixtures.FOUR_THREE;
import static domain.Fixtures.FOUR_TWO;
import static domain.Fixtures.NINE_FIVE;
import static domain.Fixtures.NINE_FOUR;
import static domain.Fixtures.ONE_EIGHT;
import static domain.Fixtures.ONE_FOUR;
import static domain.Fixtures.ONE_NINE;
import static domain.Fixtures.ONE_ONE;
import static domain.Fixtures.ONE_SEVEN;
import static domain.Fixtures.ONE_SIX;
import static domain.Fixtures.ONE_THREE;
import static domain.Fixtures.ONE_TWO;
import static domain.Fixtures.SEVEN_EIGHT;
import static domain.Fixtures.SEVEN_FIVE;
import static domain.Fixtures.SEVEN_NINE;
import static domain.Fixtures.SEVEN_ONE;
import static domain.Fixtures.SEVEN_SEVEN;
import static domain.Fixtures.SEVEN_THREE;
import static domain.Fixtures.SEVEN_TWO;
import static domain.Fixtures.SIX_FIVE;
import static domain.Fixtures.SIX_ONE;
import static domain.Fixtures.SIX_SEVEN;
import static domain.Fixtures.THREE_EIGHT;
import static domain.Fixtures.THREE_THREE;
import static domain.Fixtures.THREE_TWO;
import static domain.Fixtures.TWO_EIGHT;
import static domain.Fixtures.TWO_FIVE;
import static domain.Fixtures.ZERO_EIGHT;
import static domain.Fixtures.ZERO_FOUR;
import static domain.Fixtures.ZERO_NINE;
import static domain.Fixtures.ZERO_ONE;
import static domain.Fixtures.ZERO_SEVEN;
import static domain.Fixtures.ZERO_SIX;
import static domain.Fixtures.ZERO_THREE;
import static domain.Fixtures.ZERO_TWO;
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
                Arguments.of(NINE_FIVE, new General(Side.CHO)),
                Arguments.of(ZERO_ONE, new Chariot(Side.CHO)),
                Arguments.of(ZERO_NINE, new Chariot(Side.CHO)),
                Arguments.of(EIGHT_TWO, new Cannon(Side.CHO)),
                Arguments.of(EIGHT_EIGHT, new Cannon(Side.CHO)),
                Arguments.of(SEVEN_ONE, new Soldier(Side.CHO)),
                Arguments.of(SEVEN_THREE, new Soldier(Side.CHO)),
                Arguments.of(SEVEN_FIVE, new Soldier(Side.CHO)),
                Arguments.of(SEVEN_SEVEN, new Soldier(Side.CHO)),
                Arguments.of(SEVEN_NINE, new Soldier(Side.CHO)),
                Arguments.of(ZERO_FOUR, new Guard(Side.CHO)),
                Arguments.of(ZERO_SIX, new Guard(Side.CHO)),
                Arguments.of(ZERO_TWO, new Horse(Side.CHO)),
                Arguments.of(ZERO_EIGHT, new Horse(Side.CHO)),
                Arguments.of(ZERO_THREE, new Elephant(Side.CHO)),
                Arguments.of(ZERO_SEVEN, new Elephant(Side.CHO)),
                Arguments.of(TWO_FIVE, new General(Side.HAN)),
                Arguments.of(ONE_ONE, new Chariot(Side.HAN)),
                Arguments.of(ONE_NINE, new Chariot(Side.HAN)),
                Arguments.of(THREE_TWO, new Cannon(Side.HAN)),
                Arguments.of(THREE_EIGHT, new Cannon(Side.HAN)),
                Arguments.of(FOUR_ONE, new Soldier(Side.HAN)),
                Arguments.of(FOUR_THREE, new Soldier(Side.HAN)),
                Arguments.of(FOUR_FIVE, new Soldier(Side.HAN)),
                Arguments.of(FOUR_SEVEN, new Soldier(Side.HAN)),
                Arguments.of(FOUR_NINE, new Soldier(Side.HAN)),
                Arguments.of(ONE_FOUR, new Guard(Side.HAN)),
                Arguments.of(ONE_SIX, new Guard(Side.HAN)),
                Arguments.of(ONE_TWO, new Horse(Side.HAN)),
                Arguments.of(ONE_EIGHT, new Horse(Side.HAN)),
                Arguments.of(ONE_THREE, new Elephant(Side.HAN)),
                Arguments.of(ONE_SEVEN, new Elephant(Side.HAN))
        );
    }

    @Test
    void 기물을_이동하며_마주치는_장애물을_확인할_수_있다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

        // when & then
        assertThatThrownBy(() -> janggiBoard.move(ZERO_ONE, SIX_ONE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    class 기물을_이동시킬_수_있다 {
        @Test
        void General을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(NINE_FIVE, EIGHT_FIVE);

            // then
            assertThat(janggiBoard.getPieceFrom(EIGHT_FIVE)).isInstanceOf(General.class);
        }

        @Test
        void Horse를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(ZERO_TWO, EIGHT_THREE);

            // then
            assertThat(janggiBoard.getPieceFrom(EIGHT_THREE)).isInstanceOf(Horse.class);
        }

        @Test
        void 사를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(ZERO_FOUR, NINE_FOUR);

            // then
            assertThat(janggiBoard.getPieceFrom(NINE_FOUR)).isInstanceOf(Guard.class);
        }

        @Test
        void 상을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(SEVEN_FIVE, SIX_FIVE);
            janggiBoard.move(ZERO_THREE, SEVEN_FIVE);

            // then
            assertThat(janggiBoard.getPieceFrom(SEVEN_FIVE)).isInstanceOf(Elephant.class);
        }

        @Test
        void 졸을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(SEVEN_ONE, SIX_ONE);

            // then
            assertThat(janggiBoard.getPieceFrom(SIX_ONE)).isInstanceOf(Soldier.class);
        }

        @Test
        void 병을_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(FOUR_ONE, FIVE_ONE);

            // then
            assertThat(janggiBoard.getPieceFrom(FIVE_ONE)).isInstanceOf(Soldier.class);
        }

        @Test
        void 차를_이동시킬_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(SEVEN_NINE, SEVEN_EIGHT);
            janggiBoard.move(ZERO_NINE, FIVE_NINE);

            // then
            assertThat(janggiBoard.getPieceFrom(FIVE_NINE)).isInstanceOf(Chariot.class);
        }

        @Test
        void 차를_오른쪽으로_이동시킬_수_있다() {
            // given

            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());
            janggiBoard.move(ZERO_TWO, EIGHT_THREE);

            // when
            janggiBoard.move(ZERO_ONE, ZERO_TWO);

            // then
            assertThat(janggiBoard.getPieceFrom(ZERO_TWO)).isInstanceOf(Chariot.class);
        }

        @Test
        void 포는_기물을_하나_뛰어넘어서_이동할_수_있다() {
            // given
            JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

            // when
            janggiBoard.move(FOUR_THREE, FOUR_FOUR);
            janggiBoard.move(ONE_TWO, THREE_THREE);
            janggiBoard.move(THREE_THREE, FIVE_TWO);

            janggiBoard.move(EIGHT_TWO, FOUR_TWO);

            // then
            assertThat(janggiBoard.getPieceFrom(FOUR_TWO)).isInstanceOf(Cannon.class);
        }
    }

    @Test
    void 포는_포를_뛰어넘을_수_없다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

        // when & then
        assertThatThrownBy(() -> janggiBoard.move(EIGHT_EIGHT, TWO_EIGHT))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_기물을_두_개_이상_뛰어넘을_수_없다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

        // when
        janggiBoard.move(NINE_FIVE, EIGHT_FIVE);
        janggiBoard.move(EIGHT_EIGHT, EIGHT_THREE);

        // then
        assertThatThrownBy(() -> janggiBoard.move(EIGHT_THREE, THREE_THREE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 기물은_다른_기물을_잡아서_잡힌_기물의_상태를_바꿀_수_있다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

        // when
        janggiBoard.move(SEVEN_SEVEN, SIX_SEVEN);
        janggiBoard.move(SIX_SEVEN, FIVE_SEVEN);

        Piece pieceInDanger = janggiBoard.getPieceFrom(FOUR_SEVEN);
        janggiBoard.move(FIVE_SEVEN, FOUR_SEVEN);

        // then
        assertThat(pieceInDanger.getState()).isInstanceOf(Captured.class);
    }

    @Test
    void 포는_포를_잡을_수_없다() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());

        // when
        janggiBoard.move(SEVEN_THREE, SEVEN_TWO);

        // then
        assertThatThrownBy(() -> janggiBoard.move(EIGHT_TWO, THREE_TWO))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
