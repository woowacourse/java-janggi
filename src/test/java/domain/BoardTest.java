package domain;

import domain.piece.Piece;
import domain.strategy.HorseMoveStrategy;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("플레이어가 선택한 기물과 플레이어가 가고자 하는 위치에 같은 팀 기물이 존재한다면 이동할 수 없다.")
    void input_board_out_of_range_test() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Piece horsePiece = new Piece(PieceProperty.of(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.of(Position.of(3, 3)));
        Piece soldierPiece = new Piece(PieceProperty.of(PieceType.SOLDIER, Team.GREEN),
                HorseMoveStrategy.of(Position.of(5, 2)));
        testBoard.put(horsePiece.position(), horsePiece);
        testBoard.put(soldierPiece.position(), soldierPiece);
        Board board = Board.of(testBoard);

        Position selectPiecePosition = horsePiece.position();
        Position targetPosition = soldierPiece.position();

        Assertions.assertThat(board.isMoveable(selectPiecePosition, targetPosition)).isFalse();
    }

    @Test
    @DisplayName("플레이어가 선택한 기물을 이동시키면, 기물이 있던 자리는 빈 칸이 된다.")
    void board_move_piece_test() {
        Map<Position, Piece> testBoard = new HashMap<>();

        Position selectPosition = Position.of(3, 3);
        Position targetPosition = Position.of(5, 2);

        Piece select = new Piece(PieceProperty.of(PieceType.HORSE, Team.GREEN), HorseMoveStrategy.of(selectPosition));
        Piece target = new Piece(PieceProperty.of(PieceType.SOLDIER, Team.RED), HorseMoveStrategy.of(targetPosition));

        testBoard.put(select.position(), select);
        testBoard.put(target.position(), target);

        Board board = Board.of(testBoard);

        board.movePiece(selectPosition, targetPosition);

        Assertions.assertThat(board.greenPieces()).hasSize(1);
        Assertions.assertThat(board.redPieces()).hasSize(0);
        Assertions.assertThat(board.nonePieces()).hasSize(1);
    }
}
