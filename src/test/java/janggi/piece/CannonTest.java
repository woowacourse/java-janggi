package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.Position;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class CannonTest {

    @Test
    @DisplayName("포의 경로 상 말이 2개 이상인 경우 false를 반환한다.")
    void shouldThrowExceptionWhenExistsOverTwoPieceOnCannonPath() {
        // given
        Cannon cannon = new Cannon(Side.RED);
        Map<Position, Piece> board = Map.of(
                new Position(1, 1), cannon,
                new Position(3, 1), new Soldier(Side.RED),
                new Position(4, 1), new Soldier(Side.RED)
        );
        Position start = new Position(1, 1);
        Position end = new Position(6, 1);

        // when
        boolean canMove = cannon.canMove(start, end, board);

        // then
        assertThat(canMove).isFalse();
    }

    @Test
    @DisplayName("포의 경로 상 말이 1개인 경우 true를 반환한다.")
    void shouldThrowExceptionWhenExistsOnePieceOnCannonPath() {
        // given
        Cannon cannon = new Cannon(Side.RED);
        Map<Position, Piece> board = Map.of(
                new Position(1, 1), cannon,
                new Position(3, 1), new Soldier(Side.RED)
        );
        Position start = new Position(1, 1);
        Position end = new Position(6, 1);

        // when
        boolean canMove = cannon.canMove(start, end, board);

        // then
        assertThat(canMove).isTrue();
    }

    @Test
    @DisplayName("포의 경로 상 포가 존재하는 경우 false를 반환한다.")
    void shouldThrowExceptionWhenExistsCannonOnCannonPath() {
        // given
        Cannon cannon = new Cannon(Side.RED);
        Map<Position, Piece> board = Map.of(
                new Position(1, 1), cannon,
                new Position(3, 1), new Cannon(Side.RED)
        );
        Position start = new Position(1, 1);
        Position end = new Position(6, 1);

        // when
        boolean canMove = cannon.canMove(start, end, board);

        // then
        assertThat(canMove).isFalse();
    }

    @Test
    @DisplayName("포의 경로 상에 말이 존재하지 않는 경우 false를 반환한다.")
    void shouldThrowExceptionWhenEmptyOnCannonPath() {
        // given
        Cannon cannon = new Cannon(Side.RED);
        Map<Position, Piece> board = Map.of(
                new Position(1, 1), cannon
        );
        Position start = new Position(1, 1);
        Position end = new Position(6, 1);

        // when
        boolean canMove = cannon.canMove(start, end, board);

        // then
        assertThat(canMove).isFalse();
    }

    @Test
    @DisplayName("포의 도착지의 말이 포인 경우 false를 반환한다.")
    void shouldThrowExceptionWhenExistsCannonOnCannonEndPosition() {
        // given
        Cannon cannon = new Cannon(Side.RED);
        Map<Position, Piece> board = Map.of(
                new Position(1, 1), cannon,
                new Position(3, 1), new Soldier(Side.RED),
                new Position(6, 1), new Cannon(Side.BLUE)
        );
        Position start = new Position(1, 1);
        Position end = new Position(6, 1);

        // when
        boolean canMove = cannon.canMove(start, end, board);

        // then
        assertThat(canMove).isFalse();
    }

    @ParameterizedTest
    @DisplayName("말의 이동 규칙이 어긋나면 false를 발생한다.")
    @CsvSource(value = {
            "6, 6",
            "4, 6",
            "6, 4",
            "4, 4"
    })
    void shouldReturnFalseWhenUnfollowMovingRule(int destX, int destY) {
        // given
        Cannon cannon = new Cannon(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        boolean canMove = cannon.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isFalse();
    }

    @ParameterizedTest
    @DisplayName("시작점과 끝점이 주어졌을 때, 이동 경로를 반환한다.")
    @MethodSource("pathArguments")
    void shouldReturnFalseWhenRouteExists(Position end, List<Position> expectedPath) {
        // given
        Cannon cannon = new Cannon(Side.RED);
        Position start = new Position(5, 5);

        // when
        List<Position> path = cannon.calculatePath(start, end);

        // then
        assertThat(path).containsExactlyElementsOf(expectedPath);
    }

    @ParameterizedTest
    @DisplayName("말의 이동 규칙이 어긋나면 예외를 발생한다.")
    @CsvSource(value = {
            "6, 6",
            "4, 6",
            "6, 4",
            "4, 4"
    })
    void shouldReturnTrueWhenUnfollowMovingRule(int destX, int destY) {
        // given
        Cannon cannon = new Cannon(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        assertThatThrownBy(() -> cannon.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> pathArguments() {
        return Stream.of(
                Arguments.of(
                        new Position(8, 5),
                        List.of(
                                new Position(6, 5),
                                new Position(7, 5)
                        )
                ),
                Arguments.of(
                        new Position(2, 5),
                        List.of(
                                new Position(4, 5),
                                new Position(3, 5)
                        )
                ),
                Arguments.of(
                        new Position(5, 8),
                        List.of(
                                new Position(5, 6),
                                new Position(5, 7)
                        )
                ),
                Arguments.of(
                        new Position(5, 2),
                        List.of(
                                new Position(5, 4),
                                new Position(5, 3)
                        )
                )
        );
    }
}
