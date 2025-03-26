package domain;

import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.piece.category.Guard;
import domain.piece.category.King;
import domain.spatial.Position;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class MoveInfoTest {

    @ParameterizedTest
    @MethodSource
    void 경로에_배치된_기물이_없는지_판단한다(Piece pathPiece, boolean excepted) {
        // given
        Map<Position, Piece> pathPieces = new LinkedHashMap<>();
        pathPieces.put(new Position(1, 2), pathPiece);

        MoveInfo moveInfo = new MoveInfo(pathPieces);

        // when
        boolean result = moveInfo.isPathInPiece();

        // then
        assertThat(result).isEqualTo(excepted);
    }

    private static Stream<Arguments> 경로에_배치된_기물이_없는지_판단한다() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of(new King(new Position(1, 2), PieceDirection.KING.get()), false)
        );
    }

    @ParameterizedTest
    @MethodSource
    void 이동_기물과_도착지_기물이_같은지_판단한다(Piece startPiece, Piece targetPiece, boolean excepted) {
        // given
        Map<Position, Piece> pathPieces = new LinkedHashMap<>();
        pathPieces.put(new Position(1, 2), null);
        pathPieces.put(new Position(2, 2), null);
        pathPieces.put(new Position(3, 2), targetPiece);

        // when
        boolean result = pathPieces.isSameTargetPiece(startPiece);

        // then
        assertThat(result).isEqualTo(excepted);
    }

    private static Stream<Arguments> 이동_기물과_도착지_기물이_같은지_판단한다() {
        return Stream.of(
                Arguments.of(
                        new King(new Position(1, 2), PieceDirection.KING.get()),
                        new King(new Position(3, 2), PieceDirection.KING.get()),
                        true
                ),
                Arguments.of(
                        new King(new Position(1, 2), PieceDirection.KING.get()),
                        new Guard(new Position(1, 2), PieceDirection.GUARD.get()),
                        false
                )
        );
    }
}
