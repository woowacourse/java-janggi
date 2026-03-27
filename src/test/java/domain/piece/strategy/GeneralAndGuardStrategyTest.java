package domain.piece.strategy;

import domain.board.Board;
import domain.board.PathChecker;
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

public class GeneralAndGuardStrategyTest {

    Map<Position, Piece> dummyBoard;
    PathChecker pathChecker;

    @BeforeEach
    void setUp() {
        dummyBoard = new HashMap<>();

        dummyBoard.put(
                new Position(1, 1),
                new Piece(Camp.HAN, PieceType.GENERAL, PieceType.GENERAL.createStrategy())
        );

        pathChecker = new Board(dummyBoard);
    }

    @Test
    @DisplayName("x좌표 상 이동할 거리가 1칸 초과면 예외가 발생한다.")
    void throwException_When_X_ForwardOverOne() {
        Position from = new Position(1, 1);
        Position to = new Position(3, 1);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatThrownBy(() -> generalAndGuard.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("y좌표 상 이동할 거리가 1칸 초과면 예외가 발생한다.")
    void throwException_When_Y_ForwardOverOne() {
        Position from = new Position(1, 1);
        Position to = new Position(1, 3);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatThrownBy(() -> generalAndGuard.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("대각선으로 이동하는 경우 예외가 발생한다.")
    void throwException_When_Diagonal_Forward() {
        Position from = new Position(1, 1);
        Position to = new Position(2, 2);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatThrownBy(() -> generalAndGuard.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
