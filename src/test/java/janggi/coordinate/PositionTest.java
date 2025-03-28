package janggi.coordinate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.piece.Country;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PositionTest {


    @Nested
    @DisplayName("포지션 생성")
    class Construct {

        @DisplayName("포지션을 생성하면 주어진 값으로 초기화된다.")
        @Test
        void construct1() {
            // given
            final int x = 1;
            final int y = 1;

            // when
            final var p = new Position(x, y);

            // then
            Assertions.assertAll(
                    () -> assertThat(p.x()).isEqualTo(x),
                    () -> assertThat(p.y()).isEqualTo(y)
            );
        }

        @DisplayName("포지션의 범위가 벗어나면 예외가 발생한다.")
        @Test
        void construct2() {
            // given
            final int x = 0;
            final int y = 1;

            // when & then
            assertThatThrownBy(() -> {
                new Position(x, y);
            }).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("포지션 계산")
    class Calculate {

        @DisplayName("두 개의 포지선 거리를 계산한다.")
        @Test
        void calculateDistance() {
            // given
            final double expected = Math.sqrt(2);
            final Position srcPosition = new Position(2, 2);
            final Position descPosition = new Position(1, 1);

            // when
            final double distance = srcPosition.calculateDistance(descPosition);

            // then
            org.assertj.core.api.Assertions.assertThat(distance).isEqualTo(expected);
        }

        @DisplayName("두 개의 포지션이 같은 라인 내에 있는 지 계산한다.")
        @Test
        void isSameLine() {
            // given
            final Position srcPosition = new Position(1, 1);
            final Position ablePosition = new Position(1, 5);
            final Position unablePosition = new Position(2, 2);

            // when
            final boolean ableResult = srcPosition.isSameLine(ablePosition);
            final boolean unableResult = srcPosition.isSameLine(unablePosition);

            // then
            Assertions.assertAll(
                    () -> org.assertj.core.api.Assertions.assertThat(ableResult).isTrue(),
                    () -> org.assertj.core.api.Assertions.assertThat(unableResult).isFalse()
            );
        }

        @DisplayName("x가 더 큰지 검사할 수 있다.")
        @Test
        void isXGreaterThan() {
            // given
            final Position srcPosition = new Position(3, 1);
            final Position ablePosition = new Position(2, 1);
            final Position unablePosition = new Position(4, 1);

            // when
            final boolean ableResult = srcPosition.isXGreaterThan(ablePosition);
            final boolean unableResult = srcPosition.isXGreaterThan(unablePosition);

            // then
            Assertions.assertAll(
                    () -> org.assertj.core.api.Assertions.assertThat(ableResult).isTrue(),
                    () -> org.assertj.core.api.Assertions.assertThat(unableResult).isFalse()
            );
        }

        @DisplayName("x가 더 작거나 같은지 검사할 수 있다.")
        @Test
        void isXLessThan() {
            // given
            final Position srcPosition = new Position(2, 1);
            final Position ablePosition = new Position(3, 1);
            final Position unablePosition = new Position(1, 1);

            // when
            final boolean ableResult = srcPosition.isXLessThan(ablePosition);
            final boolean unableResult = srcPosition.isXLessThan(unablePosition);

            // then
            Assertions.assertAll(
                    () -> org.assertj.core.api.Assertions.assertThat(ableResult).isTrue(),
                    () -> org.assertj.core.api.Assertions.assertThat(unableResult).isFalse()
            );
        }

        @Test
        @DisplayName("같은 라인의 position간의 모든 position을 반환한다.")
        void calculateBetweenPositions() {
            // given
            final Position srcPosition = new Position(1, 1);
            final Position destPosition = new Position(5, 1);

            // when
            final List<Position> betweenPositions = srcPosition.calculateBetweenPositions(destPosition);

            // then
            assertThat(betweenPositions).hasSize(3);
        }

        @DisplayName("Position에 대한 덧셈 연산")
        @Test
        void plusPosition() {
            // given
            final Position position = new Position(1, 1);

            // when
            final Position actual = position.plusPosition(3, 2);

            // then
            assertThat(actual).isEqualTo(new Position(4, 3));
        }
    }

    @Nested
    @DisplayName("포지션 검증")
    class Is {

        @DisplayName("Country에 따라, Position이 궁성 내부인지 검증한다.")
        @ParameterizedTest
        @MethodSource
        void isInsidePalace(final Position position, final Country country) {
            // given & when
            final boolean actual = position.isInsidePalace(country);

            // then
            assertThat(actual).isTrue();
        }

        static Stream<Arguments> isInsidePalace() {
            return Stream.of(
                    Arguments.of(new Position(1, 4), Country.HAN),
                    Arguments.of(new Position(1, 5), Country.HAN),
                    Arguments.of(new Position(1, 6), Country.HAN),
                    Arguments.of(new Position(2, 4), Country.HAN),
                    Arguments.of(new Position(2, 5), Country.HAN),
                    Arguments.of(new Position(2, 6), Country.HAN),
                    Arguments.of(new Position(3, 4), Country.HAN),
                    Arguments.of(new Position(3, 5), Country.HAN),
                    Arguments.of(new Position(3, 6), Country.HAN),
                    Arguments.of(new Position(8, 4), Country.CHO),
                    Arguments.of(new Position(8, 5), Country.CHO),
                    Arguments.of(new Position(8, 6), Country.CHO),
                    Arguments.of(new Position(9, 4), Country.CHO),
                    Arguments.of(new Position(9, 5), Country.CHO),
                    Arguments.of(new Position(9, 6), Country.CHO),
                    Arguments.of(new Position(10, 4), Country.CHO),
                    Arguments.of(new Position(10, 5), Country.CHO),
                    Arguments.of(new Position(10, 6), Country.CHO)
            );
        }

        @DisplayName("Country에 따라, position이 궁성 중심인지 검증한다.")
        @ParameterizedTest
        @MethodSource
        void isCenterInPalace(final Position position, final Country country) {
            // given & when
            final boolean actual = position.isCenterInPalace(country);

            // then
            assertThat(actual).isTrue();
        }

        static Stream<Arguments> isCenterInPalace() {
            return Stream.of(
                    Arguments.of(new Position(2, 5), Country.HAN),
                    Arguments.of(new Position(9, 5), Country.CHO)
            );
        }

        @DisplayName("Country에 따라, position이 궁성 모서리인지 검증한다.")
        @ParameterizedTest
        @MethodSource
        void isCornerInPalace(final Position position, final Country country) {
            // given & when
            final boolean actual = position.isCornerInPalace(country);

            // then
            assertThat(actual).isTrue();
        }

        static Stream<Arguments> isCornerInPalace() {
            return Stream.of(
                    Arguments.of(new Position(1, 4), Country.HAN),
                    Arguments.of(new Position(1, 6), Country.HAN),
                    Arguments.of(new Position(3, 4), Country.HAN),
                    Arguments.of(new Position(3, 6), Country.HAN),
                    Arguments.of(new Position(8, 4), Country.CHO),
                    Arguments.of(new Position(8, 6), Country.CHO),
                    Arguments.of(new Position(10, 4), Country.CHO),
                    Arguments.of(new Position(10, 6), Country.CHO)
            );
        }
    }
}
