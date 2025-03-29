package janggi.domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.BoardFixture;
import janggi.domain.Coordinate;
import janggi.domain.movestep.MoveStep;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class CastleBoundedTest {

    @ParameterizedTest
    @DisplayName("궁성 내 움직임은 궁성 내에서만 움직일 수 있다.")
    @CsvSource({"5,1", "5,3", "4,2", "6,2"})
    void test1(int x, int y) {
        // given
        final var movement = new CastleBounded(
            new OnceMovement(MoveStep.UP, MoveStep.DOWN, MoveStep.LEFT, MoveStep.RIGHT));
        final var departure = new Coordinate(5, 2);
        final var arrival = new Coordinate(x, y);

        // when
        final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @DisplayName("궁성 내 움직임은 궁성을 벗어날 수 없다.")
    @MethodSource("provideDeparturesAndArrivalsOutOfCastle")
    void test3(Coordinate departure, Coordinate arrival) {
        //given
        final var movement = new CastleBounded(
            new OnceMovement(MoveStep.UP, MoveStep.DOWN, MoveStep.LEFT, MoveStep.RIGHT));

        //when
        final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

        //then
        assertThat(canMove).isFalse();
    }

    public static Stream<Arguments> provideDeparturesAndArrivalsOutOfCastle() {
        return Stream.of(
            Arguments.of(new Coordinate(4, 10), new Coordinate(3, 10)),
            Arguments.of(new Coordinate(6, 10), new Coordinate(7, 10)),
            Arguments.of(new Coordinate(4, 9), new Coordinate(3, 9)),
            Arguments.of(new Coordinate(6, 9), new Coordinate(7, 9)),
            Arguments.of(new Coordinate(4, 8), new Coordinate(3, 8)),
            Arguments.of(new Coordinate(6, 8), new Coordinate(7, 8)),
            Arguments.of(new Coordinate(4, 8), new Coordinate(4, 7)),
            Arguments.of(new Coordinate(5, 8), new Coordinate(5, 7)),
            Arguments.of(new Coordinate(6, 8), new Coordinate(6, 7))
        );
    }
}
