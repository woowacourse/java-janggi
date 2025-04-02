package model.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Position;
import model.Team;
import model.piece.Chariot;
import model.piece.King;
import model.piece.Pao;
import model.piece.Pawn;
import model.piece.Piece;

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
        Piece p = new King(0, 0, Team.CHO);
        Board board = new Board(List.of(p));
        assertThatThrownBy(() -> board.movePiece(new Position(0, 0), new Position(0, -1), Team.CHO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 장기판 내에서만 이동할 수 있습니다.");
    }

    @Test
    @DisplayName("도착 칸에 다른 팀의 기물이 존재할 경우 해당 기물을 잡는다.")
    void takePieceTest() {
        Piece p = new Chariot(5, 4, Team.CHO);
        Board board = new Board(List.of(new Pawn(5, 5, Team.HAN), p));
        board.movePiece(p.getPosition(), new Position(5, 5), Team.CHO);
        assertThat(board.get(new Position(5, 5))).isEqualTo(p);
    }

    @Test
    @DisplayName("왕이 잡히면 게임이 종료된다.")
    void gameOverTest() {
        Board board = new Board(List.of(new King(6, 5, Team.CHO)));
        assertThat(board.getWinnerIfGameOver()).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("최종 기물 점수를 올바르게 반환한다.")
    void pieceScoreTest() {
        Board board = new Board(
            List.of(new King(6, 5, Team.CHO), new Pao(1, 0, Team.CHO), new Chariot(1, 2, Team.HAN)));
        assertThat(board.getPieceScore()).containsEntry(Team.CHO, 7);
        assertThat(board.getPieceScore()).containsEntry(Team.HAN, 13);
    }
}
