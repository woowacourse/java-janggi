package movepolicy.move;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import pieces.PieceType;
import pieces.Side;
import position.Position;

class PoMovementTest {

    private static final Position CHO_LEFT_TOP = new Position(2, 3);
    private static final Position CHO_CENTER_TOP = new Position(2, 4);
    private static final Position CHO_RIGHT_TOP = new Position(2, 5);
    private static final Position CHO_LEFT_MIDDLE = new Position(1, 3);
    private static final Position CHO_CENTER = new Position(1, 4);
    private static final Position CHO_RIGHT_MIDDLE = new Position(1, 5);
    private static final Position CHO_LEFT_BOTTOM = new Position(0, 3);
    private static final Position CHO_CENTER_BOTTOM = new Position(0, 4);
    private static final Position CHO_RIGHT_BOTTOM = new Position(0, 5);

    private static final Position DEFAULT = new Position(3, 3);

    private final Movement movement = PieceType.PO.getMovement();

    @ParameterizedTest
    @EnumSource(Side.class)
    void 앞으로_여러_칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.forwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 뒤로_여러_칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.backDelta())
            .move(side.backDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 좌로_여러_칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.leftDelta())
            .move(side.leftDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 우로_여러_칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.rightDelta())
            .move(side.rightDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 대각으로_이동할_수_없다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure.move(side.rightForwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("choGungsungCornerPositions")
    void 초나라는_초의_궁성_중앙에서_모서리로_대각_1칸_이동할_수_있다(final Position destination) {
        // given
        Side side = Side.CHO;
        // when & then
        assertThat(movement.canReach(CHO_CENTER, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("hanGungsungCornerPositions")
    void 초나라는_한의_궁성_중앙에서_모서리로_대각_1칸_이동할_수_있다(final Position destination) {
        // given
        Side side = Side.CHO;
        Position departure = CHO_CENTER.reverse();
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choGungsungCornerPositions")
    void 한나라는_초의_궁성_중앙에서_모서리로_대각_1칸_이동할_수_있다(final Position destination) {
        // given
        Side side = Side.HAN;
        // when & then
        assertThat(movement.canReach(CHO_CENTER, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("hanGungsungCornerPositions")
    void 한나라는_한의_궁성_중앙에서_모서리로_대각_1칸_이동할_수_있다(final Position destination) {
        // given
        Side side = Side.HAN;
        Position departure = CHO_CENTER.reverse();
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choGungsungCornerPositions")
    void 초나라는_초의_궁성_모서리에서_중앙으로_대각_이동할_수_있다(final Position departure) {
        // given
        Side side = Side.CHO;
        // when & then
        assertThat(movement.canReach(departure, CHO_CENTER, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("hanGungsungCornerPositions")
    void 초나라는_한의_궁성_모서리에서_중앙으로_대각_이동할_수_있다(final Position departure) {
        // given
        Side side = Side.CHO;
        Position destination = CHO_CENTER.reverse();
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choGungsungCornerPositions")
    void 한나라는_초의_궁성_모서리에서_중앙으로_대각_이동할_수_있다(final Position departure) {
        // given
        Side side = Side.HAN;
        // when & then
        assertThat(movement.canReach(departure, CHO_CENTER, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("hanGungsungCornerPositions")
    void 한나라는_한의_궁성_모서리에서_중앙으로_대각_이동할_수_있다(final Position departure) {
        // given
        Side side = Side.HAN;
        Position destination = CHO_CENTER.reverse();
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("gunsungDepartureDestination")
    void 초나라는_궁성_끝에서_반대쪽_끝까지_대각_이동할_수_있다(final Position departure, final Position destination) {
        // given
        Side side = Side.CHO;
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("gunsungDepartureDestination")
    void 한나라는_궁성_끝에서_반대쪽_끝까지_대각_이동할_수_있다(final Position departure, final Position destination) {
        // given
        Side side = Side.HAN;
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("invalidChoDiagonalCases")
    void 초나라는_궁성의_직각_면에서는_대각으로_이동할_수_없다(final Position departure, final Position destination) {
        // given
        Side side = Side.CHO;
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("invalidChoDiagonalCases")
    void 한나라는_궁성의_직각_면에서는_대각으로_이동할_수_없다(final Position departure, final Position destination) {
        // given
        Side side = Side.HAN;
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 앞_1칸_차이로_이동할_수_없다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure.move(side.forwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 뒤_1칸_차이로_이동할_수_없다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure.move(side.backDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 좌_1칸_차이로_이동할_수_없다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure.move(side.leftDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 우_1칸_차이로_이동할_수_없다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure.move(side.rightDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("gunsungDepartureDestination")
    void 초나라는_궁성_좌상단에서_우하단_이동의_경로를_반환한다(final Position departure, final Position destination) {
        // when
        Side side = Side.CHO;
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions).hasSize(1);
        assertThat(positions.getFirst()).isEqualTo(CHO_CENTER);
    }

    @ParameterizedTest
    @MethodSource("gunsungDepartureDestination")
    void 한나라는_궁성_좌상단에서_우하단_이동의_경로를_반환한다(final Position departure, final Position destination) {
        // when
        Side side = Side.HAN;
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions).hasSize(1);
        assertThat(positions.getFirst()).isEqualTo(CHO_CENTER.reverse());
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 출발지와_도착지_차이_만큼의_이동_경로가_존재한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.backDelta())
            .move(side.backDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions).hasSize(1);
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 앞으로_여러_칸_차이인_경우의_이동_경로를_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.forwardDelta())
            .move(side.forwardDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions.get(0)).isEqualTo(
            departure.move(side.forwardDelta())
        );
        assertThat(positions.get(1)).isEqualTo(
            departure
                .move(side.forwardDelta())
                .move(side.forwardDelta())
        );
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 뒤로_여러_칸_차이인_경우의_이동_경로를_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.backDelta())
            .move(side.backDelta())
            .move(side.backDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions.get(0)).isEqualTo(
            departure.move(side.backDelta())
        );
        assertThat(positions.get(1)).isEqualTo(
            departure
                .move(side.backDelta())
                .move(side.backDelta())
        );
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 좌로_여러_칸_차이인_경우의_이동_경로를_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.leftDelta())
            .move(side.leftDelta())
            .move(side.leftDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions.get(0)).isEqualTo(
            departure.move(side.leftDelta())
        );
        assertThat(positions.get(1)).isEqualTo(
            departure
                .move(side.leftDelta())
                .move(side.leftDelta())
        );
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 우로_여러_칸_차이인_경우의_이동_경로를_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.rightDelta())
            .move(side.rightDelta())
            .move(side.rightDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions.get(0)).isEqualTo(
            departure.move(side.rightDelta())
        );
        assertThat(positions.get(1)).isEqualTo(
            departure
                .move(side.rightDelta())
                .move(side.rightDelta())
        );
    }

    private static Stream<Position> choGungsungCornerPositions() {
        return Stream.of(
            CHO_LEFT_TOP, CHO_RIGHT_TOP,
            CHO_LEFT_BOTTOM, CHO_RIGHT_BOTTOM
        );
    }

    private static Stream<Position> hanGungsungCornerPositions() {
        return Stream.of(
            CHO_LEFT_TOP.reverse(), CHO_RIGHT_TOP.reverse(),
            CHO_LEFT_BOTTOM.reverse(), CHO_RIGHT_BOTTOM.reverse()
        );
    }

    private static Stream<Arguments> gunsungDepartureDestination() {
        return Stream.of(
            Arguments.of(CHO_LEFT_TOP, CHO_RIGHT_BOTTOM),
            Arguments.of(CHO_RIGHT_TOP, CHO_LEFT_BOTTOM),
            Arguments.of(CHO_LEFT_TOP.reverse(), CHO_RIGHT_BOTTOM.reverse()),
            Arguments.of(CHO_RIGHT_TOP.reverse(), CHO_LEFT_BOTTOM.reverse())
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
            Arguments.of(CHO_CENTER_BOTTOM, CHO_RIGHT_MIDDLE),

            Arguments.of(CHO_CENTER_TOP.reverse(), CHO_LEFT_MIDDLE.reverse()),
            Arguments.of(CHO_CENTER_TOP.reverse(), CHO_RIGHT_MIDDLE.reverse()),
            Arguments.of(CHO_LEFT_MIDDLE.reverse(), CHO_CENTER_TOP.reverse()),
            Arguments.of(CHO_LEFT_MIDDLE.reverse(), CHO_CENTER_BOTTOM.reverse()),
            Arguments.of(CHO_RIGHT_MIDDLE.reverse(), CHO_CENTER_TOP.reverse()),
            Arguments.of(CHO_RIGHT_MIDDLE.reverse(), CHO_CENTER_BOTTOM.reverse()),
            Arguments.of(CHO_CENTER_BOTTOM.reverse(), CHO_LEFT_MIDDLE.reverse()),
            Arguments.of(CHO_CENTER_BOTTOM.reverse(), CHO_RIGHT_MIDDLE.reverse())
        );
    }
}