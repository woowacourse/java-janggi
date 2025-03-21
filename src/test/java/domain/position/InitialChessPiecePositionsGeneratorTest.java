package domain.position;

import domain.chessPiece.ChessPiece;
import domain.type.ChessPieceType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class InitialChessPiecePositionsGeneratorTest {
    @ParameterizedTest(name = "기물들의 위치를 초기화 한다 - {0}")
    @MethodSource
    void initializePositions(String name, final ChessPosition position, final ChessPieceType chessPieceType) {
        // given
        InitialChessPiecePositionsGenerator generator = new InitialChessPiecePositionsGenerator();

        // when
        Map<ChessPosition, ChessPiece> generated = generator.generate();

        // then
        assertThat(generated.get(position).getChessPieceType()).isEqualTo(chessPieceType);
    }

    private static Stream<Arguments> initializePositions() {
        return Stream.of(
                Arguments.of("RED Chariot", new ChessPosition(0,0), ChessPieceType.CHARIOT),
                Arguments.of("RED Horse", new ChessPosition(0,1), ChessPieceType.HORSE),
                Arguments.of("RED Elephant", new ChessPosition(0,2), ChessPieceType.ELEPHANT),
                Arguments.of("RED Cannon", new ChessPosition(2,1), ChessPieceType.CANNON),
                Arguments.of("RED Guard", new ChessPosition(0, 3), ChessPieceType.GUARD),
                Arguments.of("RED Pawn", new ChessPosition(3,0), ChessPieceType.PAWN),
                Arguments.of("RED King", new ChessPosition(1,4), ChessPieceType.KING)
        );
    }
}
