package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PalaceTest {
    private final Palace palace = new Palace();

    @Nested
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
}
