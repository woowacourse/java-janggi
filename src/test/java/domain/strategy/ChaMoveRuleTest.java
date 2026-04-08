package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Piece;
import domain.Position;
import domain.constant.Country;
import domain.constant.PieceType;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChaMoveRuleTest {
    @ParameterizedTest
    @MethodSource("validMovePositions")
    void 차_이동_가능_좌표_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPiece = new Piece(Country.CHO, pieceType);
        boolean canMove = pieceType.canMovePosition(start, end, choPiece);
        assertThat(canMove).isTrue();
    }

    private static Stream<Arguments> validMovePositions() {
        return Stream.of(
                Arguments.of(PieceType.CHA, Position.create(3, 3), Position.create(1, 3)),
                Arguments.of(PieceType.CHA, Position.create(3, 3), Position.create(9, 3)),
                Arguments.of(PieceType.CHA, Position.create(3, 3), Position.create(3, 9)),
                Arguments.of(PieceType.CHA, Position.create(3, 3), Position.create(3, 1))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidMovePositions")
    void 차_이동_불가_좌표_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPiece = new Piece(Country.CHO, pieceType);
        boolean canMove = pieceType.canMovePosition(start, end, choPiece);
        assertThat(canMove).isFalse();
    }

    private static Stream<Arguments> invalidMovePositions() {
        return Stream.of(
                Arguments.of(PieceType.CHA, Position.create(3, 3), Position.create(4, 4)),
                Arguments.of(PieceType.CHA, Position.create(3, 3), Position.create(5, 6))
        );
    }
}