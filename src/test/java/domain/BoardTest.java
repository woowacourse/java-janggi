package domain;

import domain.strategy.CannonMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.NonMoveableStrategy;
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
                HorseMoveStrategy.of(new Position(3, 3)));
        Piece soldierPiece = new Piece(PieceProperty.of(PieceType.SOLDIER, Team.GREEN),
                HorseMoveStrategy.of(new Position(5, 2)));
        testBoard.put(horsePiece.currentPosition(), horsePiece);
        testBoard.put(soldierPiece.currentPosition(), soldierPiece);
        Board board = Board.of(testBoard);

        Position selectPiecePosition = horsePiece.currentPosition();
        Position targetPosition = soldierPiece.currentPosition();

        Assertions.assertThat(board.canMove(selectPiecePosition, targetPosition)).isFalse();
    }

    @Test
    @DisplayName("플레이어가 선택한 기물을 이동시키면, 기물이 있던 자리는 빈 칸이 된다.")
    void board_move_piece_test() {
        Map<Position, Piece> testBoard = new HashMap<>();

        Position selectPosition = new Position(3, 3);
        Position targetPosition = new Position(5, 2);

        Piece select = new Piece(PieceProperty.of(PieceType.HORSE, Team.GREEN), HorseMoveStrategy.of(selectPosition));
        Piece target = new Piece(PieceProperty.of(PieceType.SOLDIER, Team.RED), HorseMoveStrategy.of(targetPosition));

        testBoard.put(select.currentPosition(), select);
        testBoard.put(target.currentPosition(), target);

        Board board = Board.of(testBoard);

        board.movePiece(selectPosition, targetPosition);

        Assertions.assertThat(board.greenPieces()).hasSize(1);
        Assertions.assertThat(board.redPieces()).hasSize(0);
        Assertions.assertThat(board.nonePieces()).hasSize(1);
    }

    @Test
    @DisplayName("플레이어가 선택한 기물이 목적지로 이동할 수 있다.")
    void canMoveTo_test() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Piece horsePiece = new Piece(PieceProperty.of(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.of(new Position(3, 3)));
        Piece soldierPiece = new Piece(PieceProperty.of(PieceType.SOLDIER, Team.RED),
                HorseMoveStrategy.of(new Position(5, 2)));
        testBoard.put(horsePiece.currentPosition(), horsePiece);
        testBoard.put(soldierPiece.currentPosition(), soldierPiece);
        Board board = Board.of(testBoard);

        Position selectPiecePosition = horsePiece.currentPosition();
        Position targetPosition = soldierPiece.currentPosition();

        Assertions.assertThat(board.canMove(selectPiecePosition, targetPosition)).isTrue();
    }

    @Test
    @DisplayName("포는 포를 넘을 수 없다.")
    void cannon_can_not_jump_cannon() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Piece selected = new Piece(PieceProperty.of(PieceType.CANNON, Team.GREEN),
                CannonMoveStrategy.of(new Position(3, 3)));

        Piece fixed = new Piece(PieceProperty.of(PieceType.CANNON, Team.GREEN),
                CannonMoveStrategy.of(new Position(5, 3)));

        Piece destination = new Piece(PieceProperty.of(PieceType.EMPTY_VALUE, Team.NONE),
                NonMoveableStrategy.of(new Position(7, 3)));

        testBoard.put(selected.currentPosition(), selected);
        testBoard.put(fixed.currentPosition(), fixed);
        testBoard.put(destination.currentPosition(), destination);
        Board board = Board.of(testBoard);

        Position selectPiecePosition = selected.currentPosition();
        Position targetPosition = destination.currentPosition();

        Assertions.assertThat(board.canMove(selectPiecePosition, targetPosition)).isFalse();
    }

    @Test
    @DisplayName("포는 포를 잡을 수 없다.")
    void cannon_can_not_catch_cannon() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Piece selected = new Piece(PieceProperty.of(PieceType.CANNON, Team.GREEN),
                CannonMoveStrategy.of(new Position(3, 3)));

        Piece fixed = new Piece(PieceProperty.of(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.of(new Position(5, 3)));

        Piece destination = new Piece(PieceProperty.of(PieceType.CANNON, Team.RED),
                CannonMoveStrategy.of(new Position(7, 3)));

        testBoard.put(selected.currentPosition(), selected);
        testBoard.put(fixed.currentPosition(), fixed);
        testBoard.put(destination.currentPosition(), destination);
        Board board = Board.of(testBoard);

        Position selectPiecePosition = selected.currentPosition();
        Position targetPosition = destination.currentPosition();

        Assertions.assertThat(board.canMove(selectPiecePosition, targetPosition)).isFalse();
    }
}
