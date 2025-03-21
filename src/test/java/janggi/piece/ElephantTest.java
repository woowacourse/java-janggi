package janggi.piece;

import janggi.Team;
import janggi.board.Board;
import janggi.board.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

class ElephantTest {
    @CsvSource(value = {"2:7", "6:7", "6:1", "7:6", "7:2", "2:1", "1:2", "1:6"}, delimiterString = ":")
    @ParameterizedTest
    void 상의_정상적인_움직임을_테스트한다(int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 4);
        Position goal = createPosition(column, row);
        Elephant piece = new Elephant(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // when
        board.movePiece(start, goal, Team.GREEN);

        // then
        assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }

    @Test
    void 이동_경로에_다른_기물이_있을_경우_예외를_발생한다() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(4, 4);
        Position goal = createPosition(6, 7);
        Elephant piece = new Elephant(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(createPosition(4,5), new Soldier(Team.GREEN));
        Board board = new Board(initialBoard);

        // when
        // then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 경로에 다른 기물이 있어 이동할 수 없습니다.");
    }
}
