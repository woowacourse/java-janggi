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

public class ChariotTest {
    @ParameterizedTest
    @DisplayName("차의 목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedChariotPaths")
    void chariotPathTest(Position from, Position to, List<Position> paths) {
        Piece choChariot = new Chariot(Country.CHO);
        Piece hanChariot = new Chariot(Country.HAN);

        assertThat(choChariot.findPaths(from, to)).isEqualTo(paths);
        assertThat(hanChariot.findPaths(from, to)).isEqualTo(paths);
    }

    static Stream<Arguments> expectedChariotPaths() {
        return Stream.of(
                Arguments.arguments(new Position(4, 4), new Position(4, 0),
                        List.of(new Position(4, 3), new Position(4, 2), new Position(4, 1), new Position(4, 0))),
                Arguments.arguments(new Position(4, 4), new Position(0, 4),
                        List.of(new Position(3, 4), new Position(2, 4), new Position(1, 4), new Position(0, 4))),
                Arguments.arguments(new Position(4, 4), new Position(8, 4),
                        List.of(new Position(5, 4), new Position(6, 4), new Position(7, 4), new Position(8, 4))),
                Arguments.arguments(new Position(4, 4), new Position(4, 8),
                        List.of(new Position(4, 5), new Position(4, 6), new Position(4, 7), new Position(4, 8)))
        );
    }

    @Test
    @DisplayName("차가 하나의 방향으로만 이동하지 않을 경우 예외가 발생한다.")
    void chariotOneDirectionExceptionTest() {
        Piece chariot = new Chariot(Country.CHO);

        Position from = new Position(1, 2);
        Position to = new Position(4, 4);

        assertThatThrownBy(() -> chariot.findPaths(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 하나의 방향으로만 이동 가능합니다.");
    }

    @Test
    @DisplayName("차가 대각선으로 이동할 경우 예외가 발생한다.")
    void chariotDiagonalExceptionTest() {
        Piece chariot = new Chariot(Country.CHO);

        Position from = new Position(1, 1);
        Position to = new Position(4, 4);

        assertThatThrownBy(() -> chariot.findPaths(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 직선으로만 이동 가능합니다.");
    }

    @Test
    @DisplayName("차가 궁성 내부에서 대각선으로 이동할 수 있다.")
    void chariotDiagonalInPalaceTest() {
        Piece choChariot = new Chariot(Country.CHO);
        Piece hanChariot = new Chariot(Country.HAN);

        assertThat(choChariot.findPaths(new Position(3, 0), new Position(5, 2)))
                .isEqualTo(List.of(new Position(4, 1), new Position(5, 2)));
        assertThat(hanChariot.findPaths(new Position(3, 9), new Position(5, 7)))
                .isEqualTo(List.of(new Position(4, 8), new Position(5, 7)));
    }
}
