package janggi.piece;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;
import janggi.moving.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GeneralTest {
    @DisplayName("초나라_궁이_궁성_밖으로_이동하면_예외를_발생한다")
    @Test
    void aa() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(4, 2);
        Position goal = createPosition(4, 3);
        Path path = new Path(List.of(start, goal));
        General piece = new General(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatThrownBy(() -> piece.validatePath(board, path))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 궁은 궁성을 벗어날 수 없습니다.");
    }

    @DisplayName("한나라_궁이_궁성_밖으로_이동하면_예외를_발생한다")
    @Test
    void aaa() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(3, 7);
        Position goal = createPosition(2, 7);
        Path path = new Path(List.of(start, goal));
        General piece = new General(Team.RED);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatThrownBy(() -> piece.validatePath(board, path))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 궁은 궁성을 벗어날 수 없습니다.");
    }
}
