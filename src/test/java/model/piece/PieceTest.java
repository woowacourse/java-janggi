package model.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import janggi.Team;
import janggi.board.Board;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.Horse;
import janggi.piece.Palace;
import janggi.piece.Pao;
import janggi.piece.Pawn;
import janggi.piece.Piece;
import janggi.piece.Soldier;

class PieceTest {

    private Board board = new Board(List.of());

    @Test
    @DisplayName("궁은 적절한 이동이 가능하다")
    void palaceMoveTest() {
        Piece p = new Palace(5, 5, Team.CHO);
        p.move(board, Team.CHO, 1, 0);
        assertThat(p.getPosition().x()).isEqualTo(6);
        assertThat(p.getPosition().y()).isEqualTo(5);
    }

    @Test
    @DisplayName("궁의 이동 범위를 벗어나면 예외를 반환한다")
    void palaceMoveExceptionTest() {
        Piece p = new Palace(5, 5, Team.CHO);
        assertThatThrownBy(() -> p.move(board, Team.CHO, 2, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 도달할 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("차는 적절한 이동이 가능하다")
    void chariotMoveTest() {
        Piece p = new Chariot(5, 5, Team.CHO);
        p.move(board, Team.CHO, 3, 0);
        assertThat(p.getPosition().x()).isEqualTo(8);
        assertThat(p.getPosition().y()).isEqualTo(5);
    }

    @Test
    @DisplayName("차의 이동 범위를 벗어나면 예외를 반환한다")
    void chariotMoveExceptionTest() {
        Piece p = new Palace(5, 5, Team.CHO);
        assertThatThrownBy(() -> p.move(board, Team.CHO, 2, 2))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 도달할 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("포는 적절한 이동이 가능하다")
    void paoMoveTest() {
        Piece p = new Pao(5, 5, Team.CHO);
        board = new Board(List.of(new Chariot(7, 5, Team.CHO), p));
        p.move(board, Team.CHO, 3, 0);
        assertThat(p.getPosition().x()).isEqualTo(8);
        assertThat(p.getPosition().y()).isEqualTo(5);
    }

    @Test
    @DisplayName("포의 이동 범위를 벗어나면 예외를 반환한다")
    void paoMoveExceptionTest() {
        Piece p = new Pao(5, 5, Team.CHO);
        board = new Board(List.of(p));
        assertThatThrownBy(() -> p.move(board, Team.CHO, 3, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 도달할 수 없는 위치입니다.");

        assertThatThrownBy(() -> p.move(board, Team.CHO, 1, 1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 도달할 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("마는 적절한 이동이 가능하다")
    void horseMoveTest() {
        Piece p = new Horse(5, 5, Team.CHO);
        p.move(board, Team.CHO, 2, 1);
        assertThat(p.getPosition().x()).isEqualTo(7);
        assertThat(p.getPosition().y()).isEqualTo(6);
    }

    @Test
    @DisplayName("마의 이동 범위를 벗어나면 예외를 반환한다")
    void horseMoveExceptionTest() {
        Piece p = new Horse(5, 5, Team.CHO);
        assertThatThrownBy(() -> p.move(board, Team.CHO, 3, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 도달할 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("상은 적절한 이동이 가능하다")
    void elephantMoveTest() {
        Piece p = new Elephant(5, 5, Team.CHO);
        p.move(board, Team.CHO, 3, 2);
        assertThat(p.getPosition().x()).isEqualTo(8);
        assertThat(p.getPosition().y()).isEqualTo(7);
    }

    @Test
    @DisplayName("상의 이동 범위를 벗어나면 예외를 반환한다")
    void elephantMoveExceptionTest() {
        Piece p = new Elephant(5, 5, Team.CHO);
        assertThatThrownBy(() -> p.move(board, Team.CHO, 3, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 도달할 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("사는 적절한 이동이 가능하다")
    void soldierMoveTest() {
        Piece p = new Soldier(5, 5, Team.CHO);
        p.move(board, Team.CHO, 1, 0);
        assertThat(p.getPosition().x()).isEqualTo(6);
        assertThat(p.getPosition().y()).isEqualTo(5);
    }

    @Test
    @DisplayName("사의 이동 범위를 벗어나면 예외를 반환한다")
    void soldierMoveExceptionTest() {
        Piece p = new Soldier(5, 5, Team.CHO);
        assertThatThrownBy(() -> p.move(board, Team.CHO, 3, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 도달할 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("병은 적절한 이동이 가능하다")
    void pawnMoveTest() {
        Piece cho = new Pawn(5, 5, Team.CHO);
        Piece han = new Pawn(5, 5, Team.HAN);
        cho.move(board, Team.CHO, 0, -1);
        han.move(board, Team.HAN, 0, 1);
        assertThat(cho.getPosition().x()).isEqualTo(5);
        assertThat(cho.getPosition().y()).isEqualTo(4);
        assertThat(han.getPosition().x()).isEqualTo(5);
        assertThat(han.getPosition().y()).isEqualTo(6);
    }

    @Test
    @DisplayName("병의 이동 범위를 벗어나면 예외를 반환한다")
    void pawnMoveExceptionTest() {
        Piece cho = new Pawn(5, 5, Team.CHO);
        Piece han = new Pawn(5, 5, Team.HAN);
        assertThatThrownBy(() -> cho.move(board, Team.CHO, 0, 1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 도달할 수 없는 위치입니다.");
        assertThatThrownBy(() -> han.move(board, Team.HAN, 0, -1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 도달할 수 없는 위치입니다.");
    }
}
