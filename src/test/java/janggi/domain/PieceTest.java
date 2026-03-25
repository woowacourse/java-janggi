package janggi.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PieceTest {

    public static Stream<Arguments> successMovePositions() {
        return Stream.of(
                Arguments.of(new Position(6, 0), new Position(5, 0)),
                Arguments.of(new Position(5, 1), new Position(4, 1)),
                Arguments.of(new Position(6, 0), new Position(6, 1)),
                Arguments.of(new Position(6, 1), new Position(6, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("successMovePositions")
    void 병은_1칸_이동_여부를_확인한다(Position from, Position to) {
        //given
        Piece piece = new Piece(Type.SOLDIER, Camp.HAN, new SoldierStrategy());
        //when
        boolean result = piece.canMove(from, to);
        //then
        assertTrue(result);
    }

    public static Stream<Arguments> exceptionMovePositions() {
        return Stream.of(
                Arguments.of(new Position(6, 0), new Position(5, 1)),
                Arguments.of(new Position(6, 0), new Position(6, 2)),
                Arguments.of(new Position(3, 2), new Position(2, 3))
        );
    }

    @ParameterizedTest
    @MethodSource("exceptionMovePositions")
    void 병은_1칸_이동이_아니면_예외가_발생한다(Position from, Position to) {
        Piece piece = new Piece(Type.SOLDIER, Camp.HAN, new SoldierStrategy());

        assertThatThrownBy(() -> piece.canMove(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
    }
}
