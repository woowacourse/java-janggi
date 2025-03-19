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

public class KingTest {
    @DisplayName("정상: 왕이 생성 가능한 위치에 있는지 확인")
    @ParameterizedTest
    @MethodSource("getWithinBoundPositions")
    void createKingTest(Position position) {
        assertThatCode(() -> new King(position)).doesNotThrowAnyException(); ;
    }

    @DisplayName("예외: 왕이 생성 가능한 위치에 있는지 확인")
    @ParameterizedTest
    @MethodSource("getOutOfBoundPositions")
    void createKingExceptionTest(Position position) {
        assertThatThrownBy(() -> new King(position)).isInstanceOf(IllegalArgumentException.class) ;
    }

    private static Stream<Arguments> getOutOfBoundPositions() {
        return TestPositionGenerator.makeOutOfBoundPositions();
    }

    private static Stream<Arguments> getWithinBoundPositions() {
        return TestPositionGenerator.makeWithinBoundPositions();
    }

    @DisplayName("왕이 이동 가능한 position 목록 반환 확인")
    @Test
    void kingPositionTest() {
        Piece king = new King(new Position(5,5));
        List<Position> possibleMoves = king.checkPossibleMoves();
        List<Position> expectedPossibleMoves = List.of(
                new Position(6, 5),
                new Position(5, 6),
                new Position(4, 5),
                new Position(5, 4)
        );
        assertThat(possibleMoves).containsAll(expectedPossibleMoves);
    }
}
