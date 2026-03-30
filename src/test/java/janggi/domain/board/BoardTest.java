package janggi.domain.board;

import janggi.domain.movestrategy.ChariotStrategy;
import janggi.domain.movestrategy.SoliderStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @Test
    @DisplayName("기물이 없는 좌표에서 이동을 시작하면 예외가 발생한다.")
    void testStartMoveFromEmptyPosition() {
        // given
        Map<Position, Piece> pieces = new LinkedHashMap<>();
        pieces.put(new Position(1, 1), new Piece(Team.HAN, new ChariotStrategy()));
        Board board = new Board(pieces);

        // when & then
        assertThatThrownBy(() -> board.move(new Position(5, 5), new Position(5, 6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표에는 기물이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("시작 좌표에 상대 팀 기물이 있다면 예외가 발생한다.")
    void testStartMoveFromOtherSideTeamPiece() {
        // given
        Map<Position, Piece> pieces = new LinkedHashMap<>();
        pieces.put(new Position(1, 10), new Piece(Team.CHO, new ChariotStrategy()));
        Board board = new Board(pieces);

        // when & then
        assertThatThrownBy(() -> board.move(new Position(1, 10), new Position(1, 9)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 턴의 기물이 아닙니다.");
    }

    @Test
    @DisplayName("이동 성공 시 기물의 위치가 변경된다.")
    void testChangesPosition() {
        // given
        Map<Position, Piece> pieces = new LinkedHashMap<>();
        pieces.put(new Position(1, 1), new Piece(Team.HAN, new ChariotStrategy()));
        Board board = new Board(pieces);

        // when
        board.move(new Position(1, 1), new Position(1, 5));

        // then
        assertThat(board.getBoard().containsKey(new Position(1, 5))).isTrue();
        assertThat(board.getBoard().containsKey(new Position(1, 1))).isFalse();
    }

    @Test
    @DisplayName("경로에 기물이 있으면 차는 이동할 수 없다.")
    void testChariotBlockedByPieceInPath() {
        // given
        Map<Position, Piece> pieces = new LinkedHashMap<>();
        pieces.put(new Position(1, 1), new Piece(Team.HAN, new ChariotStrategy()));
        pieces.put(new Position(1, 3), new Piece(Team.CHO, new SoliderStrategy(Team.CHO)));
        Board board = new Board(pieces);

        // when & then
        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("도착지에 같은 팀 기물이 있으면 이동할 수 없다.")
    void testNotMoveSameSidePieceAtDestination() {
        // given
        Map<Position, Piece> pieces = new LinkedHashMap<>();
        pieces.put(new Position(1, 1), new Piece(Team.HAN, new ChariotStrategy()));
        pieces.put(new Position(1, 5), new Piece(Team.HAN, new ChariotStrategy()));
        Board board = new Board(pieces);

        // when & then
        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    @DisplayName("도착지에 상대 팀 기물이 있으면 잡고 이동한다.")
    void testChangePositionWhenOtherSidePieceAtDestination() {
        // given
        Map<Position, Piece> pieces = new LinkedHashMap<>();
        pieces.put(new Position(1, 1), new Piece(Team.HAN, new ChariotStrategy()));
        pieces.put(new Position(1, 5), new Piece(Team.CHO, new SoliderStrategy(Team.CHO)));
        Board board = new Board(pieces);

        // when & then
        board.move(new Position(1, 1), new Position(1, 5));
        assertThat(board.getBoard().get(new Position(1, 5))).isInstanceOf(Piece.class);
        assertThat(board.getBoard().size()).isEqualTo(1);
    }
}
