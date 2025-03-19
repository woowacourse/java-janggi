package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierTest {

    private static Stream<Arguments> moveableArguments() {
        return Stream.of(
                Arguments.of(Position.of(7, 3), Side.HAN,
                        List.of(Position.of(7, 2), Position.of(7, 4), Position.of(8, 3))),
                Arguments.of(Position.of(7, 1), Side.HAN,
                        List.of(Position.of(8, 1), Position.of(7, 2))),
                Arguments.of(Position.of(7, 1), Side.CHO,
                        List.of(Position.of(6, 1), Position.of(7, 2))),
                Arguments.of(Position.of(1, 1), Side.CHO, List.of(Position.of(1, 2)))
        );
    }

    @DisplayName("좌표를 입력하면 이동 가능한 좌표들을 반환한다.")
    @ParameterizedTest
    @MethodSource("moveableArguments")
    void test1(Position startingPosition, Side side, List<Position> expected) {
        // given
        Soldier soldier = new Soldier();

        // when
        Set<Position> actual = soldier.generateMovePosition(side, startingPosition);

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

}
