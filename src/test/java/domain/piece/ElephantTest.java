package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Country;
import domain.board.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ElephantTest {
    @ParameterizedTest
    @DisplayName("상의 목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedElephantPaths")
    void elephantPathTest(Position from, Position to, List<Position> paths) {
        Piece choElephant = new Elephant(Country.CHO);
        Piece hanElephant = new Elephant(Country.HAN);
        assertThat(choElephant.findPaths(from, to)).isEqualTo(paths);
        assertThat(hanElephant.findPaths(from, to)).isEqualTo(paths);
    }

    static Stream<Arguments> expectedElephantPaths() {
        return Stream.of(
                Arguments.arguments(new Position(4, 4), new Position(6, 7),
                        List.of(new Position(4, 5), new Position(5, 6), new Position(6, 7))),
                Arguments.arguments(new Position(4, 4), new Position(2, 7),
                        List.of(new Position(4, 5), new Position(3, 6), new Position(2, 7))),
                Arguments.arguments(new Position(4, 4), new Position(7, 6),
                        List.of(new Position(5, 4), new Position(6, 5), new Position(7, 6))),
                Arguments.arguments(new Position(4, 4), new Position(7, 2),
                        List.of(new Position(5, 4), new Position(6, 3), new Position(7, 2))),
                Arguments.arguments(new Position(4, 4), new Position(6, 1),
                        List.of(new Position(4, 3), new Position(5, 2), new Position(6, 1))),
                Arguments.arguments(new Position(4, 4), new Position(2, 1),
                        List.of(new Position(4, 3), new Position(3, 2), new Position(2, 1))),
                Arguments.arguments(new Position(4, 4), new Position(1, 2),
                        List.of(new Position(3, 4), new Position(2, 3), new Position(1, 2))),
                Arguments.arguments(new Position(4, 4), new Position(1, 6),
                        List.of(new Position(3, 4), new Position(2, 5), new Position(1, 6)))
        );
    }

    @Test
    @DisplayName("상의 방향의 크기가 3이 아닌 경우 예외가 발생한다.")
    void elephantDirectionSizeExceptionTest() {
        Piece elephant = new Elephant(Country.CHO);

        Position from = new Position(1, 0);
        Position to = new Position(4, 4);

        assertThatThrownBy(() -> elephant.findPaths(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상이 이동할 수 있는 방향은 3개이어야 합니다.");
    }

    @Test
    @DisplayName("상의 2번째, 3번째 방향이 같지 않을 경우 예외가 발생한다.")
    void elephantDirectionNotSameExceptionTest() {
        Piece elephant = new Elephant(Country.CHO);

        Position from = new Position(1, 3);
        Position to = new Position(2, 0);

        assertThatThrownBy(() -> elephant.findPaths(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상의 2번째 방향과 3번째 방향은 동일해야 합니다.");
    }

    @Test
    @DisplayName("상의 1번째 방향이 대각선이거나, 2번째, 3번째 방향이 모두 대각선이 아닐 경우 예외가 발생한다.")
    void elephantDiagonalExceptionTest() {
        Piece elephant = new Elephant(Country.CHO);

        Position from = new Position(1, 1);
        Position allDiagonalTo = new Position(4, 4);
        Position notExistDiagonalTo = new Position(1, 4);

        assertThatThrownBy(() -> elephant.findPaths(from, allDiagonalTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상의 1번째 방향은 직선이고, 2, 3번째 방향은 대각선이어야 합니다.");
        assertThatThrownBy(() -> elephant.findPaths(from, notExistDiagonalTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상의 1번째 방향은 직선이고, 2, 3번째 방향은 대각선이어야 합니다.");
    }
}
