package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.Team;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    void 기물이_이동하면_원래_위치는_빈칸이_된다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.HAN));
        Board board = new Board(pieces);

        board.move(new Position(1, 1), new Position(1, 5));

        assertThat(board.pieceAt(new Position(1, 1)).isNotEmpty()).isFalse();
    }

    @Test
    void 기물이_이동하면_도착_위치에_기물이_있다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.HAN));
        Board board = new Board(pieces);

        board.move(new Position(1, 1), new Position(1, 5));

        assertThat(board.pieceAt(new Position(1, 5)).isNotEmpty()).isTrue();
    }

    @Test
    void 이동_경로에_기물이_있으면_예외() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.HAN));
        pieces.put(new Position(1, 3), new Soldier(Team.CHO));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 있습니다.");
    }

    @Test
    void 아군_기물이_있는_위치로_이동하면_예외() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.HAN));
        pieces.put(new Position(1, 5), new Chariot(Team.HAN));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아군 기물이 있는 위치로 이동할 수 없습니다.");
    }

    @Test
    void 적_기물을_잡으면_도착지에_내_기물이_있다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.HAN));
        pieces.put(new Position(1, 5), new Chariot(Team.CHO));
        Board board = new Board(pieces);

        board.move(new Position(1, 1), new Position(1, 5));

        assertThat(board.pieceAt(new Position(1, 5))).isInstanceOf(Chariot.class);
        assertThat(board.pieceAt(new Position(1, 1))).isInstanceOf(EmptyPiece.class);
    }

    @Test
    void 이동할_수_없는_방향이면_예외() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(5, 5), new Chariot(Team.HAN));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(5, 5), new Position(3, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @Test
    void 포는_기물_하나를_넘어_이동한다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Cannon(Team.HAN));
        pieces.put(new Position(1, 3), new Soldier(Team.CHO));
        Board board = new Board(pieces);

        board.move(new Position(1, 1), new Position(1, 5));

        assertThat(board.pieceAt(new Position(1, 5)).isNotEmpty()).isTrue();
        assertThat(board.pieceAt(new Position(1, 1)).isNotEmpty()).isFalse();
    }

    @Test
    void 포가_넘을_기물이_없으면_예외() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Cannon(Team.HAN));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포가 넘을 수 있는 기물의 개수는 하나입니다.");
    }

    @Test
    void 포가_두_개_이상의_기물을_넘으면_예외() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Cannon(Team.HAN));
        pieces.put(new Position(1, 3), new Soldier(Team.CHO));
        pieces.put(new Position(1, 5), new Soldier(Team.CHO));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포가 넘을 수 있는 기물의 개수는 하나입니다.");
    }

    @Test
    void 포는_포를_넘지_못한다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Cannon(Team.HAN));
        pieces.put(new Position(1, 3), new Cannon(Team.CHO));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 넘지 못합니다.");
    }

    @Test
    void 포는_포를_잡을_수_없다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Cannon(Team.HAN));
        pieces.put(new Position(1, 3), new Soldier(Team.CHO));
        pieces.put(new Position(1, 5), new Cannon(Team.CHO));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 잡을 수 없습니다.");
    }

    @Test
    void 빈_칸에서_이동_시도하면_예외() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), EmptyPiece.getInstance());
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("빈 기물을 선택했습니다. 아군 기물을 선택해 주세요.");
    }
}
