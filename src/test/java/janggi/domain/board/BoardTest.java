package janggi.domain.board;

import janggi.domain.piece.Implementation.Cha;
import janggi.domain.piece.Implementation.Jol;
import janggi.domain.piece.Implementation.Ma;
import janggi.domain.piece.Implementation.Pho;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Implementation.Sang;
import janggi.domain.point.Point;
import janggi.domain.status.Team;
import java.util.LinkedHashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Board board;

    @Test
    @DisplayName("자신의 기물이 목적지에 있을 경우 예외 발생")
    void can_not_move() {
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        pieces.put(Point.of(0, 0), new Cha(Team.CHO));
        pieces.put(Point.of(0, 1), new Ma(Team.CHO));
        board = new Board(pieces);
        Assertions.assertThatThrownBy(() -> board.move(Point.of(0,0), Point.of(0, 1), Team.CHO))
               .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 도착지에 본인의 기물이 있습니다.");
    }

    @Test
    @DisplayName("목적지에 해당 기물의 이동 규칙으로 갈 수 없으면 예외 발생")
    void can_not_move_rule() {
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        pieces.put(Point.of(0, 0), new Ma(Team.HAN));
        board = new Board(pieces);
        Assertions.assertThatThrownBy(() -> board.move(Point.of(0, 0), Point.of(1, 1), Team.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 마가 이동할 수 없는 방향입니다.");
    }

    @Test
    @DisplayName("자신의 기물이 아닌 기물을 이동하려고 하면 예외 발생")
    void can_not_move_other_piece() {
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        pieces.put(Point.of(0, 0), new Jol(Team.CHO));
        board = new Board(pieces);
        Assertions.assertThatThrownBy(() -> board.move(Point.of(0, 0), Point.of(0, 1), Team.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 상대방의 기물은 움직일 수 없습니다.");
    }

    @Test
    @DisplayName("경유지에 기물이 있으면 예외 발생")
    void can_not_move_has_obstacle() {
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        pieces.put(Point.of(0, 0), new Sang(Team.CHO));
        pieces.put(Point.of(0, 1), new Ma(Team.HAN));
        board = new Board(pieces);
        Assertions.assertThatThrownBy(() -> board.move(Point.of(0, 0), Point.of(2, 3), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 기물의 이동 경로에 장애물이 있거나 규칙에 어긋납니다.");
    }

    @Test
    @DisplayName("포를 움직일 때, 도착지에 같은 포가 있으면 움직일 수 없다.")
    void can_not_catch_piece() {
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        pieces.put(Point.of(0, 0), new Pho(Team.CHO));
        pieces.put(Point.of(0, 2), new Ma(Team.HAN));
        pieces.put(Point.of(0, 4), new Pho(Team.HAN));
        board = new Board(pieces);
        Assertions.assertThatThrownBy(() -> board.move(Point.of(0, 0), Point.of(0, 4), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 이 기물은 해당 타겟을 잡을 수 없습니다.");
    }

    @Test
    @DisplayName("궁성 안에서 차를 움직일 때, (4, 0)에서는 대각선으로 움직일 수 없다.")
    void can_move_pho_diagonal() {
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        pieces.put(Point.of(4, 0), new Cha(Team.HAN));
        board = new Board(pieces);
        Assertions.assertThatThrownBy(() -> board.move(Point.of(4, 0), Point.of(5, 1), Team.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 이동할 수 없는 방향입니다.");
    }

    @Test
    @DisplayName("초나라의 기물이 차와 포만 있으면 총합은 20이다.")
    void cho_total_score() {
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        pieces.put(Point.of(0, 0), new Cha(Team.CHO));
        pieces.put(Point.of(1, 1), new Pho(Team.CHO));
        board = new Board(pieces);
        Assertions.assertThat(board.calculateScore(Team.CHO)).isEqualTo(20);
    }
}
