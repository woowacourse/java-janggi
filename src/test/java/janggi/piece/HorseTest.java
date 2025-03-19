package janggi.piece;

import janggi.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.TestPositionGenerator;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class HorseTest {
    @DisplayName("정상: 마가 생성 가능한 위치에 있는지 확인")
    @ParameterizedTest
    @MethodSource("getWithinBoundPositions")
    void createHorseTest(Position position) {
        assertThatCode(() -> new Horse(position)).doesNotThrowAnyException(); ;
    }

    @DisplayName("예외: 마가 생성 가능한 위치에 있는지 확인")
    @ParameterizedTest
    @MethodSource("getOutOfBoundPositions")
    void createHorseExceptionTest(Position position) {
        assertThatThrownBy(() -> new Horse(position)).isInstanceOf(IllegalArgumentException.class) ;
    }

    private static Stream<Arguments> getOutOfBoundPositions() {
        return TestPositionGenerator.makeOutOfBoundPositions();
    }

    private static Stream<Arguments> getWithinBoundPositions() {
        return TestPositionGenerator.makeWithinBoundPositions();
    }

    @DisplayName("마가 이동 가능한 position 목록 반환 확인")
    @Test
    void elephantPositionTest() {
        Piece horse = new Horse(new Position(5,5));
        List<Position> possibleMoves = horse.checkPossibleMoves();
        List<Position> expectedPossibleMoves = List.of(
                new Position(7, 4),
                new Position(7, 6),
                new Position(3, 4),
                new Position(3, 6),
                new Position(4, 3),
                new Position(6, 3),
                new Position(6, 7),
                new Position(4, 7)
        );
        assertThat(possibleMoves).containsAll(expectedPossibleMoves);
    }
}
