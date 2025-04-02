package janggi.piece;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.PieceType;
import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChariotTest {
    @DisplayName("차는_궁성_내부에서_대각으로_움직일_수_있다")
    @CsvSource(value = {"5:0:4:1", "5:0:3:2", "3:0:4:1", "3:0:5:2", "5:2:4:1", "5:2:3:0", "3:2:4:1", "3:2:5:0"},
            delimiterString = ":")
    @ParameterizedTest
    void validateMovable(
            int startColumn,
            int startRow,
            int goalColumn,
            int goalRow
    ) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Piece piece = PieceCreator.create(Team.GREEN, PieceType.CHARIOT);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatCode(() -> piece.validateMovable(board, start, goal))
                .doesNotThrowAnyException();
    }
}
