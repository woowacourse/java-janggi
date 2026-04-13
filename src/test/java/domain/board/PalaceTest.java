package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PalaceTest {
    private final Palace palace = new Palace();

    @Nested
    @DisplayName("궁성 좌표 포함 여부를 확인한다")
    class Contains {

        @Test
        void 위_궁성_좌표를_포함한다() {
            Position position = new Position(8, 4);

            assertThat(palace.contains(position)).isTrue();
        }

        @Test
        void 아래_궁성_좌표를_포함한다() {
            Position position = new Position(1, 4);

            assertThat(palace.contains(position)).isTrue();
        }

        @Test
        void 궁성_밖_좌표는_포함하지_않는다() {
            Position position = new Position(6, 4);

            assertThat(palace.contains(position)).isFalse();
        }
    }

    @Nested
    @DisplayName("궁성 내부 연결을 확인한다")
    class Connection {

        @Test
        void 같은_궁성_내부_상하좌우_한_칸은_연결된다() {
            Position departure = new Position(9, 3);
            Position destination = new Position(9, 4);

            assertThat(palace.isConnected(departure, destination)).isTrue();
        }

        @Test
        void 같은_궁성_내부_대각선_한_칸은_연결된다() {
            Position departure = new Position(9, 3);
            Position destination = new Position(8, 4);

            assertThat(palace.isConnected(departure, destination)).isTrue();
        }

        @Test
        void 같은_궁성_내부라도_직접_연결되지_않은_좌표는_연결되지_않는다() {
            Position departure = new Position(9, 3);
            Position destination = new Position(9, 5);

            assertThat(palace.isConnected(departure, destination)).isFalse();
        }

        @Test
        void 서로_다른_궁성_좌표는_연결되지_않는다() {
            Position departure = new Position(9, 4);
            Position destination = new Position(1, 4);

            assertThat(palace.isConnected(departure, destination)).isFalse();
        }

        @Test
        void 궁성_밖_좌표와는_연결되지_않는다() {
            Position departure = new Position(9, 4);
            Position destination = new Position(6, 4);

            assertThat(palace.isConnected(departure, destination)).isFalse();
        }
    }

    @Nested
    @DisplayName("궁성 내부 대각선 연결을 확인한다")
    class DiagonalConnection {

        @Test
        void 궁성_대각선으로_직접_연결된_좌표는_대각선_연결이다() {
            Position departure = new Position(0, 3);
            Position destination = new Position(1, 4);

            assertThat(palace.isSingleStepDiagonalConnection(departure, destination)).isTrue();
        }

        @Test
        void 상하좌우_연결은_대각선_연결이_아니다() {
            Position departure = new Position(0, 4);
            Position destination = new Position(1, 4);

            assertThat(palace.isSingleStepDiagonalConnection(departure, destination)).isFalse();
        }

        @Test
        void 차와_포는_궁성_대각선_두_칸_축_위에서_연결될_수_있다() {
            Position departure = new Position(0, 3);
            Position destination = new Position(2, 5);

            assertThat(palace.isSlidingDiagonalConnection(departure, destination)).isTrue();
        }

        @Test
        void 차와_포도_궁성_내부지만_대각선_선분이_아닌_경우는_연결되지_않는다() {
            Position departure = new Position(0, 4);
            Position destination = new Position(1, 5);

            assertThat(palace.isSlidingDiagonalConnection(departure, destination)).isFalse();
        }
    }
}
