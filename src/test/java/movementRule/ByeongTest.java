package movementRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pieceProperty.PieceType.BYEONG;

import java.util.stream.Stream;
import movementRule.Byeong;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pieceProperty.Position;
import pieceProperty.Positions;

class ByeongTest {

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("byeongNonCanMoveToPositionProvider")
    void nonCanMoveTo(Position position) {
        //given
        Byeong byeong = new Byeong();
        Position startPosition = new Position(5, 5);

        //when
        assertThatThrownBy(() -> byeong.canMoveTo(startPosition, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("병은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Byeong byeong = new Byeong();
        Position startPosition = new Position(5, 5);
        Position futurePosition = new Position(4, 5);

        //when
        Positions actual = byeong.makeRoute(startPosition, futurePosition);

        //then
        assertThat(actual.getPositions().contains(futurePosition)).isFalse();
    }

    private static Stream<Arguments> byeongNonCanMoveToPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(6, 5)),
                Arguments.of(new Position(6, 3)),
                Arguments.of(new Position(6, 6))
        );
    }

}
