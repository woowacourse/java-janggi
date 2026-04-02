package domain.piece.strategy;

import domain.board.Board;
import domain.board.BoardChecker;
import domain.board.Position;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class GeneralAndGuardStrategyTest {

    Map<Position, Piece> dummyBoard;
    BoardChecker boardChecker;

    @BeforeEach
    void setUp() {
        dummyBoard = new HashMap<>();

        dummyBoard.put(
                new Position(1, 1),
                new Piece(Camp.HAN, PieceType.GENERAL)
        );

        boardChecker = new Board(dummyBoard);
    }

    @Test
    @DisplayName("x좌표 상 이동할 거리가 1칸 초과면 예외가 발생한다.")
    void throwException_When_X_ForwardOverOne() {
        Position from = new Position(1, 1);
        Position to = new Position(3, 1);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatThrownBy(() -> generalAndGuard.move(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 한 칸만 움직일 수 있습니다.");
    }

    @Test
    @DisplayName("y좌표 상 이동할 거리가 1칸 초과면 예외가 발생한다.")
    void throwException_When_Y_ForwardOverOne() {
        Position from = new Position(1, 1);
        Position to = new Position(1, 3);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatThrownBy(() -> generalAndGuard.move(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 한 칸만 움직일 수 있습니다.");
    }

    @Test
    @DisplayName("대각선으로 이동하는 경우 예외가 발생한다.")
    void throwException_When_Diagonal_Forward() {
        Position from = new Position(1, 1);
        Position to = new Position(2, 2);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatThrownBy(() -> generalAndGuard.move(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 한 칸만 움직일 수 있습니다.");
    }

    @Test
    @DisplayName("경로상의 기물 리스트가 비어있는 경우 정상 이동한다.")
    void moveSuccessfully_When_PiecesIsEmpty() {
        Position from = new Position(1, 1);
        Position to = new Position(1, 2);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatCode(() -> generalAndGuard.move(from, to, boardChecker))
                .doesNotThrowAnyException();
    }
}
