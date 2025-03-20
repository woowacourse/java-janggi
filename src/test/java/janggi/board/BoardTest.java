package janggi.board;

import janggi.Team;
import janggi.fixture.BoardFixture;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @Test
    void 출발_지점에_기물이_존재하지_않으면_예외를_발생한다() {
        // given
        Board board = BoardFixture.createBasicBoard();
        Position start = new Position(0, 1);
        Position goal = new Position(0, 2);

        // when && then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출발 지점에 기물이 존재하지 않습니다.");
    }

    @Test
    void 출발_지점에_기물이_존재하면_예외가_발생하지_않는다() {
        // given
        Board board = BoardFixture.createBasicBoard();
        Position start = new Position(0, 0);
        Position goal = new Position(0, 2);

        // when && then
        assertThatCode(() -> board.movePiece(start, goal, Team.GREEN))
                .doesNotThrowAnyException();
    }

    @Test
    void 목적지에_같은_진영의_기물이_있는_경우_예외가_발생한다() {
        Board board = BoardFixture.createBasicBoard();
        Position start = new Position(2, 0);
        Position goal = new Position(1, 2);

        // when && then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 목적지에 같은 진영의 기물이 있어 이동할 수 없습니다.");
    }

    @Test
    void 목적지에_같은_진영의_기물이_없는_경우_정상_작동한다() {
        Board board = BoardFixture.createBasicBoard();
        Position start = new Position(2, 0);
        Position goal = new Position(3, 2);

        // when && then
        assertThatCode(() -> board.movePiece(start, goal, Team.GREEN))
                .doesNotThrowAnyException();
    }

    @Test
    void 다른_진영의_기물을_움직일_경우_예외를_발생한다() {
        Board board = BoardFixture.createBasicBoard();
        Position start = new Position(0, 0);
        Position goal = new Position(0, 1);

        // when && then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.RED))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 진영의 기물만 움직일 수 있습니다.");
    }

    @Test
    void 왕이_죽으면_게임_종료_예외를_발생한다() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position redGeneralPosition = createPosition(4, 8);
        Position greenChariotPosition = createPosition(4, 2);

        initialBoard.put(redGeneralPosition, new General(Team.RED));
        initialBoard.put(greenChariotPosition, new Chariot(Team.GREEN));
        Board board = new Board(initialBoard);

        // when && then
        assertThatThrownBy(() -> board.movePiece(greenChariotPosition, redGeneralPosition, Team.GREEN))
                .isInstanceOf(GameOverException.class);
    }
}
