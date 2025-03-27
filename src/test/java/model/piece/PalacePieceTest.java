package model.piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Position;
import model.Team;
import model.board.Board;
import model.piece.palace.King;

class PalacePieceTest {

    @Test
    @DisplayName("궁은 궁성에서 사전 정의된 대각선 이동이 가능하다")
    void kingDiagonalMoveTest() {
        Piece piece = new King(4, 1, Team.HAN);
        Board board = new Board(List.of(piece));
        assertThatCode(() -> board.movePiece(new Position(4, 1), new Position(5, 2), Team.HAN))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("궁은 궁성 밖으로 나갈 수 없다.")
    void kingCantMoveOutOfPalace() {
        Piece piece = new King(4, 2, Team.HAN);
        Board board = new Board(List.of(piece));
        assertThatThrownBy(() -> board.movePiece(new Position(4, 2), new Position(4, 3), Team.HAN))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 궁 밖으로 나갈 수 없는 기물입니다.");

    }
}
