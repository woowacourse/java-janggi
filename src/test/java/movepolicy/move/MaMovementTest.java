package movepolicy.move;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pieces.PieceType;
import pieces.Side;
import position.Position;

class MaMovementTest {

    private static final Position DEFAULT = new Position(3, 3);

    private final Movement movement = PieceType.MA.getMovement();

    @ParameterizedTest
    @EnumSource(Side.class)
    void 전방_1칸_우전방_1칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.rightForwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 전방_1칸_좌전방_1칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.leftForwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 후방_1칸_좌후방_1칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.backDelta())
            .move(side.leftBackDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 후방_1칸_우후방_1칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.backDelta())
            .move(side.rightBackDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 좌_1칸_좌전방_1칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.leftDelta())
            .move(side.leftForwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 좌_1칸_좌후방_1칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.leftDelta())
            .move(side.leftBackDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 우_1칸_우전방_1칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.rightDelta())
            .move(side.rightForwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 우_1칸_우후방_1칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.rightDelta())
            .move(side.rightBackDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 전방_2칸으로_이동할_수_없다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.forwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 우_2칸으로_이동할_수_없다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.rightDelta())
            .move(side.rightDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 출발지와_도착지_사이에는_1칸의_이동_경로만_존재한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.rightForwardDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions).hasSize(1);
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 전방_1칸_좌전방_1칸이_도착지인_경우_전방_1칸_포지션을_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.leftForwardDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        Position interveningPosition = positions.getFirst();
        assertThat(interveningPosition).isEqualTo(departure.move(side.forwardDelta()));
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 전방_1칸_우전방_1칸이_도착지인_경우_전방_1칸_포지션을_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.rightForwardDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        Position interveningPosition = positions.getFirst();
        assertThat(interveningPosition).isEqualTo(departure.move(side.forwardDelta()));
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 후방_1칸_좌후방_1칸이_도착지인_경우_후방_1칸_포지션을_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.backDelta())
            .move(side.leftBackDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        Position interveningPosition = positions.getFirst();
        assertThat(interveningPosition).isEqualTo(departure.move(side.backDelta()));
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 후방_1칸_우후방_1칸이_도착지인_경우_후방_1칸_포지션을_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.backDelta())
            .move(side.rightBackDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        Position interveningPosition = positions.getFirst();
        assertThat(interveningPosition).isEqualTo(departure.move(side.backDelta()));
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 좌_1칸_좌전방_1칸이_도착지인_경우_좌_1칸_포지션을_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.leftDelta())
            .move(side.leftForwardDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        Position interveningPosition = positions.getFirst();
        assertThat(interveningPosition).isEqualTo(departure.move(side.leftDelta()));
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 좌_1칸_좌후방_1칸이_도착지인_경우_좌_1칸_포지션을_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.leftDelta())
            .move(side.leftBackDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        Position interveningPosition = positions.getFirst();
        assertThat(interveningPosition).isEqualTo(departure.move(side.leftDelta()));
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 우_1칸_우전방_1칸이_도착지인_경우_우_1칸_포지션을_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.rightDelta())
            .move(side.rightForwardDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        Position interveningPosition = positions.getFirst();
        assertThat(interveningPosition).isEqualTo(departure.move(side.rightDelta()));
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 우_1칸_우후방_1칸이_도착지인_경우_우_1칸_포지션을_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.rightDelta())
            .move(side.rightBackDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        Position interveningPosition = positions.getFirst();
        assertThat(interveningPosition).isEqualTo(departure.move(side.rightDelta()));
    }
}