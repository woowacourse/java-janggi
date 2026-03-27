package domain.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.position.Position;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BoardFactoryTest {

    @ParameterizedTest
    @MethodSource("provideFormations")
    void 배치_조합을_검증한다(int choNum, int hanNum, String[] choExpected, String[] hanExpected) {
        Board board = BoardFactory.createWithFormation(
            Formation.from(choNum),
            Formation.from(hanNum)
        );

        assertEquals(hanExpected[0], getPieceString(board, 0, 1));
        assertEquals(hanExpected[1], getPieceString(board, 0, 2));
        assertEquals(hanExpected[2], getPieceString(board, 0, 6));
        assertEquals(hanExpected[3], getPieceString(board, 0, 7));

        assertEquals(choExpected[0], getPieceString(board, 9, 1));
        assertEquals(choExpected[1], getPieceString(board, 9, 2));
        assertEquals(choExpected[2], getPieceString(board, 9, 6));
        assertEquals(choExpected[3], getPieceString(board, 9, 7));
    }

    private String getPieceString(Board board, int row, int col) {
        var piece = board.findPiece(new Position(row, col));
        return piece.getTeamString() + piece.getPieceString();
    }

    static Stream<Arguments> provideFormations() {
        String[] f1 = {"HSD", "HMA", "HSD", "HMA"};
        String[] f2 = {"HMA", "HSD", "HMA", "HSD"};
        String[] f3 = {"HMA", "HSD", "HSD", "HMA"};
        String[] f4 = {"HSD", "HMA", "HMA", "HSD"};

        String[] c1 = {"CSD", "CMA", "CSD", "CMA"};
        String[] c2 = {"CMA", "CSD", "CMA", "CSD"};
        String[] c3 = {"CMA", "CSD", "CSD", "CMA"};
        String[] c4 = {"CSD", "CMA", "CMA", "CSD"};

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
}
