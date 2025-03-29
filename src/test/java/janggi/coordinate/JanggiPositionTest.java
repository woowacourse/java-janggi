package janggi.coordinate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import janggi.piece.Country;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class JanggiPositionTest {


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
            final JanggiPosition janggiPosition = new JanggiPosition(x, y);

            // then
            assertSoftly(s -> {
                s.assertThat(janggiPosition.x()).isEqualTo(x);
                s.assertThat(janggiPosition.y()).isEqualTo(y);
            });
        }

        @DisplayName("장기 판의 범위가 벗어나면 예외가 발생한다.")
        @Test
        void construct2() {
            // given
            final int x = 0;
            final int y = 1;

            // when & then
            assertThatThrownBy(() -> {
                new JanggiPosition(x, y);
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
            final JanggiPosition srcJanggiPosition = new JanggiPosition(2, 2);
            final JanggiPosition descJanggiPosition = new JanggiPosition(1, 1);

            // when
            final double distance = srcJanggiPosition.calculateDistance(descJanggiPosition);

            // then
            assertThat(distance).isEqualTo(expected);
        }

        @DisplayName("두 개의 포지션이 같은 라인 내에 있는 지 계산한다.")
        @ParameterizedTest
        @CsvSource(value = {"1:5:true", "2:2:false"}, delimiter = ':')
        void isSameLine(final int x, final int y, final boolean expected) {
            // given
            final JanggiPosition srcJanggiPosition = new JanggiPosition(1, 1);
            final JanggiPosition target = new JanggiPosition(x, y);

            // when
            final boolean actual = srcJanggiPosition.isSameLine(target);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @DisplayName("x가 더 큰지 검사할 수 있다.")
        @ParameterizedTest
        @CsvSource(value = {"2:1:true", "4:1:false"}, delimiter = ':')
        void isXGreaterThan(final int x, final int y, final boolean expected) {
            // given
            final JanggiPosition srcJanggiPosition = new JanggiPosition(3, 1);
            final JanggiPosition target = new JanggiPosition(x, y);

            // when
            final boolean actual = srcJanggiPosition.isXGreaterThan(target);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @DisplayName("x가 더 작거나 같은지 검사할 수 있다.")
        @ParameterizedTest
        @CsvSource(value = {"3:1:true", "1:1:false"}, delimiter = ':')
        void isXLessThan(final int x, final int y, final boolean expected) {
            // given
            final JanggiPosition srcJanggiPosition = new JanggiPosition(2, 1);
            final JanggiPosition target = new JanggiPosition(x, y);

            // when
            final boolean actual = srcJanggiPosition.isXLessThan(target);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("직선상의 src, dest 사이의 position을 반환한다.(src, dest은 포함하지 않는다.)")
        void calculateBetweenPositions() {
            // given
            final JanggiPosition srcJanggiPosition = new JanggiPosition(1, 1);
            final JanggiPosition destJanggiPosition = new JanggiPosition(5, 1);

            // when
            final List<JanggiPosition> betweenJanggiPositions = srcJanggiPosition.calculateBetweenPositions(
                    destJanggiPosition);

            // then
            assertThat(betweenJanggiPositions)
                    .hasSize(3)
                    .contains(new JanggiPosition(2, 1))
                    .contains(new JanggiPosition(3, 1))
                    .contains(new JanggiPosition(4, 1));
        }

        @DisplayName("Position에 대한 덧셈 연산")
        @Test
        void plusPosition() {
            // given
            final JanggiPosition janggiPosition = new JanggiPosition(1, 1);

            // when
            final JanggiPosition actual = janggiPosition.plusPosition(3, 2);

            // then
            assertThat(actual).isEqualTo(new JanggiPosition(4, 3));
        }
    }

    @Nested
    @DisplayName("포지션 검증")
    class Is {

        @DisplayName("Country에 따라, Position이 궁성 내부인지 검증한다.")
        @ParameterizedTest
        @MethodSource
        void isInsidePalace(final JanggiPosition janggiPosition, final Country country) {
            // given & when
            final boolean actual = janggiPosition.isInsidePalace(country);

            // then
            assertThat(actual).isTrue();
        }

        static Stream<Arguments> isInsidePalace() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(1, 4), Country.HAN),
                    Arguments.of(new JanggiPosition(1, 5), Country.HAN),
                    Arguments.of(new JanggiPosition(1, 6), Country.HAN),
                    Arguments.of(new JanggiPosition(2, 4), Country.HAN),
                    Arguments.of(new JanggiPosition(2, 5), Country.HAN),
                    Arguments.of(new JanggiPosition(2, 6), Country.HAN),
                    Arguments.of(new JanggiPosition(3, 4), Country.HAN),
                    Arguments.of(new JanggiPosition(3, 5), Country.HAN),
                    Arguments.of(new JanggiPosition(3, 6), Country.HAN),
                    Arguments.of(new JanggiPosition(8, 4), Country.CHO),
                    Arguments.of(new JanggiPosition(8, 5), Country.CHO),
                    Arguments.of(new JanggiPosition(8, 6), Country.CHO),
                    Arguments.of(new JanggiPosition(9, 4), Country.CHO),
                    Arguments.of(new JanggiPosition(9, 5), Country.CHO),
                    Arguments.of(new JanggiPosition(9, 6), Country.CHO),
                    Arguments.of(new JanggiPosition(10, 4), Country.CHO),
                    Arguments.of(new JanggiPosition(10, 5), Country.CHO),
                    Arguments.of(new JanggiPosition(10, 6), Country.CHO)
            );
        }

        @DisplayName("Country에 따라, position이 궁성 모서리인지 검증한다.")
        @ParameterizedTest
        @MethodSource
        void isCornerInPalace(final JanggiPosition janggiPosition, final Country country) {
            // given & when
            final boolean actual = janggiPosition.isCornerInPalace(country);

            // then
            assertThat(actual).isTrue();
        }

        static Stream<Arguments> isCornerInPalace() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(1, 4), Country.HAN),
                    Arguments.of(new JanggiPosition(1, 6), Country.HAN),
                    Arguments.of(new JanggiPosition(3, 4), Country.HAN),
                    Arguments.of(new JanggiPosition(3, 6), Country.HAN),
                    Arguments.of(new JanggiPosition(8, 4), Country.CHO),
                    Arguments.of(new JanggiPosition(8, 6), Country.CHO),
                    Arguments.of(new JanggiPosition(10, 4), Country.CHO),
                    Arguments.of(new JanggiPosition(10, 6), Country.CHO)
            );
        }
    }
}
