package domain.board;

import static org.junit.jupiter.api.Assertions.*;

import dto.BoardDTO;
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

        BoardDTO dto = board.createDTO();
        var data = dto.board();

        assertEquals(choExpected[0], data.get(0).get(1));
        assertEquals(choExpected[1], data.get(0).get(2));
        assertEquals(choExpected[2], data.get(0).get(6));
        assertEquals(choExpected[3], data.get(0).get(7));

        assertEquals(hanExpected[0], data.get(9).get(1));
        assertEquals(hanExpected[1], data.get(9).get(2));
        assertEquals(hanExpected[2], data.get(9).get(6));
        assertEquals(hanExpected[3], data.get(9).get(7));
    }

    static Stream<Arguments> provideFormations() {
        String[] f1 = {"SD", "MA", "SD", "MA"};
        String[] f2 = {"MA", "SD", "MA", "SD"};
        String[] f3 = {"MA", "SD", "SD", "MA"};
        String[] f4 = {"SD", "MA", "MA", "SD"};

        return Stream.of(
                Arguments.of(1, 1, f1, f1),
                Arguments.of(1, 2, f1, f2),
                Arguments.of(1, 3, f1, f3),
                Arguments.of(1, 4, f1, f4),
                Arguments.of(2, 1, f2, f1),
                Arguments.of(2, 2, f2, f2),
                Arguments.of(2, 3, f2, f3),
                Arguments.of(2, 4, f2, f4),
                Arguments.of(3, 1, f3, f1),
                Arguments.of(3, 2, f3, f2),
                Arguments.of(3, 3, f3, f3),
                Arguments.of(3, 4, f3, f4),
                Arguments.of(4, 1, f4, f1),
                Arguments.of(4, 2, f4, f2),
                Arguments.of(4, 3, f4, f3),
                Arguments.of(4, 4, f4, f4)
        );
    }
}




