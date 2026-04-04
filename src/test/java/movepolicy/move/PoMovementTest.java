package movepolicy.move;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pieces.PieceType;
import pieces.Side;
import position.Position;

class PoMovementTest {

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
    void 앞_1칸_차이인_경우_이동_경로를_반환하지_않는다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure.move(side.forwardDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions).isEmpty();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 뒤_1칸_차이인_경우_이동_경로를_반환하지_않는다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure.move(side.backDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions).isEmpty();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 좌_1칸_차이인_경우_이동_경로를_반환하지_않는다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure.move(side.leftDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions).isEmpty();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 우_1칸_차이인_경우_이동_경로를_반환하지_않는다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure.move(side.rightDelta());
        // when
        List<Position> positions = movement.findPathPositions(departure, destination, side);
        // then
        assertThat(positions).isEmpty();
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
}