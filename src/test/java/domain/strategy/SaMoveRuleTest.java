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

class SaMoveRuleTest {
    @ParameterizedTest
    @MethodSource("validMovePositions")
    void 사_이동_가능_좌표_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPiece = new Piece(Country.CHO, pieceType);
        boolean canMove = pieceType.canMovePosition(start, end, choPiece);
        assertThat(canMove).isTrue();
    }

    private static Stream<Arguments> validMovePositions() {
        return Stream.of(
                Arguments.of(PieceType.SA, Position.create(3, 3), Position.create(2, 3)),
                Arguments.of(PieceType.SA, Position.create(3, 3), Position.create(4, 3)),
                Arguments.of(PieceType.SA, Position.create(3, 3), Position.create(3, 2)),
                Arguments.of(PieceType.SA, Position.create(3, 3), Position.create(3, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidMovePositions")
    void 사_이동_불가_좌표_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPiece = new Piece(Country.CHO, pieceType);
        boolean canMove = pieceType.canMovePosition(start, end, choPiece);
        assertThat(canMove).isFalse();
    }

    private static Stream<Arguments> invalidMovePositions() {
        return Stream.of(
                Arguments.of(PieceType.SA, Position.create(3, 3), Position.create(1, 3)),
                Arguments.of(PieceType.SA, Position.create(3, 3), Position.create(5, 5))
        );
    }

    @ParameterizedTest
    @MethodSource("validSaMovePositions")
    void 사_궁성_영역_내부_이동_가능_좌표_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPiece = new Piece(Country.CHO, pieceType);
        boolean canMove = pieceType.canMovePosition(start, end, choPiece);
        assertThat(canMove).isTrue();
    }

    private static Stream<Arguments> validSaMovePositions() {
        return Stream.of(
                Arguments.of(PieceType.SA, Position.create(2, 5), Position.create(1, 4)),
                Arguments.of(PieceType.SA, Position.create(2, 5), Position.create(1, 5)),
                Arguments.of(PieceType.SA, Position.create(2, 5), Position.create(1, 6)),

                Arguments.of(PieceType.SA, Position.create(2, 5), Position.create(2, 4)),
                Arguments.of(PieceType.SA, Position.create(2, 5), Position.create(2, 6)),

                Arguments.of(PieceType.SA, Position.create(2, 5), Position.create(3, 4)),
                Arguments.of(PieceType.SA, Position.create(2, 5), Position.create(3, 5)),
                Arguments.of(PieceType.SA, Position.create(2, 5), Position.create(3, 6))
        );
    }
}
