package janggiGame.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.Position;
import janggiGame.piece.character.Dynasty;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HorseTest {
    public static Stream<Arguments> provideHorseOriginAndDestinationAndExpected() {
        return Stream.of(
                Arguments.of(Position.of(5, 6), Position.of(6, 8), List.of(Position.of(5, 7))),
                Arguments.of(Position.of(5, 6), Position.of(4, 8), List.of(Position.of(5, 7))),
                Arguments.of(Position.of(5, 6), Position.of(7, 7), List.of(Position.of(6, 6))),
                Arguments.of(Position.of(5, 6), Position.of(7, 5), List.of(Position.of(6, 6))),
                Arguments.of(Position.of(5, 6), Position.of(4, 4), List.of(Position.of(5, 5)))
        );
    }

    @DisplayName("마는 목적지로 갈 수 없다면 예외를 발생 시킨다")
    @Test
    void horseCanValidateDestination() {
        // given
        Position origin = Position.of(1, 1);
        Position destination = Position.of(3, 3);
        Horse horse = new Horse(Dynasty.HAN);

        // when // then
        assertThatCode(() -> horse.getIntermediatePoints(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("마는 목적지로 가는 경로를 구할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideHorseOriginAndDestinationAndExpected")
    void horseCanGetIntermediatePoints(Position origin, Position destination, List<Position> expected) {
        // given
        Horse horse = new Horse(Dynasty.HAN);

        // when
        List<Position> actual = horse.getIntermediatePoints(origin, destination);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("마는 이동 경로에 기물이 존재 한다면 이동할 수 없다")
    @Test
    void horseCanNotMoveIfIntermediatePointsHasPiece() {
        // given
        Map<Position, Piece> intermediatePointsWithPiece = new LinkedHashMap<>();
        Horse horse = new Horse(Dynasty.HAN);

        intermediatePointsWithPiece.put(Position.of(6, 8), new Horse(Dynasty.HAN));

        // when // then
        assertThatCode(() -> horse.validateMove(intermediatePointsWithPiece, new EmptyPiece()))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("마는 이동 경로에 어떤 말도 없다면 이동 가능 하다")
    @Test
    void horseCanMove() {
        // given
        Map<Position, Piece> intermediatePointsWithPiece = new LinkedHashMap<>();
        Horse horse = new Horse(Dynasty.HAN);

        intermediatePointsWithPiece.put(Position.of(5, 7), new EmptyPiece());

        // when // then
        assertThatCode(() -> horse.validateMove(intermediatePointsWithPiece, new EmptyPiece()))
                .doesNotThrowAnyException();
    }

}