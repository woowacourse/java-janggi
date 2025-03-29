package janggi.piece;

import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.assertThatCode;

class SoldierTest {
    @DisplayName("졸병의_정상적인_움직임을_테스트한다")
    @CsvSource(value = {"GREEN:4:5", "GREEN:5:4", "GREEN:3:4", "RED:4:3", "RED:5:4", "RED:3:4",}, delimiterString = ":")
    @ParameterizedTest
    void 졸병의_정상적인_움직임을_테스트한다(Team team, int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(4, 4);
        Position goal = createPosition(column, row);
        Soldier piece = new Soldier(team);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatCode(() -> piece.validateMovable(board, start, goal))
                .doesNotThrowAnyException();
    }
}
