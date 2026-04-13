package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.strategy.CannonMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.NonMoveableStrategy;
import factory.JanggiBoardFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("플레이어가 선택한 기물과 플레이어가 가고자 하는 위치에 같은 팀 기물이 존재한다면 이동할 수 없다.")
    void cannot_move_sameTeam_test() {
        Piece currentHorsePiece = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.getInstance());
        Piece targetSoldierPiece = Piece.of(new PieceProperty(PieceType.SOLDIER, Team.GREEN),
                HorseMoveStrategy.getInstance());
        Position current = new Position(3, 3);
        Position target = new Position(5, 2);

        Map<Position, Piece> testBoard = new HashMap<>();
        putPiecesOnBoard(testBoard, List.of(current, target), List.of(currentHorsePiece, targetSoldierPiece));
        Board board = Board.of(testBoard);

        assertThat(board.canMove(current, target)).isFalse();
    }

    @Test
    @DisplayName("플레이어가 선택한 기물을 이동시키면, 기물이 있던 자리는 빈 칸이 된다.")
    void can_move_piece_then_source_becomes_empty_test() {
        Position current = new Position(3, 3);
        Position target = new Position(5, 2);
        Piece currentHorsePiece = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.getInstance());
        Piece targetSoldierPiece = Piece.of(new PieceProperty(PieceType.SOLDIER, Team.RED),
                HorseMoveStrategy.getInstance());
        Map<Position, Piece> testBoard = new HashMap<>();

        putPiecesOnBoard(testBoard, List.of(current, target), List.of(currentHorsePiece, targetSoldierPiece));
        Board board = Board.of(testBoard);
        int noneSize = board.board().size() - board.greenPieces().size();

        assertThat(board.canMove(current, target)).isTrue();
        board.movePiece(current, target);
        assertThat(board.greenPieces()).hasSize(1);
        assertThat(board.redPieces()).hasSize(0);
        assertThat(noneSize).isEqualTo(1);
    }

    @Test
    @DisplayName("플레이어가 선택한 기물이 목적지로 이동할 수 있다.")
    void can_move_test() {
        Position current = new Position(3, 3);
        Position target = new Position(5, 2);
        Piece currentHorsePiece = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.getInstance());
        Piece targetSoldierPiece = Piece.of(new PieceProperty(PieceType.SOLDIER, Team.RED),
                HorseMoveStrategy.getInstance());
        Map<Position, Piece> testBoard = new HashMap<>();

        putPiecesOnBoard(testBoard, List.of(current, target), List.of(currentHorsePiece, targetSoldierPiece));
        Board board = Board.of(testBoard);

        assertThat(board.canMove(current, target)).isTrue();
    }

    @Test
    @DisplayName("포는 포를 넘을 수 없다.")
    void cannot_jump_cannon_test() {
        Piece selected = Piece.of(new PieceProperty(PieceType.CANNON, Team.GREEN),
                CannonMoveStrategy.getInstance());
        Piece fixed = Piece.of(new PieceProperty(PieceType.CANNON, Team.GREEN),
                CannonMoveStrategy.getInstance());
        Piece destination = Piece.of(new PieceProperty(PieceType.EMPTY_VALUE, Team.NONE),
                NonMoveableStrategy.getInstance());
        Position current = new Position(3, 3);
        Position intermediatePiecePosition = new Position(5, 3);
        Position target = new Position(7, 3);
        Map<Position, Piece> testBoard = new HashMap<>();

        putPiecesOnBoard(testBoard, List.of(current, intermediatePiecePosition, target),
                List.of(selected, fixed, destination));
        Board board = Board.of(testBoard);

        assertThat(board.canMove(current, target)).isFalse();
    }

    @Test
    @DisplayName("포는 포를 잡을 수 없다.")
    void cannot_catch_cannon_test() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Piece selected = Piece.of(new PieceProperty(PieceType.CANNON, Team.GREEN),
                CannonMoveStrategy.getInstance());
        Piece fixed = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN), HorseMoveStrategy.getInstance());
        Piece destination = Piece.of(new PieceProperty(PieceType.CANNON, Team.RED),
                CannonMoveStrategy.getInstance());

        Position current = new Position(3, 3);
        Position intermediatePiecePosition = new Position(5, 3);
        Position target = new Position(7, 3);

        putPiecesOnBoard(testBoard, List.of(current, intermediatePiecePosition, target),
                List.of(selected, fixed, destination));
        Board board = Board.of(testBoard);

        assertThat(board.canMove(current, target)).isFalse();
    }

    @Test
    @DisplayName("포는 이동 경로에 기물이 2개 이상 있다면 이동할 수 없다.")
    void cannot_move_when_two_or_more_pieces_block_the_path_test() {
        Piece selected = Piece.of(new PieceProperty(PieceType.CANNON, Team.GREEN),
                CannonMoveStrategy.getInstance());
        Piece fixedHorse = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.getInstance());
        Piece fixedChariot = Piece.of(new PieceProperty(PieceType.CHARIOT, Team.GREEN),
                HorseMoveStrategy.getInstance());
        Piece destination = Piece.of(new PieceProperty(PieceType.CANNON, Team.RED),
                CannonMoveStrategy.getInstance());

        Position current = new Position(3, 3);
        Position firstIntermediatePiecePosition = new Position(5, 3);
        Position secondIntermediatePiecePosition = new Position(6, 3);
        Position target = new Position(7, 3);

        Map<Position, Piece> testBoard = new HashMap<>();
        putPiecesOnBoard(testBoard,
                List.of(current, firstIntermediatePiecePosition, secondIntermediatePiecePosition, target),
                List.of(selected, fixedHorse, fixedChariot, destination));
        Board board = Board.of(testBoard);

        assertThat(board.canMove(current, target)).isFalse();
    }

    @Test
    @DisplayName("포는 이동 경로에 포가 아닌 기물이 1개 있다면 이동할 수 있다.")
    void can_move_when_one_pieces_block_the_path_test() {
        Piece selected = Piece.of(new PieceProperty(PieceType.CANNON, Team.GREEN),
                CannonMoveStrategy.getInstance());
        Piece fixedHorse = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.getInstance());
        Piece destination = Piece.of(new PieceProperty(PieceType.EMPTY_VALUE, Team.NONE),
                NonMoveableStrategy.getInstance());

        Position current = new Position(3, 3);
        Position intermediatePiecePosition = new Position(5, 3);
        Position target = new Position(7, 3);
        Map<Position, Piece> testBoard = new HashMap<>();

        putPiecesOnBoard(testBoard, List.of(current, intermediatePiecePosition, target),
                List.of(selected, fixedHorse, destination));
        Board board = Board.of(testBoard);

        assertThat(board.canMove(current, target)).isTrue();
    }

    @Test
    @DisplayName("각 팀의 남아있는 기물의 점수를 구할 수 있다.")
    void calculate_remaining_pieceScore_test() {
        JanggiBoardFactory janggiBoardFactory = new JanggiBoardFactory();
        Board board = janggiBoardFactory.initialBoard();

        assertThat(board.greenPiecesScore()).isEqualTo(72);
        assertThat(board.redPiecesScore()).isEqualTo(73.5);
    }

    private void putPiecesOnBoard(Map<Position, Piece> testBoard, List<Position> positions, List<Piece> pieces) {
        for (int i = 0; i < pieces.size(); i++) {
            testBoard.put(positions.get(i), pieces.get(i));
        }
    }

}
