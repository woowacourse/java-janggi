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

public class HorseTest {
    @ParameterizedTest
    @DisplayName("마의 목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedHorsePaths")
    void horsePathTest(Position from, Position to, List<Position> paths) {
        Piece choHorse = new Horse(Country.CHO);
        Piece hanHorse = new Horse(Country.HAN);
        assertThat(choHorse.findPaths(from, to)).isEqualTo(paths);
        assertThat(hanHorse.findPaths(from, to)).isEqualTo(paths);
    }

    static Stream<Arguments> expectedHorsePaths() {
        return Stream.of(
                Arguments.arguments(new Position(4, 4), new Position(5, 6),
                        List.of(new Position(4, 5), new Position(5, 6))),
                Arguments.arguments(new Position(4, 4), new Position(3, 6),
                        List.of(new Position(4, 5), new Position(3, 6))),
                Arguments.arguments(new Position(4, 4), new Position(6, 5),
                        List.of(new Position(5, 4), new Position(6, 5))),
                Arguments.arguments(new Position(4, 4), new Position(6, 3),
                        List.of(new Position(5, 4), new Position(6, 3))),
                Arguments.arguments(new Position(4, 4), new Position(5, 2),
                        List.of(new Position(4, 3), new Position(5, 2))),
                Arguments.arguments(new Position(4, 4), new Position(3, 2),
                        List.of(new Position(4, 3), new Position(3, 2))),
                Arguments.arguments(new Position(4, 4), new Position(2, 3),
                        List.of(new Position(3, 4), new Position(2, 3))),
                Arguments.arguments(new Position(4, 4), new Position(2, 5),
                        List.of(new Position(3, 4), new Position(2, 5)))
        );
    }


    @Test
    @DisplayName("마의 방향의 크기가 2가 아닌 경우 예외가 발생한다.")
    void horseDirectionSizeExceptionTest() {
        Piece horse = new Horse(Country.CHO);

        Position from = new Position(1, 0);
        Position to = new Position(4, 4);

        assertThatThrownBy(() -> horse.findPaths(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 마가 이동할 수 있는 방향은 2개이어야 합니다.");
    }

    @Test
    @DisplayName("마의 1번째 방향이 대각선이거나, 2번째 방향이 대각선이 아닐 경우 예외가 발생한다.")
    void horseDiagonalExceptionTest() {
        Piece horse = new Horse(Country.CHO);

        Position from = new Position(1, 1);
        Position allDialogTo = new Position(3, 3);
        Position notExistDialogTo = new Position(1, 3);

        assertThatThrownBy(() -> horse.findPaths(from, allDialogTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 마의 1번째 방향은 직선이고, 2번째 방향은 대각선이어야 합니다.");
        assertThatThrownBy(() -> horse.findPaths(from, notExistDialogTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 마의 1번째 방향은 직선이고, 2번째 방향은 대각선이어야 합니다.");
    }
}
