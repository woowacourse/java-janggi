package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Country;
import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class GeneralTest {
    @ParameterizedTest
    @DisplayName("궁의 목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedGeneralPaths")
    void generalPathTest(Position from, Position to, List<Position> paths) {
        Piece choGeneral = new General(Country.CHO);
        Piece hanGeneral = new General(Country.HAN);
        assertThat(choGeneral.findPaths(from, to)).isEqualTo(paths);
        assertThat(hanGeneral.findPaths(from, to)).isEqualTo(paths);
    }

    static Stream<Arguments> expectedGeneralPaths() {
        return Stream.of(
                Arguments.arguments(new Position(1, 1), new Position(1, 2),
                        List.of(new Position(1, 2))),
                Arguments.arguments(new Position(1, 1), new Position(0, 1),
                        List.of(new Position(0, 1))),
                Arguments.arguments(new Position(1, 1), new Position(2, 1),
                        List.of(new Position(2, 1))),
                Arguments.arguments(new Position(1, 1), new Position(1, 0),
                        List.of(new Position(1, 0)))
        );
    }

    @Test
    @DisplayName("궁의 방향의 크기가 1이 아닌 경우 예외가 발생한다.")
    void generalDirectionSizeExceptionTest() {
        Piece general = new General(Country.CHO);

        Position from = new Position(3, 0);
        Position to = new Position(3, 2);

        assertThatThrownBy(() -> general.findPaths(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 한 칸만 이동할 수 있습니다.");
    }

    @Test
    @DisplayName("궁이 대각선으로 이동할 경우 예외가 발생한다.")
    void generalDiagonalExceptionTest() {
        Piece general = new General(Country.CHO);

        Position from = new Position(3, 0);
        Position to = new Position(2, 1);

        assertThatThrownBy(() -> general.findPaths(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 직선으로만 이동 가능합니다.");
    }
}
