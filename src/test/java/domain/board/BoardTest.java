package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.Score;
import domain.game.Scores;
import domain.game.Team;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.PalacePiece;
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
        pieces.put(new Position(1, 1), new Chariot(Team.CHO));
        Board board = new Board(pieces);

        board.move(new Position(1, 1), new Position(1, 5), Team.CHO);

        assertThat(board.getState().get(new Position(1, 1))).isNull();
    }

    @Test
    void 기물이_이동하면_도착_위치에_기물이_있다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.CHO));
        Board board = new Board(pieces);

        board.move(new Position(1, 1), new Position(1, 5), Team.CHO);

        assertThat(board.getState().get(new Position(1, 5))).isNotNull();
    }

    @Test
    void 이동_경로에_기물이_있으면_예외() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.CHO));
        pieces.put(new Position(1, 3), new Soldier(Team.CHO));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 5), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 있습니다.");
    }

    @Test
    void 아군_기물이_있는_위치로_이동하면_예외() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.CHO));
        pieces.put(new Position(1, 5), new Chariot(Team.CHO));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 5), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아군 기물이 있는 위치로 이동할 수 없습니다.");
    }

    @Test
    void 적_기물을_잡으면_도착지에_내_기물이_있다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.CHO));
        pieces.put(new Position(9, 1), new Chariot(Team.HAN));
        Board board = new Board(pieces);

        board.move(new Position(1, 1), new Position(9, 1), Team.CHO);

        assertThat(board.getState().get(new Position(9, 1))).isInstanceOf(Chariot.class);
        assertThat(board.getState().get(new Position(1, 1))).isNull();
    }

    @Test
    void 이동할_수_없는_방향이면_예외() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.CHO));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(2, 2), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @Test
    void 포는_기물_하나를_넘어_이동한다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Cannon(Team.CHO));
        pieces.put(new Position(1, 3), new Soldier(Team.CHO));
        Board board = new Board(pieces);

        board.move(new Position(1, 1), new Position(1, 5), Team.CHO);

        assertThat(board.getState().get(new Position(1, 5))).isNotNull();
        assertThat(board.getState().get(new Position(1, 1))).isNull();
    }

    @Test
    void 포가_넘을_기물이_없으면_예외() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Cannon(Team.CHO));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 5), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포가 넘을 수 있는 기물의 개수는 하나입니다.");
    }

    @Test
    void 포가_두_개_이상의_기물을_넘으면_예외() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Cannon(Team.CHO));
        pieces.put(new Position(1, 3), new Soldier(Team.CHO));
        pieces.put(new Position(1, 5), new Soldier(Team.CHO));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 7), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포가 넘을 수 있는 기물의 개수는 하나입니다.");
    }

    @Test
    void 포는_포를_넘지_못한다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Cannon(Team.CHO));
        pieces.put(new Position(1, 3), new Cannon(Team.CHO));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 5), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 넘지 못합니다.");
    }

    @Test
    void 포는_포를_잡을_수_없다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Cannon(Team.CHO));
        pieces.put(new Position(1, 3), new Soldier(Team.CHO));
        pieces.put(new Position(1, 5), new Cannon(Team.CHO));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 5), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 잡을 수 없습니다.");
    }

    @Test
    void 초_차례에_한_기물을_움직이면_예외() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(9, 1), new Chariot(Team.HAN));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(9, 1), new Position(9, 5), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 차례의 기물만 이동할 수 있습니다.");
    }

    @Test
    void 빈_칸에서_이동_시도하면_예외() {
        Map<Position, Piece> pieces = new HashMap<>();
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 5), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 기물이 없는 위치입니다.");
    }

    @Test
    void 두_장군이_모두_있으면_게임_진행_중() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(2, 5), PalacePiece.general(Team.CHO));
        pieces.put(new Position(9, 5), PalacePiece.general(Team.HAN));
        Board board = new Board(pieces);

        assertThat(board.isGeneralAlive()).isTrue();
    }

    @Test
    void 장군이_하나만_있으면_게임_종료() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(9, 5), PalacePiece.general(Team.HAN));
        Board board = new Board(pieces);

        assertThat(board.isGeneralAlive()).isFalse();
    }

    @Test
    void 초_장군만_남으면_초가_승리() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(2, 5), PalacePiece.general(Team.CHO));
        Board board = new Board(pieces);

        assertThat(board.decideWinner()).isTrue();
    }

    @Test
    void 한_장군만_남으면_한이_승리() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(9, 5), PalacePiece.general(Team.HAN));
        Board board = new Board(pieces);

        assertThat(board.decideWinner()).isFalse();
    }

    @Test
    void 기물이_있는_위치를_조회하면_기물이_반환된다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.CHO));
        Board board = new Board(pieces);

        assertThat(board.findPieceAt(1, 1)).isPresent();
    }

    @Test
    void 기물이_없는_위치를_조회하면_빈값이_반환된다() {
        Map<Position, Piece> pieces = new HashMap<>();
        Board board = new Board(pieces);

        assertThat(board.findPieceAt(1, 1)).isEmpty();
    }

    @Test
    void 기물이_있는_위치는_true를_반환한다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.CHO));
        Board board = new Board(pieces);

        assertThat(board.hasPieceAt(new Position(1, 1))).isTrue();
    }

    @Test
    void 기물이_없는_위치는_false를_반환한다() {
        Map<Position, Piece> pieces = new HashMap<>();
        Board board = new Board(pieces);

        assertThat(board.hasPieceAt(new Position(1, 1))).isFalse();
    }

    @Test
    void 점수_계산_시_각_팀_기물_점수가_합산된다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), new Chariot(Team.CHO));
        pieces.put(new Position(1, 2), new Soldier(Team.CHO));
        pieces.put(new Position(9, 1), new Chariot(Team.HAN));
        Board board = new Board(pieces);

        Scores scores = board.calculateScore();

        assertThat(scores.get(Team.CHO).score()).isEqualTo(15.0);
        assertThat(scores.get(Team.HAN).score()).isEqualTo(14.5);
    }
}
