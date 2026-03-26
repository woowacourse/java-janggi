package domain;

import domain.piece.Horse;
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
        Piece horsePiece = new Horse(Team.GREEN, HorseMoveStrategy.of(Position.of(3, 3)));
        Piece soldierPiece = new Horse(Team.GREEN, HorseMoveStrategy.of(Position.of(5, 2)));
        testBoard.put(horsePiece.position(), horsePiece);
        testBoard.put(soldierPiece.position(), soldierPiece);
        Board board = Board.of(testBoard);

        Position selectPiecePosition = horsePiece.position();
        Position targetPosition = soldierPiece.position();

        Assertions.assertThat(board.isMoveable(selectPiecePosition, targetPosition)).isFalse();
    }

}
