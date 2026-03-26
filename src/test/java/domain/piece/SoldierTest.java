package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Country;
import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SoldierTest {
    @ParameterizedTest
    @DisplayName("목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedPaths")
    void pathTest(Position from, Position to, List<Position> paths) {
        Soldier soldier = new Soldier(new PieceInfo(PieceType.SOLDIER, Country.CHO));
        assertThat(soldier.path(from, to)).isEqualTo(paths);
    }

    static Stream<Arguments> expectedPaths() {
        return Stream.of(
                Arguments.arguments(new Position(1, 1), new Position(1, 2),
                        List.of(new Position(1, 1), new Position(1, 2))),
                Arguments.arguments(new Position(1, 1), new Position(0, 1),
                        List.of(new Position(1, 1), new Position(0, 1))),
                Arguments.arguments(new Position(1, 1), new Position(2, 1),
                        List.of(new Position(1, 1), new Position(2, 1)))
        );
    }
}
