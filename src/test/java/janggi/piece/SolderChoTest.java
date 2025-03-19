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

public class SolderChoTest {
    @DisplayName("정상: 졸이 생성 가능한 위치에 있는지 확인")
    @ParameterizedTest
    @MethodSource("getWithinBoundPositions")
    void createSoldierChoTest(Position position) {
        assertThatCode(() -> new SoldierCho(position)).doesNotThrowAnyException(); ;
    }

    @DisplayName("예외: 졸이 생성 가능한 위치에 있는지 확인")
    @ParameterizedTest
    @MethodSource("getOutOfBoundPositions")
    void createSoldierChoExceptionTest(Position position) {
        assertThatThrownBy(() -> new SoldierCho(position)).isInstanceOf(IllegalArgumentException.class) ;
    }

    private static Stream<Arguments> getOutOfBoundPositions() {
        return TestPositionGenerator.makeOutOfBoundPositions();
    }

    private static Stream<Arguments> getWithinBoundPositions() {
        return TestPositionGenerator.makeWithinBoundPositions();
    }

    @DisplayName("졸이 이동 가능한 position 목록 반환 확인")
    @Test
    void solderChoPositionTest() {
        Piece solderCho = new SoldierCho(new Position(7,3));
        List<Position> possibleMoves = solderCho.checkPossibleMoves();
        List<Position> expectedPossibleMoves = List.of(
                new Position(8, 3),
                new Position(7, 4),
                new Position(6, 3)
        );
        assertThat(possibleMoves).containsAll(expectedPossibleMoves);
    }
}
