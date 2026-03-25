import domain.board.Board;
import domain.Piece;
import domain.PieceType;
import domain.board.BoardFactory;
import domain.board.InitializeSetting;
import domain.board.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class BoardTest {
    @ParameterizedTest
    @MethodSource("pawnProvider")
    void 졸을_올바른_위치에_초기화한다 (Position position) {
        Board board = BoardFactory.createBoard(InitializeSetting.RIGHT_ELEPHANT_SETTING);
        Piece piece= board.getPiece(position);
        Assertions.assertThat(piece.pieceType()).isEqualTo(PieceType.PAWN);
    }

    static Stream<Arguments> pawnProvider() {
        return Stream.of(
                Arguments.of(new Position(0, 6)),
                Arguments.of(new Position(2, 6)),
                Arguments.of(new Position(4, 6)),
                Arguments.of(new Position(6, 6)),
                Arguments.of(new Position(8, 6))
        );
    }



}
