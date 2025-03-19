package model.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Board;
import model.Position;
import model.Team;

class PieceMoveTest {

    private Board board = new Board(List.of());

    @Test
    @DisplayName("장기판 밖으로 나갈 경우 예외를 반환한다.")
    void outOfBoardMoveTest() {
        Piece p = new Palace(0, 0, Team.CHO);
        assertThatThrownBy(() -> p.move(board, 0, -1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("도착 칸에 같은 팀의 기물이 존재할 경우 예외를 반환한다.")
    void arrivalPositionOnOtherMyPieceTest() {
        Piece p = new Palace(5, 4, Team.CHO);
        board = new Board(List.of(new Pawn(5, 5, Team.CHO), p));
        assertThatThrownBy(() -> p.move(board, 0, 1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("도착 칸에 다른 팀의 기물이 존재할 경우 해당 기물을 잡는다.")
    void takePieceTest() {
        Piece p = new Palace(5, 4, Team.CHO);
        board = new Board(List.of(new Pawn(5, 5, Team.HAN), p));
        p.move(board, 0, 1);
        assertThat(board.get(new Position(5, 5))).isEqualTo(p);
    }

    @Test
    @DisplayName("포가 포를 넘을 경우 예외를 반환한다.")
    void paoNextPaoExceptionTest() {
        Piece p = new Pao(5, 5, Team.CHO);
        board = new Board(List.of(new Pao(6, 5, Team.CHO), p));
        assertThatThrownBy(() -> p.move(board, 2, 0))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("포는 포를 잡을 수 없다.")
    void paoCantTakePaoTest() {
        Piece p = new Pao(5, 4, Team.CHO);
        board = new Board(List.of(new Pawn(5, 5, Team.HAN), new Pao(5, 6, Team.HAN), p));
        assertThatThrownBy(() -> p.move(board, 0, 2))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("기물의 이동 경로에 다른 기물이 존재할 경우 예외를 반환한다. - 일반 기물")
    void routeValidateRouteTest() {
        Piece p = new Elephant(5, 5, Team.CHO);
        board = new Board(List.of(new Pawn(6, 5, Team.CHO), p));
        assertThatThrownBy(() -> p.move(board, 8, 7))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("기물의 이동 경로에 다른 기물이 존재할 경우 예외를 반환한다. - 차")
    void routeValidateRouteChariotTest() {
        Piece p = new Chariot(5, 5, Team.CHO);
        board = new Board(List.of(new Pawn(6, 5, Team.CHO), p));
        assertThatThrownBy(() -> p.move(board, 2, 0))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("기물의 이동 경로에 다른 기물이 존재할 경우 예외를 반환한다. - 포")
    void routeValidateRoutePaoTest() {
        Piece p = new Pao(5, 5, Team.CHO);
        board = new Board(List.of(new Pawn(6, 5, Team.CHO), new Pawn(7, 5, Team.CHO), p));
        assertThatThrownBy(() -> p.move(board, 3, 0))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
