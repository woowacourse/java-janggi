package janggi.domain.rule;

import janggi.domain.Location;
import janggi.domain.Side;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class JolbyeongMovementTest {

    @Nested
    class CalculateRouteTest {
        @Test
        @DisplayName("한팀 졸병이 이동할 수 있는 위치로 경로를 계산하면, 이동 경로를 반환한다.")
        void shouldReturnRouteForReachableLocationWhenTeamHan() {
            // given
            Location from = new Location(0, 0);
            Location to = new Location(1, 0);
            JolbyeongMovement movement = JolbyeongMovement.getInstanceBySide(Side.HAN);

            // when & then
            Assertions.assertThat(movement.calculateRoute(from, to)).hasValue(List.of(to));
        }

        @Test
        @DisplayName("초팀 졸병이 이동할 수 있는 위치로 경로를 계산하면, 이동 경로를 반환한다.")
        void shouldReturnRouteForReachableLocationWhenTeamCho() {
            // given
            Location from = new Location(1, 0);
            Location to = new Location(0, 0);
            JolbyeongMovement movement = JolbyeongMovement.getInstanceBySide(Side.CHO);

            // when & then
            Assertions.assertThat(movement.calculateRoute(from, to)).hasValue(List.of(to));
        }

        @Test
        @DisplayName("한팀 졸병이 이동할 수 없는 위치로 경로를 계산하면, 빈 결과를 반환한다.")
        void shouldReturnEmptyForUnReachableLocationWhenTeamHan() {
            // given
            Location from = new Location(1, 0);
            Location to = new Location(0, 0);
            JolbyeongMovement movement = JolbyeongMovement.getInstanceBySide(Side.HAN);

            // when & then
            Assertions.assertThat(movement.calculateRoute(from, to)).isEmpty();
        }

        @ParameterizedTest
        @DisplayName("초팀 졸병이 이동할 수 없는 위치로 경로를 계산하면, 빈 결과를 반환한다.")
        @MethodSource("provideUnreachableCoordination")
        void shouldReturnEmptyForUnReachableLocationWhenTeamCho(Location destination) {
            // given
            Location from = new Location(0, 0);
            JolbyeongMovement movement = JolbyeongMovement.getInstanceBySide(Side.CHO);

            // when & then
            Assertions.assertThat(movement.calculateRoute(from, destination)).isEmpty();
        }

        static List<Location> provideUnreachableCoordination() {
            return List.of(
                    new Location(2,0), // 거리가 멀어서 도달할 수 없는 경우
                    new Location(1,1), // 대각선으로 이동하는 경우
                    new Location(1,0) // 뒤로 이동하는 경우
            );
        }
    }
}
