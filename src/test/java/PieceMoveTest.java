import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceMoveTest {

    private Board board = new Board(List.of());

    @Test
    @DisplayName("장기판 밖으로 나갈 경우 예외를 반환한다.")
    void outOfBoardMoveTest() {
        Piece p = new Palace(0,0, Team.CHO);
        assertThatThrownBy(() -> p.move(board, 0, -1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("도착 칸에 같은 팀의 기물이 존재할 경우 예외를 반환한다.")
    void arrivalPositionOnOtherMyPieceTest() {
        Palace p = new Palace(5, 4, Team.CHO);
        board = new Board(List.of(new Pawn(5, 5, Team.CHO), p));
        assertThatThrownBy(() -> p.move(board, 0, 1))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
