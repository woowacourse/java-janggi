package janggi.piece;

import janggi.board.Board;
import janggi.board.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

class GuardTest {

    @CsvSource(value = {"4:5", "3:4", "5:4", "4:3"}, delimiterString = ":")
    @ParameterizedTest
    void 사의_정상적인_움직임을_테스트한다(int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 4);
        Position goal = createPosition(column, row);
        Chariot piece = new Chariot(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // when
        board.movePiece(start, goal, Team.GREEN);

        // then
        assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }
}
