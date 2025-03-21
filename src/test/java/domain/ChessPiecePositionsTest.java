package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.stream.Stream;

import domain.chessPiece.ChessPiece;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChessPiecePositionsTest {

    @ParameterizedTest(name = "기물들의 위치를 초기화 한다 - {0}")
    @MethodSource
    void test1(String name, final ChessPosition position, final ChessPieceType chessPieceType) {
        //given
        //when
        final ChessPiecePositions chessPiecePositions = ChessPiecePositions.empty();
        chessPiecePositions.initialize();
        final Map<ChessPosition, ChessPiece> chessPieces = chessPiecePositions.getChessPieces();

        //then
        assertThat(chessPieces.get(position).getChessPieceType()).isEqualTo(chessPieceType);

    }

    private static Stream<Arguments> test1() {
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
