package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import exception.JanggiGameException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("플레이어가 선택한 기물과 플레이어가 가고자 하는 위치에 같은 팀 기물이 존재한다면 이동할 수 없다.")
    void input_board_out_of_range_test() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Piece horsePiece = new Piece(PieceProperty.of(PieceType.HORSE, Team.GREEN), Position.of(3, 3));
        Piece soldierPiece = new Piece(PieceProperty.of(PieceType.GREEN_SOLDIER, Team.GREEN), Position.of(5, 2));
        testBoard.put(horsePiece.position(), horsePiece);
        testBoard.put(soldierPiece.position(), soldierPiece);
        Board board = Board.of(testBoard);

        Position selectPiecePosition = horsePiece.position();
        Position targetPosition = soldierPiece.position();

        assertThatThrownBy(() -> board.movePiece(selectPiecePosition, targetPosition))
                .isExactlyInstanceOf(JanggiGameException.class);
    }

    @Test
    @DisplayName("플레이어가 선택한 기물을 이동시키면, 기물이 있던 자리는 빈 칸이 된다.")
    void board_move_piece_test() {
        Map<Position, Piece> testBoard = new HashMap<>();

        Position selectPosition = Position.of(3, 3);
        Position targetPosition = Position.of(5, 2);

        Piece select = new Piece(PieceProperty.of(PieceType.HORSE, Team.GREEN), selectPosition);
        Piece target = new Piece(PieceProperty.of(PieceType.RED_SOLDIER, Team.RED), targetPosition);

        testBoard.put(select.position(), select);
        testBoard.put(target.position(), target);

        Board board = Board.of(testBoard);

        board.movePiece(selectPosition, targetPosition);

        assertThat(board.findPieceAt(selectPosition).isNone()).isTrue();
        assertThat(board.findPieceAt(selectPosition).isRedTeam()).isFalse();

        assertThat(board.findPieceAt(targetPosition).isGreenTeam()).isTrue();
    }


    @Test
    @DisplayName("포는 포를 넘을 수 없다.")
    void cannon_can_not_jump_cannon() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Piece selected = new Piece(PieceProperty.of(PieceType.CANNON, Team.GREEN), Position.of(3, 3));
        Piece fixed = new Piece(PieceProperty.of(PieceType.CANNON, Team.GREEN), Position.of(5, 3));
        Piece destination = new Piece(PieceProperty.none(), Position.of(7, 3));

        testBoard.put(selected.position(), selected);
        testBoard.put(fixed.position(), fixed);
        testBoard.put(destination.position(), destination);
        Board board = Board.of(testBoard);

        Position selectPiecePosition = selected.position();
        Position targetPosition = destination.position();

        assertThatThrownBy(() -> board.movePiece(selectPiecePosition, targetPosition))
                .isExactlyInstanceOf(JanggiGameException.class);
    }

    @Test
    @DisplayName("포는 포를 잡을 수 없다.")
    void cannon_can_not_catch_cannon() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Piece selected = new Piece(PieceProperty.of(PieceType.CANNON, Team.GREEN), Position.of(3, 3));
        Piece fixed = new Piece(PieceProperty.of(PieceType.HORSE, Team.GREEN), Position.of(5, 3));
        Piece destination = new Piece(PieceProperty.of(PieceType.CANNON, Team.RED), Position.of(7, 3));

        testBoard.put(selected.position(), selected);
        testBoard.put(fixed.position(), fixed);
        testBoard.put(destination.position(), destination);
        Board board = Board.of(testBoard);

        Position selectPiecePosition = selected.position();
        Position targetPosition = destination.position();

        assertThatThrownBy(() -> board.movePiece(selectPiecePosition, targetPosition))
                .isExactlyInstanceOf(JanggiGameException.class);
    }
}
