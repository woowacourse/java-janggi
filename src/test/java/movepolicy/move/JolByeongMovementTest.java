package movepolicy.move;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;
import position.Position;

class JolByeongMovementTest {

    private static final Position DEFAULT = new Position(3, 3);

    private static final Position CHO_ENEMY_LEFT_TOP = new Position(9, 3);
    private static final Position CHO_ENEMY_CENTER_TOP = new Position(9, 4);
    private static final Position CHO_ENEMY_RIGHT_TOP = new Position(9, 5);
    private static final Position CHO_ENEMY_LEFT_MIDDLE = new Position(8, 3);
    private static final Position CHO_ENEMY_CENTER = new Position(8, 4);
    private static final Position CHO_ENEMY_RIGHT_MIDDLE = new Position(8, 5);
    private static final Position CHO_ENEMY_LEFT_BOTTOM = new Position(7, 3);
    private static final Position CHO_ENEMY_CENTER_BOTTOM = new Position(7, 4);
    private static final Position CHO_ENEMY_RIGHT_BOTTOM = new Position(7, 5);

    private final Movement movement = PieceType.JOL_BYEONG.getMovement();

    @ParameterizedTest
    @EnumSource(Side.class)
    void 앞_1칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure.move(side.forwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 좌_1칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure.move(side.leftDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 우_1칸_이동할_수_있다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure.move(side.rightDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 앞_2칸은_이동할_수_없다(final Side side) {
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
    void 일반_위치에서는_대각선으로_이동할_수_없다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure.move(side.rightForwardDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 뒤_1칸은_이동할_수_없다(final Side side) {
        // given
        Position departure = DEFAULT;
        Position destination = departure.move(side.backDelta());
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("choEnemyPalaceDiagonalCases")
    void 초나라는_상대_궁성에서_전방_대각선으로_이동할_수_있다(final Position departure, final Position destination) {
        // given
        Side side = Side.CHO;
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choEnemyPalaceDiagonalCases")
    void 한나라는_상대_궁성에서_전방_대각선으로_이동할_수_있다(final Position choDeparture, final Position choDestination) {
        // given
        Side side = Side.HAN;
        Position departure = choDeparture.reverse();
        Position destination = choDestination.reverse();
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("choEnemyPalaceInvalidBackwardDiagonalCases")
    void 초나라는_상대_궁성에서도_후방_대각선으로는_이동할_수_없다(final Position departure, final Position destination) {
        // given
        Side side = Side.CHO;
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("choEnemyPalaceInvalidBackwardDiagonalCases")
    void 한나라는_상대_궁성에서도_후방_대각선으로는_이동할_수_없다(final Position choDeparture, final Position choDestination) {
        // given
        Side side = Side.HAN;
        Position departure = choDeparture.reverse();
        Position destination = choDestination.reverse();
        // when & then
        assertThat(movement.canReach(departure, destination, side)).isFalse();
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 출발지와_도착지_사이에는_이동경로가_존재하지_않는다(final Side side) {
        // given
        Piece piece = new Piece(side, PieceType.JOL_BYEONG);
        Position departure = DEFAULT;
        Position destination = departure.move(side.forwardDelta());
        // when
        List<Position> positions = piece.findPathPositions(departure, destination);
        // then
        assertThat(positions).hasSize(0);
    }

    private static Stream<Arguments> choEnemyPalaceDiagonalCases() {
        return Stream.of(
            Arguments.of(CHO_ENEMY_LEFT_BOTTOM, CHO_ENEMY_CENTER),
            Arguments.of(CHO_ENEMY_CENTER, CHO_ENEMY_LEFT_TOP),
            Arguments.of(CHO_ENEMY_CENTER, CHO_ENEMY_RIGHT_TOP),
            Arguments.of(CHO_ENEMY_RIGHT_BOTTOM, CHO_ENEMY_CENTER)
        );
    }

    private static Stream<Arguments> choEnemyPalaceInvalidBackwardDiagonalCases() {
        return Stream.of(
            Arguments.of(CHO_ENEMY_LEFT_TOP, CHO_ENEMY_CENTER),
            Arguments.of(CHO_ENEMY_CENTER, CHO_ENEMY_LEFT_BOTTOM),
            Arguments.of(CHO_ENEMY_CENTER, CHO_ENEMY_RIGHT_BOTTOM),
            Arguments.of(CHO_ENEMY_RIGHT_TOP, CHO_ENEMY_CENTER)
        );
    }
}