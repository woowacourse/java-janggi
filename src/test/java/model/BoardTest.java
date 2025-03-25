package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.board.Board;
import model.piece.Piece;
import model.piece.normal.Palace;
import model.piece.normal.Pawn;

class BoardTest {

    @Test
    @DisplayName("9(가로) x 10(세로) 크기의 장기판을 사용한다.")
    void isInBoardTest() {
        Board board = new Board(List.of());
        assertThat(board.isInBoard(new Position(9, 0))).isFalse();
        assertThat(board.isInBoard(new Position(0, 10))).isFalse();
    }

    @Test
    @DisplayName("장기판 밖으로 나갈 경우 예외를 반환한다.")
    void outOfBoardMoveTest() {
        Piece p = new Palace(0, 0, Team.CHO);
        Board board = new Board(List.of(p));
        assertThatThrownBy(() -> board.movePiece(new Position(0,0), new Position(0, -1), Team.CHO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 장기판 내에서만 이동할 수 있습니다.");
    }

    @Test
    @DisplayName("도착 칸에 다른 팀의 기물이 존재할 경우 해당 기물을 잡는다.")
    void takePieceTest() {
        Piece p = new Palace(5, 4, Team.CHO);
        Board board = new Board(List.of(new Pawn(5, 5, Team.HAN), p));
        board.movePiece(p.getPosition(), new Position(5, 5), Team.CHO);
        assertThat(board.get(new Position(5, 5))).isEqualTo(p);
    }

    @Test
    @DisplayName("왕이 잡히면 게임이 종료된다.")
    void gameOverTest() {
        Board board = new Board(List.of(new Palace(6, 5, Team.CHO)));
        assertThat(board.getWinnerIfGameOver()).isEqualTo(Team.CHO);
    }
}
