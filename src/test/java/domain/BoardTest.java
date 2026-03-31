package domain;

import domain.strategy.CannonMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.NonMoveableStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("플레이어가 선택한 기물과 플레이어가 가고자 하는 위치에 같은 팀 기물이 존재한다면 이동할 수 없다.")
    void input_board_out_of_range_test() {
        Piece horsePiece = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.of(new Position(3, 3)));
        Piece soldierPiece = Piece.of(new PieceProperty(PieceType.SOLDIER, Team.GREEN),
                HorseMoveStrategy.of(new Position(5, 2)));

        Map<Position, Piece> testBoard = new HashMap<>();
        putPiecesOnBoard(testBoard, List.of(horsePiece, soldierPiece));
        Board board = Board.of(testBoard);

        Position selectPiecePosition = horsePiece.currentPosition();
        Position targetPosition = soldierPiece.currentPosition();

        Assertions.assertThat(board.canMove(selectPiecePosition, targetPosition)).isFalse();
    }

    @Test
    @DisplayName("플레이어가 선택한 기물을 이동시키면, 기물이 있던 자리는 빈 칸이 된다.")
    void board_move_piece_test() {
        Piece select = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.of(new Position(3, 3)));
        Piece target = Piece.of(new PieceProperty(PieceType.SOLDIER, Team.RED),
                HorseMoveStrategy.of(new Position(5, 2)));
        Map<Position, Piece> testBoard = new HashMap<>();

        putPiecesOnBoard(testBoard, List.of(select, target));
        Board board = Board.of(testBoard);
        board.movePiece(select.currentPosition(), target.currentPosition());

        Assertions.assertThat(board.greenPieces()).hasSize(1);
        Assertions.assertThat(board.redPieces()).hasSize(0);
        Assertions.assertThat(board.nonePieces()).hasSize(1);
    }

    @Test
    @DisplayName("플레이어가 선택한 기물이 목적지로 이동할 수 있다.")
    void canMoveTo_test() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Piece horsePiece = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.of(new Position(3, 3)));
        Piece soldierPiece = Piece.of(new PieceProperty(PieceType.SOLDIER, Team.RED),
                HorseMoveStrategy.of(new Position(5, 2)));

        putPiecesOnBoard(testBoard, List.of(horsePiece, soldierPiece));
        Board board = Board.of(testBoard);

        Position selectPiecePosition = horsePiece.currentPosition();
        Position targetPosition = soldierPiece.currentPosition();

        Assertions.assertThat(board.canMove(selectPiecePosition, targetPosition)).isTrue();
    }

    @Test
    @DisplayName("포는 포를 넘을 수 없다.")
    void cannon_can_not_jump_cannon() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Piece selected = Piece.of(new PieceProperty(PieceType.CANNON, Team.GREEN),
                CannonMoveStrategy.of(new Position(3, 3)));

        Piece fixed = Piece.of(new PieceProperty(PieceType.CANNON, Team.GREEN),
                CannonMoveStrategy.of(new Position(5, 3)));

        Piece destination = Piece.of(new PieceProperty(PieceType.EMPTY_VALUE, Team.NONE),
                NonMoveableStrategy.of(new Position(7, 3)));

        testBoard.put(selected.currentPosition(), selected);
        testBoard.put(fixed.currentPosition(), fixed);
        testBoard.put(destination.currentPosition(), destination);
        Board board = Board.of(testBoard);

        Assertions.assertThat(board.canMove(selected.currentPosition(), destination.currentPosition())).isFalse();
    }

    @Test
    @DisplayName("포는 포를 잡을 수 없다.")
    void cannon_can_not_catch_cannon() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Piece selected = Piece.of(new PieceProperty(PieceType.CANNON, Team.GREEN),
                CannonMoveStrategy.of(new Position(3, 3)));

        Piece fixed = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN), HorseMoveStrategy.of(new Position(5, 3)));

        Piece destination = Piece.of(new PieceProperty(PieceType.CANNON, Team.RED),
                CannonMoveStrategy.of(new Position(7, 3)));

        putPiecesOnBoard(testBoard, List.of(selected, fixed, destination));
        Board board = Board.of(testBoard);

        Assertions.assertThat(board.canMove(selected.currentPosition(), destination.currentPosition())).isFalse();
    }

    @Test
    @DisplayName("포는 이동 경로에 기물이 2개 이상 있다면 이동할 수 없다.")
    void cannon_can_not_move_when_two_or_more_pieces_block_the_path() {
        Piece selected = Piece.of(new PieceProperty(PieceType.CANNON, Team.GREEN),
                CannonMoveStrategy.of(new Position(3, 3)));

        Piece fixedHorse = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.of(new Position(5, 3)));
        Piece fixedChariot = Piece.of(new PieceProperty(PieceType.CHARIOT, Team.GREEN),
                HorseMoveStrategy.of(new Position(6, 3)));

        Piece destination = Piece.of(new PieceProperty(PieceType.CANNON, Team.RED),
                CannonMoveStrategy.of(new Position(7, 3)));

        Map<Position, Piece> testBoard = new HashMap<>();
        putPiecesOnBoard(testBoard, List.of(selected, fixedHorse, fixedChariot, destination));
        Board board = Board.of(testBoard);

        Assertions.assertThat(board.canMove(selected.currentPosition(), destination.currentPosition())).isFalse();
    }

    @Test
    @DisplayName("포는 이동 경로에 포가 아닌 기물이 1개 있다면 이동할 수 있다.")
    void cannon_can__move_when_one_pieces_block_the_path() {
        Piece selected = Piece.of(new PieceProperty(PieceType.CANNON, Team.GREEN),
                CannonMoveStrategy.of(new Position(3, 3)));

        Piece fixedHorse = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.of(new Position(5, 3)));

        Piece destination = Piece.of(new PieceProperty(PieceType.EMPTY_VALUE, Team.RED),
                NonMoveableStrategy.of(new Position(7, 3)));

        Map<Position, Piece> testBoard = new HashMap<>();
        putPiecesOnBoard(testBoard, List.of(selected, fixedHorse, destination));
        Board board = Board.of(testBoard);

        Assertions.assertThat(board.canMove(selected.currentPosition(), destination.currentPosition())).isTrue();
    }

    private void putPiecesOnBoard(Map<Position, Piece> testBoard, List<Piece> pieces) {
        for (Piece piece : pieces) {
            testBoard.put(piece.currentPosition(), piece);
        }
    }

}
