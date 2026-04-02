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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class GeneralAndGuardStrategyTest {

    Map<Position, Piece> dummyBoard;
    PathChecker pathChecker;

    @BeforeEach
    void setUp() {
        dummyBoard = new HashMap<>();

        dummyBoard.put(new Position(1, 1), new Piece(Camp.HAN, PieceType.GENERAL));
        dummyBoard.put(new Position(4, 1), new Piece(Camp.HAN, PieceType.GUARD));
        dummyBoard.put(new Position(5, 3), new Piece(Camp.HAN, PieceType.GUARD));
        dummyBoard.put(new Position(4, 8), new Piece(Camp.CHO, PieceType.GUARD));
        dummyBoard.put(new Position(5, 9), new Piece(Camp.CHO, PieceType.GENERAL));

        pathChecker = new Board(dummyBoard);
    }

    @Test
    @DisplayName("x좌표 상 이동할 거리가 1칸 초과면 예외가 발생한다.")
    void throwException_When_X_ForwardOverOne() {
        Position from = new Position(4, 1);
        Position to = new Position(6, 1);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatThrownBy(() -> generalAndGuard.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("y좌표 상 이동할 거리가 1칸 초과면 예외가 발생한다.")
    void throwException_When_Y_ForwardOverOne() {
        Position from = new Position(4, 1);
        Position to = new Position(4, 3);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatThrownBy(() -> generalAndGuard.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경로상의 기물 리스트가 비어있는 경우 정상 이동한다.")
    void moveSuccessfully_When_PiecesIsEmpty() {
        Position from = new Position(4, 1);
        Position to = new Position(4, 2);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatCode(() -> generalAndGuard.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("출발지와 도착지가 궁성 내부가 아니라면 예외가 발생한다")
    void throwException_When_FromAndToIsOutPalace() {
        Position from = new Position(4, 1);
        Position to = new Position(3, 1);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatThrownBy(() -> generalAndGuard.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("출발지와 도착지가 궁성 내부이고 중앙을 포함한 대각선 이동이라면 정상 이동한다")
    void fromAndToIsInPalaceAndIncludePalaceCenterDiagonalMove_Then_ReturnTrue() {
        Position from = new Position(4, 1);
        Position to = new Position(5, 2);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatCode(() -> generalAndGuard.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("출발지와 도착지가 궁성 내부이고 중앙을 포함하지 않은 대각선 이동이라면 예외가 발생한다")
    void throwException_When_DoesNotIncludePalaceCenter() {
        Position from = new Position(5, 3);
        Position to = new Position(6, 2);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatThrownBy(() -> generalAndGuard.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("출발지와 도착지가 궁성 내부이고 차이가 한 칸을 초과하면 예외가 발생한다")
    void fromAndToIsInPalaceAndMoreThanOneSpace_Then_ReturnFalse() {
        Position from = new Position(4, 1);
        Position to = new Position(4, 3);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatThrownBy(() -> generalAndGuard.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("현재 위치가 궁성 중앙이면 궁성 내부 모든 곳으로 갈 수 있다")
    void fromAndToIsOnPalaceCenter_Then_CanMoveInPalace() {
        Position from = new Position(5, 9);
        Position to1 = new Position(4, 9);
        Position to2 = new Position(6, 10);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatCode(() -> generalAndGuard.move(from, to1, pathChecker))
                .doesNotThrowAnyException();
        assertThatCode(() -> generalAndGuard.move(from, to2, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("현재 위치가 궁성 중앙이 아니라면 기존 행마법을 적용할 수 있다")
    void fromAndToIsOnPalaceCenter_Then_CanMoveToPalaceCenterOrInPalace() {
        Position from = new Position(4, 1);
        Position to1 = new Position(5, 2);
        Position to2 = new Position(4, 2);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatCode(() -> generalAndGuard.move(from, to1, pathChecker))
                .doesNotThrowAnyException();
        assertThatCode(() -> generalAndGuard.move(from, to2, pathChecker))
                .doesNotThrowAnyException();
    }
}
