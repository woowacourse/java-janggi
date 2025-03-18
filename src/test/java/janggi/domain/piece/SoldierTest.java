package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierTest {

    private static Stream<Arguments> moveableArguments() {
        return Stream.of(
                Arguments.of(new Position(7, 3), Side.HAN,
                        List.of(new Position(7, 2), new Position(7, 4), new Position(8, 3))),
                Arguments.of(new Position(7, 1), Side.HAN,
                        List.of(new Position(8, 1), new Position(7, 2))),
                Arguments.of(new Position(7, 1), Side.CHO,
                        List.of(new Position(6, 1), new Position(7, 2))),
                Arguments.of(new Position(1, 1), Side.CHO, List.of(new Position(1, 2)))
        );
    }

    @DisplayName("좌표를 입력하면 이동 가능한 좌표들을 반환한다.")
    @ParameterizedTest
    @MethodSource("moveableArguments")
    void test1(Position startingPosition, Side side, List<Position> expected) {
        // given
        Soldier soldier = new Soldier();

        // when
        List<Position> actual = soldier.generateMovePosition(side, startingPosition);

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

}
