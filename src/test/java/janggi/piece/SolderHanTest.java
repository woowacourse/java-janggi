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

public class SolderHanTest {
    @DisplayName("정상: 병이 생성 가능한 위치에 있는지 확인")
    @ParameterizedTest
    @MethodSource("getWithinBoundPositions")
    void createSoldierHanTest(Position position) {
        assertThatCode(() -> new SoldierHan(position)).doesNotThrowAnyException(); ;
    }

    @DisplayName("예외: 병이 생성 가능한 위치에 있는지 확인")
    @ParameterizedTest
    @MethodSource("getOutOfBoundPositions")
    void createSoldierHanExceptionTest(Position position) {
        assertThatThrownBy(() -> new SoldierHan(position)).isInstanceOf(IllegalArgumentException.class) ;
    }

    private static Stream<Arguments> getOutOfBoundPositions() {
        return TestPositionGenerator.makeOutOfBoundPositions();
    }

    private static Stream<Arguments> getWithinBoundPositions() {
        return TestPositionGenerator.makeWithinBoundPositions();
    }

    @DisplayName("병이 이동 가능한 position 목록 반환 확인")
    @Test
    void solderHanPositionTest() {
        Piece soldierHan = new SoldierHan(new Position(4, 3));
        List<Position> possibleMoves = soldierHan.checkPossibleMoves();
        List<Position> expectedPossibleMoves = List.of(
                new Position(5, 3),
                new Position(4, 2),
                new Position(3, 3)
        );
        assertThat(possibleMoves).containsAll(expectedPossibleMoves);
    }
}
