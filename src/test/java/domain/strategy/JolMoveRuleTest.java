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

class JolMoveRuleTest {
    @ParameterizedTest
    @MethodSource("choValidMovePositions")
    void 초나라_졸_이동_가능_좌표_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPiece = new Piece(Country.CHO, pieceType);
        boolean canMove = pieceType.canMovePosition(start, end, choPiece);
        assertThat(canMove).isTrue();
    }

    private static Stream<Arguments> choValidMovePositions() {
        return Stream.of(
                // 졸 이동 가능 좌표 (초나라)
                Arguments.of(PieceType.JOL, Position.create(3, 3), Position.create(4, 3)),
                Arguments.of(PieceType.JOL, Position.create(3, 3), Position.create(3, 2)),
                Arguments.of(PieceType.JOL, Position.create(3, 3), Position.create(3, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("hanValidMovePositions")
    void 한나라_졸_이동_가능_좌표_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPiece = new Piece(Country.HAN, pieceType);
        boolean canMove = pieceType.canMovePosition(start, end, choPiece);
        assertThat(canMove).isTrue();
    }

    private static Stream<Arguments> hanValidMovePositions() {
        return Stream.of(
                // 졸 이동 가능 좌표 (한나라)
                Arguments.of(PieceType.JOL, Position.create(7, 7), Position.create(6, 7)),
                Arguments.of(PieceType.JOL, Position.create(7, 7), Position.create(7, 8)),
                Arguments.of(PieceType.JOL, Position.create(7, 7), Position.create(7, 6))
        );
    }

    @ParameterizedTest
    @MethodSource("choInvalidMovePositions")
    void 초나라_졸_이동_불가_좌표_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPiece = new Piece(Country.CHO, pieceType);
        boolean canMove = pieceType.canMovePosition(start, end, choPiece);
        assertThat(canMove).isFalse();
    }

    private static Stream<Arguments> choInvalidMovePositions() {
        return Stream.of(
                // 졸 이동 불가 좌표 (초나라 기준)
                Arguments.of(PieceType.JOL, Position.create(3, 3), Position.create(2, 3)),
                Arguments.of(PieceType.JOL, Position.create(3, 3), Position.create(4, 4)),
                Arguments.of(PieceType.JOL, Position.create(3, 3), Position.create(5, 3))
        );
    }

    @ParameterizedTest
    @MethodSource("hanInvalidMovePositions")
    void 한나라_졸_이동_불가_좌표_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPiece = new Piece(Country.HAN, pieceType);
        boolean canMove = pieceType.canMovePosition(start, end, choPiece);
        assertThat(canMove).isFalse();
    }

    private static Stream<Arguments> hanInvalidMovePositions() {
        return Stream.of(
                // 졸 이동 불가 좌표 (한나라 기준)
                Arguments.of(PieceType.JOL, Position.create(7, 7), Position.create(8, 7)),
                Arguments.of(PieceType.JOL, Position.create(7, 7), Position.create(6, 6)),
                Arguments.of(PieceType.JOL, Position.create(7, 7), Position.create(5, 7))
        );
    }
}
