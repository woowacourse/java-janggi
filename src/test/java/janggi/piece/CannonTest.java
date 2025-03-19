package janggi.piece;

import janggi.position.Position;
import net.bytebuddy.asm.MemberSubstitution;
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

public class CannonTest {
    @DisplayName("정상: 포가 생성 가능한 위치에 있는지 확인")
    @ParameterizedTest
    @MethodSource("getWithinBoundPositions")
    void createCannonTest(Position position) {
        assertThatCode(() -> new Cannon(position)).doesNotThrowAnyException(); ;
    }

    @DisplayName("예외: 포가 생성 가능한 위치에 있는지 확인")
    @ParameterizedTest
    @MethodSource("getOutOfBoundPositions")
    void createCannonExceptionTest(Position position) {
        assertThatThrownBy(() -> new Cannon(position)).isInstanceOf(IllegalArgumentException.class) ;
    }

    private static Stream<Arguments> getOutOfBoundPositions() {
        return TestPositionGenerator.makeOutOfBoundPositions();
    }

    private static Stream<Arguments> getWithinBoundPositions() {
        return TestPositionGenerator.makeWithinBoundPositions();
    }


    @DisplayName("포가 이동 가능한 position 목록 반환 확인")
    @Test
    void cannonPositionTest() {
        Piece cannon = new Cannon(new Position(2,0));
        List<Position> expected = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            expected.add(new Position(i,0));
        }
        for (int i = 0; i < 9; i++) {
            expected.add(new Position(2, i));
        }

        assertThat(cannon.checkPossibleMoves()).containsAll(expected);
    }
}
