package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.strategy.GreenSoldierMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.PalaceMoveStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    private static final Position PALACE_RED_CENTER = new Position(1, 4);
    private static final Position PALACE_GREEN_CENTER = new Position(8, 4);

    @Test
    @DisplayName("플레이어가 선택한 기물 위치에 플레이어 소유의 기물이 존재하면 예외를 던지지 않는다.")
    void player_select_piece_exist_test() {
        Position current = new Position(3, 3);
        Position target = new Position(5, 2);

        Piece horsePiece = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.getInstance());
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
                HorseMoveStrategy.getInstance());
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
                HorseMoveStrategy.getInstance());
        testBoard.put(current, horsePiece);
        Board board = Board.of(testBoard);

        JanggiGame janggiGame = JanggiGame.of(board, GameStatus.GREEN_PLAYER_TURN);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> janggiGame.validatePieceSelection(current))
                .withMessage("[ERROR] 본인의 기물이 아닙니다.");
    }

    @Test
    @DisplayName("초나라 장군이 잡혔을 경우, 한나라 승리 결과를 확인할 수 있다.")
    void green_general_captured_returns_red_win_result_test() {
        Position redHorsePosition = new Position(6, 3);

        Piece greenGeneral = Piece.of(new PieceProperty(PieceType.GENERAL, Team.GREEN),
                PalaceMoveStrategy.getInstance());
        Piece redGeneral = Piece.of(new PieceProperty(PieceType.GENERAL, Team.RED),
                PalaceMoveStrategy.getInstance());
        Piece redHorsePiece = Piece.of(new PieceProperty(PieceType.HORSE, Team.RED),
                HorseMoveStrategy.getInstance());

        Map<Position, Piece> testBoard = new HashMap<>();
        testBoard.put(PALACE_GREEN_CENTER, greenGeneral);
        testBoard.put(PALACE_RED_CENTER, redGeneral);
        testBoard.put(redHorsePosition, redHorsePiece);
        Board board = Board.of(testBoard);
        JanggiGame janggiGame = JanggiGame.of(board, GameStatus.GREEN_PLAYER_TURN);

        janggiGame.move(redHorsePosition, PALACE_GREEN_CENTER);
        janggiGame.checkGameFinished();

        assertThat(janggiGame.isGameFinished()).isTrue();
        assertThat(janggiGame.gameStatus()).isEqualTo(GameStatus.RED_TEAM_WIN);
    }

    @Test
    @DisplayName("한나라 장군이 잡혔을 경우, 초나라 승리 결과를 확인할 수 있다.")
    void red_general_captured_returns_green_win_result_test() {
        Position greenHorsePosition = new Position(3, 3);

        Piece greenGeneral = Piece.of(new PieceProperty(PieceType.GENERAL, Team.GREEN),
                PalaceMoveStrategy.getInstance());
        Piece greenHorsePiece = Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.getInstance());
        Piece redGeneral = Piece.of(new PieceProperty(PieceType.GENERAL, Team.RED),
                PalaceMoveStrategy.getInstance());

        Map<Position, Piece> testBoard = new HashMap<>();
        testBoard.put(PALACE_GREEN_CENTER, greenGeneral);
        testBoard.put(greenHorsePosition, greenHorsePiece);
        testBoard.put(PALACE_RED_CENTER, redGeneral);
        Board board = Board.of(testBoard);
        JanggiGame janggiGame = JanggiGame.of(board, GameStatus.RED_PLAYER_TURN);

        janggiGame.move(greenHorsePosition, PALACE_RED_CENTER);
        janggiGame.checkGameFinished();

        assertThat(janggiGame.isGameFinished()).isTrue();
        assertThat(janggiGame.gameStatus()).isEqualTo(GameStatus.GREEN_TEAM_WIN);
    }

}
