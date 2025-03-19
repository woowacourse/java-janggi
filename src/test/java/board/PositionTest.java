package board;

import static org.assertj.core.api.Assertions.assertThat;

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
    @DisplayName("포지션 거리 계산")
    class Calculate {

        @DisplayName("두 개의 포지선 거리를 계산한다.")
        @Test
        void calculateDistance() {
            // given
            final double expected = Math.sqrt(2);
            final Position descPosition = new Position(1, 1);
            final Position srcPosition = new Position(2, 2);

            // when
            final double distance = srcPosition.calculateDistance(descPosition);

            // then
            org.assertj.core.api.Assertions.assertThat(distance).isEqualTo(expected);
        }
    }
}
