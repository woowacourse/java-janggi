package domain.board;

import domain.piece.BasicPiece;
import domain.piece.PieceType;
import domain.player.Team;
import domain.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static domain.piece.PieceType.MA;
import static domain.piece.PieceType.SANG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BoardFactoryTest {

    static Stream<Arguments> provideFormations() {
        PieceType[] f1 = {SANG, MA, SANG, MA};
        PieceType[] f2 = {MA, SANG, MA, SANG};
        PieceType[] f3 = {MA, SANG, SANG, MA};
        PieceType[] f4 = {SANG, MA, MA, SANG};

        PieceType[] c1 = {SANG, MA, SANG, MA};
        PieceType[] c2 = {MA, SANG, MA, SANG};
        PieceType[] c3 = {MA, SANG, SANG, MA};
        PieceType[] c4 = {SANG, MA, MA, SANG};

        return Stream.of(
                Arguments.of(1, 1, c1, f1),
                Arguments.of(1, 2, c1, f2),
                Arguments.of(1, 3, c1, f3),
                Arguments.of(1, 4, c1, f4),
                Arguments.of(2, 1, c2, f1),
                Arguments.of(2, 2, c2, f2),
                Arguments.of(2, 3, c2, f3),
                Arguments.of(2, 4, c2, f4),
                Arguments.of(3, 1, c3, f1),
                Arguments.of(3, 2, c3, f2),
                Arguments.of(3, 3, c3, f3),
                Arguments.of(3, 4, c3, f4),
                Arguments.of(4, 1, c4, f1),
                Arguments.of(4, 2, c4, f2),
                Arguments.of(4, 3, c4, f3),
                Arguments.of(4, 4, c4, f4)
        );
    }

    @ParameterizedTest
    @MethodSource("provideFormations")
    void 배치_조합을_검증한다(int choNum, int hanNum, PieceType[] choExpected, PieceType[] hanExpected) {
        Board board = BoardFactory.createWithFormation(
                Formation.from(choNum),
                Formation.from(hanNum)
        );

        assertPiece(board, 0, 1, Team.HAN, hanExpected[0]);
        assertPiece(board, 0, 2, Team.HAN, hanExpected[1]);
        assertPiece(board, 0, 6, Team.HAN, hanExpected[2]);
        assertPiece(board, 0, 7, Team.HAN, hanExpected[3]);

        assertPiece(board, 9, 1, Team.CHO, choExpected[0]);
        assertPiece(board, 9, 2, Team.CHO, choExpected[1]);
        assertPiece(board, 9, 6, Team.CHO, choExpected[2]);
        assertPiece(board, 9, 7, Team.CHO, choExpected[3]);
    }

    private void assertPiece(Board board, int row, int column, Team expectedTeam, PieceType expectedPieceType) {
        BasicPiece piece = board.findPiece(new Position(row, column));
        assertFalse(piece.isNone());
        assertEquals(expectedTeam, piece.getTeam());
        assertEquals(expectedPieceType, piece.getPieceType());
    }
}
