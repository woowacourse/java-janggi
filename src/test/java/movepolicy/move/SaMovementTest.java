package movepolicy.move;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;
import position.Position;

class SaMovementTest {

    private static final Position CHO_LEFT_TOP = new Position(2, 3);
    private static final Position CHO_CENTER_TOP = new Position(2, 4);
    private static final Position CHO_RIGHT_TOP = new Position(2, 5);
    private static final Position CHO_LEFT_MIDDLE = new Position(1, 3);
    private static final Position CHO_CENTER = new Position(1, 4);
    private static final Position CHO_RIGHT_MIDDLE = new Position(1, 5);
    private static final Position CHO_LEFT_BOTTOM = new Position(0, 3);
    private static final Position CHO_CENTER_BOTTOM = new Position(0, 4);
    private static final Position CHO_RIGHT_BOTTOM = new Position(0, 5);

    private final Movement movement = PieceType.SA.getMovement();

    @ParameterizedTest
    @MethodSource("choGungsungPositionsForForwardStep")
    void 초나라는_궁성_내에서_앞_1칸_이동할_수_있다(final Position departure) {
        // given
        Side side = Side.CHO;
        Position destination = departure.move(side.forwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choGungsungPositionsForForwardStep")
    void 한나라는_궁성_내에서_앞_1칸_이동할_수_있다(final Position choDeparture) {
        // given
        Side side = Side.HAN;
        Position departure = choDeparture.reverse();
        Position destination = departure.move(side.forwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choGungsungPositionsForBackStep")
    void 초나라는_궁성_내에서_뒤_1칸_이동할_수_있다(final Position departure) {
        // given
        Side side = Side.CHO;
        Position destination = departure.move(side.backDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choGungsungPositionsForBackStep")
    void 한나라는_궁성_내에서_뒤_1칸_이동할_수_있다(final Position choDeparture) {
        // given
        Side side = Side.HAN;
        Position departure = choDeparture.reverse();
        Position destination = departure.move(side.backDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choGungsungPositionsForLeftStep")
    void 초나라는_궁성_내에서_좌_1칸_이동할_수_있다(final Position departure) {
        // given
        Side side = Side.CHO;
        Position destination = departure.move(side.leftDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choGungsungPositionsForLeftStep")
    void 한나라는_궁성_내에서_좌_1칸_이동할_수_있다(final Position choDeparture) {
        // given
        Side side = Side.HAN;
        Position departure = choDeparture.reverse();
        Position destination = departure.move(side.leftDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choGungsungPositionsForRightStep")
    void 초나라는_궁성_내에서_우_1칸_이동할_수_있다(final Position departure) {
        // given
        Side side = Side.CHO;
        Position destination = departure.move(side.rightDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choGungsungPositionsForRightStep")
    void 한나라는_궁성_내에서_우_1칸_이동할_수_있다(final Position choDeparture) {
        // given
        Side side = Side.HAN;
        Position departure = choDeparture.reverse();
        Position destination = departure.move(side.rightDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choGungsungPositionsForDiagonalStep")
    void 초나라는_궁성_중앙에서_대각_이동할_수_있다(final Position destination) {
        // given
        Side side = Side.CHO;
        // when & then
        assertThat(movement.canReach(CHO_CENTER, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choGungsungPositionsForDiagonalStep")
    void 한나라는_궁성_중앙에서_대각_이동할_수_있다(final Position choDestination) {
        // given
        Side side = Side.HAN;
        Position departure = CHO_CENTER.reverse();
        Position destination = choDestination.reverse();
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choGungsungPositionsForDiagonalStep")
    void 초나라는_궁성_모서리에서_궁성_중앙으로_대각_이동할_수_있다(final Position departure) {
        // given
        Side side = Side.CHO;
        // when & then
        assertThat(movement.canReach(departure, CHO_CENTER, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choGungsungPositionsForDiagonalStep")
    void 한나라는_궁성_모서리에서_궁성_중앙으로_대각_이동할_수_있다(final Position choDeparture) {
        // given
        Side side = Side.HAN;
        Position departure = choDeparture.reverse();
        Position destination = CHO_CENTER.reverse();
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("invalidChoDiagonalCases")
    void 초나라는_궁성의_직각_면에서_대각_이동할_수_없다(final Position departure, final Position destination) {
        // given
        Side side = Side.CHO;
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("invalidChoDiagonalCases")
    void 한나라는_궁성의_직각_면에서_대각_이동할_수_없다(final Position choDeparture, final Position choDestination) {
        // given
        Side side = Side.HAN;
        Position departure = choDeparture.reverse();
        Position destination = choDestination.reverse();
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("invalidChoOutsideCases")
    void 초나라는_궁성을_벗어날_수_없다(final Position departure, final Position destination) {
        // given
        Side side = Side.CHO;
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("invalidChoOutsideCases")
    void 한나라는_궁성을_벗어날_수_없다(final Position choDeparture, final Position choDestination) {
        // given
        Side side = Side.HAN;
        Position departure = choDeparture.reverse();
        Position destination = choDestination.reverse();
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @Test
    void 궁성_밖으로_우측_이동할_수_없다() {
        // given
        Side side = Side.CHO;
        Position departure = new Position(1, 5);
        Position destination = new Position(1, 6);
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }


    @Test
    void 출발지와_도착지_사이에는_이동_경로가_존재하지_않는다() {
        // given
        Side side = Side.CHO;
        Piece piece = new Piece(side, PieceType.SA);
        Position departure = new Position(1, 4);
        Position destination = departure.move(side.forwardDelta());
        // when
        List<Position> positions = piece.findPathPositions(departure, destination);
        // then
        assertThat(positions).hasSize(0);
    }

    private static Stream<Position> choGungsungPositionsForForwardStep() {
        return Stream.of(
            CHO_LEFT_MIDDLE, CHO_CENTER, CHO_RIGHT_MIDDLE,
            CHO_LEFT_BOTTOM, CHO_CENTER_BOTTOM, CHO_RIGHT_BOTTOM
        );
    }

    private static Stream<Position> choGungsungPositionsForBackStep() {
        return Stream.of(
            CHO_LEFT_TOP, CHO_CENTER_TOP, CHO_RIGHT_TOP,
            CHO_LEFT_MIDDLE, CHO_CENTER, CHO_RIGHT_MIDDLE
        );
    }

    private static Stream<Position> choGungsungPositionsForLeftStep() {
        return Stream.of(
            CHO_CENTER_TOP, CHO_RIGHT_TOP,
            CHO_CENTER, CHO_RIGHT_MIDDLE,
            CHO_CENTER_BOTTOM, CHO_RIGHT_BOTTOM
        );
    }

    private static Stream<Position> choGungsungPositionsForRightStep() {
        return Stream.of(
            CHO_LEFT_TOP, CHO_CENTER_TOP,
            CHO_LEFT_MIDDLE, CHO_CENTER,
            CHO_LEFT_BOTTOM, CHO_CENTER_BOTTOM
        );
    }

    private static Stream<Position> choGungsungPositionsForDiagonalStep() {
        return Stream.of(
            CHO_LEFT_TOP, CHO_RIGHT_TOP,
            CHO_LEFT_BOTTOM, CHO_RIGHT_BOTTOM
        );
    }

    private static Stream<Arguments> invalidChoDiagonalCases() {
        return Stream.of(
            Arguments.of(CHO_CENTER_TOP, CHO_LEFT_MIDDLE),
            Arguments.of(CHO_CENTER_TOP, CHO_RIGHT_MIDDLE),
            Arguments.of(CHO_LEFT_MIDDLE, CHO_CENTER_TOP),
            Arguments.of(CHO_LEFT_MIDDLE, CHO_CENTER_BOTTOM),
            Arguments.of(CHO_RIGHT_MIDDLE, CHO_CENTER_TOP),
            Arguments.of(CHO_RIGHT_MIDDLE, CHO_CENTER_BOTTOM),
            Arguments.of(CHO_CENTER_BOTTOM, CHO_LEFT_MIDDLE),
            Arguments.of(CHO_CENTER_BOTTOM, CHO_RIGHT_MIDDLE)
        );
    }

    private static Stream<Arguments> invalidChoOutsideCases() {
        return Stream.of(
            // 직선 한 칸으로 궁성 밖
            Arguments.of(CHO_CENTER_TOP, new Position(3, 4)),
            Arguments.of(CHO_LEFT_MIDDLE, new Position(1, 2)),
            Arguments.of(CHO_RIGHT_MIDDLE, new Position(1, 6)),

            // 대각선 한 칸으로 궁성 밖
            Arguments.of(CHO_CENTER_TOP, new Position(3, 3)),
            Arguments.of(CHO_CENTER_TOP, new Position(3, 5)),
            Arguments.of(CHO_LEFT_MIDDLE, new Position(2, 2)),
            Arguments.of(CHO_LEFT_MIDDLE, new Position(0, 2)),
            Arguments.of(CHO_RIGHT_MIDDLE, new Position(2, 6)),
            Arguments.of(CHO_RIGHT_MIDDLE, new Position(0, 6))
        );
    }
}