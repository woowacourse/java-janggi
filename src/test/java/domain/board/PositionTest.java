package domain.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PositionTest {

    @Nested
    class XCoordinate {
        @Test
        @DisplayName("X 좌표가 1 미만일 경우 예외가 발생한다.")
        void throwException_When_XCoordinateLessThanZero() {
            assertThatThrownBy(() -> new Position(0, 1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] x 좌표가 올바르지 않습니다.");
        }

        @Test
        @DisplayName("X 좌표가 1인 경우 생성된 위치의 X 좌표는 1이다.")
        void generate_MinXCoordinate() {
            Position position = new Position(1, 1);

            assertThat(position.x()).isEqualTo(1);
        }

        @Test
        @DisplayName("X 좌표가 9 초과인 경우 예외가 발생한다.")
        void throwException_When_XCoordinateMoreThanNine() {
            assertThatThrownBy(() -> new Position(10, 1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] x 좌표가 올바르지 않습니다.");
        }

        @Test
        @DisplayName("X 좌표가 9인 경우 생성된 위치의 X 좌표는 9이다.")
        void generate_MaxXCoordinate() {
            Position position = new Position(9, 1);

            assertThat(position.x()).isEqualTo(9);
        }
    }

    @Nested
    class YCoordinate {
        @Test
        @DisplayName("Y 좌표가 1 미만일 경우 예외가 발생한다.")
        void throwException_When_Y_CoordinateLessThanZero() {
            assertThatThrownBy(() -> new Position(1, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] y 좌표가 올바르지 않습니다.");
        }

        @Test
        @DisplayName("Y 좌표가 1인 경우 생성된 위치의 Y 좌표는 1이다.")
        void generate_Min_Y_Coordinate() {
            Position position = new Position(1, 1);

            assertThat(position.y()).isEqualTo(1);
        }


        @Test
        @DisplayName("Y 좌표가 10 초과인 경우 예외가 발생한다.")
        void throwException_When_Y_CoordinateMoreThanNine() {
            assertThatThrownBy(() -> new Position(1, 11))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] y 좌표가 올바르지 않습니다.");
        }

        @Test
        @DisplayName("Y 좌표가 10인 경우 생성된 위치의 Y 좌표는 10이다.")
        void generate_Max_Y_Coordinate() {
            Position position = new Position(1, 10);

            assertThat(position.y()).isEqualTo(10);
        }
    }

    @Test
    @DisplayName("도착지점을 받으면 도착위치와 현재위치의 x좌표 차를 구한다.")
    void xCoordinateDifference_When_ReceiveDestination() {
        Position from = new Position(1, 2);
        Position to = new Position(6, 2);

        int dx = from.calculateDx(to);

        assertThat(dx).isEqualTo(5);
    }

    @Test
    @DisplayName("도착지점을 받으면 도착위치와 현재위치의 y좌표 차를 구한다.")
    void yCoordinateDifference_When_ReceiveDestination() {
        Position from = new Position(1, 2);
        Position to = new Position(1, 6);

        int dy = from.calculateDy(to);

        assertThat(dy).isEqualTo(4);
    }

    @Test
    @DisplayName("도착위치와 출발위치 사이에 존재하는 위치를 리스트로 반환한다.")
    void returnListOfLocation_Between_DestinationAndStart() {
        Position from = new Position(1, 2);
        Position to = new Position(1, 6);

        List<Position> intermediatePositions = from.findOrthogonalPath(to);

        assertThat(intermediatePositions.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("도착위치와 출발위치의 좌표가 선형이지 않은 경우 예외를 발생한다.")
    void throwException_When_StratPosition_DifferTo_DestinationPosition() {
        Position from = new Position(3, 2);
        Position to = new Position(1, 6);

        assertThatThrownBy(() -> from.findOrthogonalPath(to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 좌표로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("두 좌표 사이의 모든 중간 위치를 올바르게 반환한다.")
    void returnCorrectIntermediatePositions() {
        Position from = new Position(1, 2);
        Position to = new Position(1, 6);

        List<Position> path = from.findOrthogonalPath(to);

        assertThat(path).containsExactly(
                new Position(1, 3),
                new Position(1, 4),
                new Position(1, 5)
        );
    }
}
