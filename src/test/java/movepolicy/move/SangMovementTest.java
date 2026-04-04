package movepolicy.move;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pieces.PieceType;
import pieces.Side;
import position.Position;

class SangMovementTest {

    private static final Position DEFAULT = new Position(3, 3);

    private final Movement movement = PieceType.SANG.getMovement();

    @ParameterizedTest
    @EnumSource(Side.class)
    void 전방_1칸_우전방_2칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.rightForwardDelta())
            .move(side.rightForwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 전방_1칸_좌전방_2칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.leftForwardDelta())
            .move(side.leftForwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 후방_1칸_우후방_2칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.backDelta())
            .move(side.rightBackDelta())
            .move(side.rightBackDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 후방_1칸_좌후방_2칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.backDelta())
            .move(side.leftBackDelta())
            .move(side.leftBackDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 좌_1칸_좌전방_2칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.leftDelta())
            .move(side.leftForwardDelta())
            .move(side.leftForwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 좌_1칸_좌후방_2칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.leftDelta())
            .move(side.leftBackDelta())
            .move(side.leftBackDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 우_1칸_우전방_2칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.rightDelta())
            .move(side.rightForwardDelta())
            .move(side.rightForwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 우_1칸_우후방_2칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.rightDelta())
            .move(side.rightBackDelta())
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
    void 우전방_1칸으로_이동할_수_없다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure.move(side.rightForwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 출발지와_도착지_사이에는_2칸의_이동_경로만_존재한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.rightForwardDelta())
            .move(side.rightForwardDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions).hasSize(2);
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 전방_1칸_우전방_2칸_이동의_경로를_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.rightForwardDelta())
            .move(side.rightForwardDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions.get(0)).isEqualTo(departure.move(side.forwardDelta()));
        assertThat(positions.get(1)).isEqualTo(
            departure
                .move(side.forwardDelta())
                .move(side.rightForwardDelta())
        );
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 전방_1칸_좌전방_2칸_이동의_경로를_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.leftForwardDelta())
            .move(side.leftForwardDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions.get(0)).isEqualTo(departure.move(side.forwardDelta()));
        assertThat(positions.get(1)).isEqualTo(
            departure
                .move(side.forwardDelta())
                .move(side.leftForwardDelta())
        );
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 후방_1칸_우후방_2칸_이동의_경로를_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.backDelta())
            .move(side.rightBackDelta())
            .move(side.rightBackDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions.get(0)).isEqualTo(departure.move(side.backDelta()));
        assertThat(positions.get(1)).isEqualTo(
            departure
                .move(side.backDelta())
                .move(side.rightBackDelta())
        );
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 후방_1칸_좌후방_2칸_이동의_경로를_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.backDelta())
            .move(side.leftBackDelta())
            .move(side.leftBackDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions.get(0)).isEqualTo(departure.move(side.backDelta()));
        assertThat(positions.get(1)).isEqualTo(
            departure
                .move(side.backDelta())
                .move(side.leftBackDelta())
        );
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 좌_1칸_좌전방_2칸_이동의_경로를_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.leftDelta())
            .move(side.leftForwardDelta())
            .move(side.leftForwardDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions.get(0)).isEqualTo(departure.move(side.leftDelta()));
        assertThat(positions.get(1)).isEqualTo(
            departure
                .move(side.leftDelta())
                .move(side.leftForwardDelta())
        );
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 좌_1칸_좌후방_2칸_이동의_경로를_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.leftDelta())
            .move(side.leftBackDelta())
            .move(side.leftBackDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions.get(0)).isEqualTo(departure.move(side.leftDelta()));
        assertThat(positions.get(1)).isEqualTo(
            departure
                .move(side.leftDelta())
                .move(side.leftBackDelta())
        );
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 우_1칸_우전방_2칸_이동의_경로를_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.rightDelta())
            .move(side.rightForwardDelta())
            .move(side.rightForwardDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions.get(0)).isEqualTo(departure.move(side.rightDelta()));
        assertThat(positions.get(1)).isEqualTo(
            departure
                .move(side.rightDelta())
                .move(side.rightForwardDelta())
        );
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 우_1칸_우후방_2칸_이동의_경로를_반환한다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.rightDelta())
            .move(side.rightBackDelta())
            .move(side.rightBackDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions.get(0)).isEqualTo(departure.move(side.rightDelta()));
        assertThat(positions.get(1)).isEqualTo(
            departure
                .move(side.rightDelta())
                .move(side.rightBackDelta())
        );
    }
}