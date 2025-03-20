package board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
            final Position srcPosition = new Position(1, 1);
            final Position ablePosition = new Position(2, 1);
            final Position unablePosition = new Position(0, 1);

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

        @Test
        @DisplayName("코끼리의 중간 Position들을 계산하여 반환할 수 있다.")
        void calculateElephantMiddlePositions() {
            // given
            final Position srcPosition = new Position(2, 2);
            final Position destPosition = new Position(4, 5);

            final Position actualPosition1 = new Position(2, 3);
            final Position actualPosition2 = new Position(3, 4);
            final List<Position> actual = List.of(
                    actualPosition1, actualPosition2
            );

            // when
            final List<Position> middlePositions = srcPosition.calculateElephantMiddlePositions(destPosition);

            // then
            assertThat(middlePositions).containsAll(actual);
        }
    }
}
