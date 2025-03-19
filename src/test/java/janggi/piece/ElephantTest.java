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

public class ElephantTest {
    @DisplayName("정상: 상이 생성 가능한 위치에 있는지 확인")
    @ParameterizedTest
    @MethodSource("getWithinBoundPositions")
    void createElephantTest(Position position) {
        assertThatCode(() -> new Elephant(position)).doesNotThrowAnyException(); ;
    }

    @DisplayName("예외: 상이 생성 가능한 위치에 있는지 확인")
    @ParameterizedTest
    @MethodSource("getOutOfBoundPositions")
    void createElephantExceptionTest(Position position) {
        assertThatThrownBy(() -> new Elephant(position)).isInstanceOf(IllegalArgumentException.class) ;
    }

    private static Stream<Arguments> getOutOfBoundPositions() {
        return TestPositionGenerator.makeOutOfBoundPositions();
    }

    private static Stream<Arguments> getWithinBoundPositions() {
        return TestPositionGenerator.makeWithinBoundPositions();
    }

    @DisplayName("상이 이동 가능한 position 목록 반환 확인")
    @Test
    void elephantPositionTest() {
        Piece elephant = new Elephant(new Position(5,5));
        List<Position> possibleMoves = elephant.checkPossibleMoves();
        List<Position> expectedPossibleMoves = List.of(
                new Position(8, 7),
                new Position(8, 3),
                new Position(2, 7),
                new Position(2, 3),
                new Position(3, 2),
                new Position(7, 2),
                new Position(7, 8),
                new Position(3, 8)
        );
        assertThat(possibleMoves).containsAll(expectedPossibleMoves);
    }

    @DisplayName("멱인지 확인 테스트")
    @Test
    void 멱테스트() {
        Elephant selectedElephant = new Elephant(new Position(2,2));

        Elephant elephant2 = new Elephant(new Position(3,2));
        Elephant elephant3 = new Elephant(new Position(1,2));
        Elephant elephant4 = new Elephant(new Position(2,3));
        Elephant elephant5 = new Elephant(new Position(2,1));

        Elephant elephant6 = new Elephant(new Position(2,0));
        Elephant elephant7 = new Elephant(new Position(2,0));
        Elephant elephant8 = new Elephant(new Position(2,0));

        assertThat(selectedElephant.canMoveFirstStep(elephant2)).isFalse();
        assertThat(selectedElephant.canMoveFirstStep(elephant3)).isFalse();
        assertThat(selectedElephant.canMoveFirstStep(elephant4)).isFalse();
        assertThat(selectedElephant.canMoveFirstStep(elephant5)).isFalse();
        assertThat(selectedElephant.canMoveFirstStep(elephant6)).isTrue();
        assertThat(selectedElephant.canMoveFirstStep(elephant7)).isTrue();
        assertThat(selectedElephant.canMoveFirstStep(elephant8)).isTrue();
    }

    @DisplayName("실제 이동 리스트 반환 확인")
    @Test
    void 실제이동테스트() {
        Elephant elephant = new Elephant(new Position(2,2));
        Elephant elephant2 = new Elephant(new Position(3,2));

        elephant2.isAlreadyLocatedWithinOneSpaceOrAtThatSpace(elephant);

    }
}
