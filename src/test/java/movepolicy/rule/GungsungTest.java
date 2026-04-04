package movepolicy.rule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import position.Position;

class GungsungTest {

    private static final String METHOD_SOURCE_PREFIX = "movepolicy.rule.GungsungTest#";

    private static final Position CHO_LEFT_TOP = new Position(2, 3);
    private static final Position CHO_CENTER_TOP = new Position(2, 4);
    private static final Position CHO_RIGHT_TOP = new Position(2, 5);
    private static final Position CHO_LEFT_MIDDLE = new Position(1, 3);
    private static final Position CHO_CENTER = new Position(1, 4);
    private static final Position CHO_RIGHT_MIDDLE = new Position(1, 5);
    private static final Position CHO_LEFT_BOTTOM = new Position(0, 3);
    private static final Position CHO_CENTER_BOTTOM = new Position(0, 4);
    private static final Position CHO_RIGHT_BOTTOM = new Position(0, 5);

    private static final Position OUTSIDE_TOP = new Position(3, 4);
    private static final Position OUTSIDE_LEFT = new Position(1, 2);
    private static final Position OUTSIDE_RIGHT = new Position(1, 6);
    private static final Position OUTSIDE_BOTTOM = new Position(6, 4);

    private final Gungsung gungsung = new Gungsung();

    @Nested
    @DisplayName("초 궁성 범위를 검증한다")
    class IsChoRange {

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "choGungsungPositions")
        void 초_궁성_내부_좌표를_포함하는_경우_TRUE를_반환한다(final Position position) {
            // when
            boolean contains = gungsung.isChoRange(position);
            // then
            assertThat(contains).isTrue();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "outsideChoGungsungPositions")
        void 초_궁성_범위를_벗어나면_FALSE를_반환한다(final Position position) {
            // when
            boolean contains = gungsung.isChoRange(position);
            // then
            assertThat(contains).isFalse();
        }
    }

    @Nested
    @DisplayName("한 궁성 범위를 검증한다")
    class IsHanRange {

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "choGungsungPositions")
        void 한_궁성_내부_좌표인_경우_TRUE를_반환한다(final Position choPosition) {
            // given
            Position hanPosition = choPosition.reverse();
            // when
            boolean contains = gungsung.isHanRange(hanPosition);
            // then
            assertThat(contains).isTrue();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "outsideChoGungsungPositions")
        void 한_궁성_범위를_벗어나는_경우_false를_반환한다(final Position choPosition) {
            // given
            Position hanPosition = choPosition.reverse();
            // when
            boolean contains = gungsung.isHanRange(hanPosition);
            // then
            assertThat(contains).isFalse();
        }
    }

    @Nested
    @DisplayName("궁성 내 대각선 한 칸 관계인지 검증한다")
    class IsDiagonalInside {

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "diagonalCornersFromCenter")
        void 궁성_가운데와_모서리는_대각선_한_칸_관계이다(final Position corner) {
            // when
            boolean isDiagonalInside = gungsung.isOneStepDiagonalInside(CHO_CENTER, corner);
            // then
            assertThat(isDiagonalInside).isTrue();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "oneStepStraightFromCenter")
        void 궁성_안이라도_직선_한_칸인_경우_FALSE를_반환한다(final Position position) {
            // when
            boolean isDiagonalInside = gungsung.isOneStepDiagonalInside(CHO_CENTER, position);
            // then
            assertThat(isDiagonalInside).isFalse();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "twoStepDiagonalInside")
        void 궁성_안이라도_대각선_두_칸인_경우_FALSE를_반환한다(final Position position) {
            // when
            boolean isDiagonalInside = gungsung.isOneStepDiagonalInside(CHO_LEFT_TOP, position);
            // then
            assertThat(isDiagonalInside).isFalse();
        }

        @ParameterizedTest
        @MethodSource(METHOD_SOURCE_PREFIX + "outsidePositionsAroundCenter")
        void 궁성_밖_좌표인_경우_FALSE를_반환한다(final Position outside) {
            // when
            boolean isDiagonalInside = gungsung.isOneStepDiagonalInside(CHO_CENTER, outside);
            // then
            assertThat(isDiagonalInside).isFalse();
        }
    }

    private static Stream<Position> choGungsungPositions() {
        return Stream.of(
            CHO_LEFT_TOP, CHO_CENTER_TOP, CHO_RIGHT_TOP,
            CHO_LEFT_MIDDLE, CHO_CENTER, CHO_RIGHT_MIDDLE,
            CHO_LEFT_BOTTOM, CHO_CENTER_BOTTOM, CHO_RIGHT_BOTTOM
        );
    }

    private static Stream<Position> outsideChoGungsungPositions() {
        return Stream.of(
            OUTSIDE_TOP,
            OUTSIDE_LEFT,
            OUTSIDE_RIGHT,
            OUTSIDE_BOTTOM
        );
    }

    private static Stream<Position> diagonalCornersFromCenter() {
        return Stream.of(
            CHO_LEFT_TOP,
            CHO_RIGHT_TOP,
            CHO_LEFT_BOTTOM,
            CHO_RIGHT_BOTTOM
        );
    }

    private static Stream<Position> oneStepStraightFromCenter() {
        return Stream.of(
            CHO_CENTER_TOP,
            CHO_LEFT_MIDDLE,
            CHO_RIGHT_MIDDLE,
            CHO_CENTER_BOTTOM
        );
    }

    private static Stream<Position> twoStepDiagonalInside() {
        return Stream.of(
            CHO_RIGHT_BOTTOM
        );
    }

    private static Stream<Position> outsidePositionsAroundCenter() {
        return Stream.of(
            OUTSIDE_TOP,
            OUTSIDE_LEFT,
            OUTSIDE_RIGHT
        );
    }
}