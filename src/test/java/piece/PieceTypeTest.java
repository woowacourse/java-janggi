package piece;

import static org.assertj.core.api.Assertions.assertThat;

import board.Position;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PieceTypeTest {

    @Nested
    @DisplayName("피스 타입 생성")
    class Construct {

        @DisplayName("피스 타입을 올바로 생성한다.")
        @Test
        void construct1() {
            // given
            final List<PieceType> expectedType = List.of(
                    PieceType.GENERAL,
                    PieceType.GUARD,
                    PieceType.HORSE,
                    PieceType.ELEPHANT,
                    PieceType.CHARIOT,
                    PieceType.CANNON,
                    PieceType.SOLDIER
            );

            // when
            // then
            assertThat(Arrays.asList(PieceType.values()))
                    .containsAll(expectedType)
                    .hasSize(7);
        }

        @DisplayName("각 피스 타입이 초기 좌표를 가지고 있는 지 확인한다.")
        @Test
        void getInitPositions() {
            // given
            final int expectedSize = 2;
            final PieceType pieceType = PieceType.CANNON;
            final TeamType teamType = TeamType.RED;

            // when
            final List<Position> initPositions = pieceType.getInitPositions(teamType);

            // then
            Assertions.assertThat(initPositions).hasSize(expectedSize);
        }
    }

}
