package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.Piece;
import domain.Position;
import domain.constant.Country;
import domain.constant.PieceType;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SangMoveRuleTest {
    @ParameterizedTest
    @MethodSource("validMovePositions")
    void 상_이동_가능_좌표_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPiece = new Piece(Country.CHO, pieceType);
        boolean canMove = pieceType.canMovePosition(start, end, choPiece);
        assertThat(canMove).isTrue();
    }

    private static Stream<Arguments> validMovePositions() {
        return Stream.of(
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(2, 3)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(2, 7)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(8, 3)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(8, 7)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(3, 2)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(7, 2)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(3, 8)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(7, 8))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidMovePositions")
    void 상_이동_불가_좌표_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPiece = new Piece(Country.CHO, pieceType);
        boolean canMove = pieceType.canMovePosition(start, end, choPiece);
        assertThat(canMove).isFalse();
    }

    private static Stream<Arguments> invalidMovePositions() {
        return Stream.of(
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(4, 5)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(3, 3)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(2, 4))
        );
    }
}