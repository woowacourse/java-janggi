package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.strategy.GreenSoldierMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Test
    @DisplayName("플레이어가 선택한 기물 위치에 플레이어 소유의 기물이 존재하면 예외를 던지지 않는다.")
    void player_select_piece_exist_test() {
        Position current = new Position(3, 3);
        Position target = new Position(5, 2);

        Piece horsePiece = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN),
                new HorseMoveStrategy());
        Piece soldierPiece = Piece.of(new PieceProperty(PieceType.SOLDIER, Team.GREEN),
                new GreenSoldierMoveStrategy());
        Map<Position, Piece> testBoard = new HashMap<>();
        testBoard.put(current, horsePiece);
        testBoard.put(target, soldierPiece);
        Board board = Board.of(testBoard);

        JanggiGame janggiGame = JanggiGame.of(board, GameStatus.GREEN_PLAYER_TURN);

        assertDoesNotThrow(() -> janggiGame.validatePieceSelection(current));
    }

    @Test
    @DisplayName("플레이어가 선택한 기물 위치가 장기판 범위를 벗어나면 예외를 던진다.")
    void player_select_position_out_of_range_throw_exception_test() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Position current = new Position(10, 3);
        Piece horsePiece = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN),
                new HorseMoveStrategy());
        testBoard.put(current, horsePiece);
        Board board = Board.of(testBoard);

        JanggiGame janggiGame = JanggiGame.of(board, GameStatus.GREEN_PLAYER_TURN);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> janggiGame.validatePieceSelection(current))
                .withMessage("[ERROR] 장기판 범위를 벗어났습니다.");
    }

    @Test
    @DisplayName("플레이어가 선택한 기물 위치와 목적지가 동일하다면 예외를 던진다.")
    void player_select_position_and_destination_isSame_throw_exception_test() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Position current = new Position(3, 3);
        Position target = new Position(3, 3);
        Board board = Board.of(testBoard);

        JanggiGame janggiGame = JanggiGame.of(board, GameStatus.GREEN_PLAYER_TURN);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> janggiGame.move(current, target))
                .withMessage("[ERROR] 기물은 제자리 이동이 불가능합니다.");
    }

    @Test
    @DisplayName("플레이어가 선택한 기물 위치에 아군 기물이 존재하지 않으면 예외를 던진다.")
    void player_select_position_piece_not_exist_throw_exception_test() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Position current = new Position(3, 3);
        Piece horsePiece = Piece.of(new PieceProperty(PieceType.HORSE, Team.RED),
                new HorseMoveStrategy());
        testBoard.put(current, horsePiece);
        Board board = Board.of(testBoard);

        JanggiGame janggiGame = JanggiGame.of(board, GameStatus.GREEN_PLAYER_TURN);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> janggiGame.validatePieceSelection(current))
                .withMessage("[ERROR] 본인의 기물이 아닙니다.");
    }

    @Test
    @DisplayName("장기 게임은 게임 종료 알 수 있다.")
    void game_finished_test() {
        Position current = new Position(3, 3);
        Position target = new Position(5, 2);

        Map<Position, Piece> testBoard = new HashMap<>();
        Piece horsePiece = Piece.of(new PieceProperty(PieceType.HORSE, Team.RED),
                new HorseMoveStrategy());
        Piece soldierPiece = Piece.of(new PieceProperty(PieceType.SOLDIER, Team.GREEN),
                new HorseMoveStrategy());
        testBoard.put(current, horsePiece);
        testBoard.put(target, soldierPiece);
        Board board = Board.of(testBoard);

        JanggiGame janggiGame = JanggiGame.of(board, GameStatus.GREEN_PLAYER_TURN);
        janggiGame.checkGameFinished();

        assertThat(janggiGame.isGameFinished()).isTrue();
    }

}
