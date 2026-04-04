package pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import position.Position;

class GungTest {

    private static final String METHOD_SOURCE_PREFIX = "pieces.GungTest#";

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

    @Nested
    @DisplayName("궁의 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class CanMove {

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "choGungsungPositionsForForwardStep")
        void 초나라는_궁성_내에서_앞_1칸_이동할_수_있다(final Position departure) {
            // given
            Side side = Side.CHO;
            Piece piece = new Piece(side, PieceType.GUNG);
            Position destination = departure.move(side.forwardDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "choGungsungPositionsForForwardStep")
        void 한나라는_궁성_내에서_앞_1칸_이동할_수_있다(final Position choDeparture) {
            // given
            Side side = Side.HAN;
            Position departure = choDeparture.reverse();
            Piece piece = new Piece(side, PieceType.GUNG);
            Position destination = departure.move(side.forwardDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "choGungsungPositionsForBackStep")
        void 초나라는_궁성_내에서_뒤_1칸_이동할_수_있다(final Position departure) {
            // given
            Side side = Side.CHO;
            Piece piece = new Piece(side, PieceType.GUNG);
            Position destination = departure.move(side.backDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "choGungsungPositionsForBackStep")
        void 한나라는_궁성_내에서_뒤_1칸_이동할_수_있다(final Position choDeparture) {
            // given
            Side side = Side.HAN;
            Position departure = choDeparture.reverse();
            Piece piece = new Piece(side, PieceType.GUNG);
            Position destination = departure.move(side.backDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "choGungsungPositionsForLeftStep")
        void 초나라는_궁성_내에서_좌_1칸_이동할_수_있다(final Position departure) {
            // given
            Side side = Side.CHO;
            Piece piece = new Piece(side, PieceType.GUNG);
            Position destination = departure.move(side.leftDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "choGungsungPositionsForLeftStep")
        void 한나라는_궁성_내에서_좌_1칸_이동할_수_있다(final Position choDeparture) {
            // given
            Side side = Side.HAN;
            Piece piece = new Piece(side, PieceType.GUNG);
            Position departure = choDeparture.reverse();
            Position destination = departure.move(side.leftDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "choGungsungPositionsForRightStep")
        void 초나라는_궁성_내에서_우_1칸_이동할_수_있다(final Position departure) {
            // given
            Side side = Side.CHO;
            Piece piece = new Piece(side, PieceType.GUNG);
            Position destination = departure.move(side.rightDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "choGungsungPositionsForRightStep")
        void 한나라는_궁성_내에서_우_1칸_이동할_수_있다(final Position choDeparture) {
            // given
            Side side = Side.HAN;
            Position departure = choDeparture.reverse();
            Piece piece = new Piece(side, PieceType.GUNG);
            Position destination = departure.move(side.rightDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "choGungsungPositionsForDiagonalStep")
        void 초나라는_궁성_중앙에서_대각_이동할_수_있다(final Position destination) {
            // given
            Side side = Side.CHO;
            Piece piece = new Piece(side, PieceType.GUNG);
            // when & then
            assertThatCode(() -> piece.validateDestination(CHO_CENTER, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "choGungsungPositionsForDiagonalStep")
        void 한나라는_궁성_중앙에서_대각_이동할_수_있다(final Position choDestination) {
            // given
            Side side = Side.HAN;
            Position destination = choDestination.reverse();
            Piece piece = new Piece(side, PieceType.GUNG);
            Position departure = CHO_CENTER.reverse();
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "choGungsungPositionsForDiagonalStep")
        void 초나라는_궁성_모서리에서_궁성_중앙으로_대각_이동할_수_있다(final Position departure) {
            // given
            Side side = Side.CHO;
            Piece piece = new Piece(side, PieceType.GUNG);
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, CHO_CENTER))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "choGungsungPositionsForDiagonalStep")
        void 한나라는_궁성_모서리에서_궁성_중앙으로_대각_이동할_수_있다(final Position choDeparture) {
            // given
            Side side = Side.CHO;
            Position departure = choDeparture.reverse();
            Piece piece = new Piece(side, PieceType.GUNG);
            Position destination = CHO_CENTER.reverse();
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "invalidChoDiagonalCases")
        void 초나라는_궁성의_직각_면에서_대각_이동할_수_없다(final Position departure, final Position destination) {
            // given
            Side side = Side.CHO;
            Piece piece = new Piece(side, PieceType.GUNG);
            // when & then
            assertThatThrownBy(() -> piece.validateDestination(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "invalidChoDiagonalCases")
        void 한나라는_궁성의_직각_면에서_대각이_도착지인_경우_예외를_던진다(final Position choDeparture, final Position choDestination) {
            // given
            Side side = Side.HAN;
            Position departure = choDeparture.reverse();
            Position destination = choDestination.reverse();
            Piece piece = new Piece(side, PieceType.GUNG);
            // when & then
            assertThatThrownBy(() -> piece.validateDestination(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "invalidChoOutsideCases")
        void 초나라는_궁성을_벗어날_수_없다(final Position departure, final Position destination) {
            // given
            Side side = Side.CHO;
            Piece piece = new Piece(side, PieceType.GUNG);
            // when & then
            assertThatThrownBy(() -> piece.validateDestination(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "invalidChoOutsideCases")
        void 한나라는_궁성을_벗어날_수_없다(final Position choDeparture, final Position choDestination) {
            // given
            Side side = Side.HAN;
            Position departure = choDeparture.reverse();
            Position destination = choDestination.reverse();
            Piece piece = new Piece(side, PieceType.GUNG);
            // when & then
            assertThatThrownBy(() -> piece.validateDestination(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 출발지와_도착지_사이에는_이동경로가_존재하지_않는다() {
        // given
        Side side = Side.CHO;
        Piece piece = new Piece(side, PieceType.GUNG);
        Position departure = DEFAULT;
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