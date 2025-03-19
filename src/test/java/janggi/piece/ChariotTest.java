package janggi.piece;

import janggi.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.TestPositionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class ChariotTest {
    @DisplayName("정상: 차가 생성 가능한 위치에 있는지 확인")
    @ParameterizedTest
    @MethodSource("getWithinBoundPositions")
    void createChariotTest(Position position) {
        assertThatCode(() -> new Chariot(position)).doesNotThrowAnyException(); ;
    }

    @DisplayName("예외: 차가 생성 가능한 위치에 있는지 확인")
    @ParameterizedTest
    @MethodSource("getOutOfBoundPositions")
    void createChariotExceptionTest(Position position) {
        assertThatThrownBy(() -> new Chariot(position)).isInstanceOf(IllegalArgumentException.class) ;
    }

    private static Stream<Arguments> getOutOfBoundPositions() {
        return TestPositionGenerator.makeOutOfBoundPositions();
    }

    private static Stream<Arguments> getWithinBoundPositions() {
        return TestPositionGenerator.makeWithinBoundPositions();
    }

    @DisplayName("차가 이동 가능한 position 목록 반환 확인")
    @Test
    void chariotPositionTest() {
        Piece chariot = new Chariot(new Position(2,0));
        List<Position> expected = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            expected.add(new Position(i,0));
        }
        for (int i = 0; i < 9; i++) {
            expected.add(new Position(2, i));
        }

        assertThat(chariot.checkPossibleMoves()).containsAll(expected);
    }
}
