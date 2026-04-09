package domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import domain.board.Board;
import domain.board.BoardChecker;
import domain.board.Position;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GeneralAndGuardStrategyTest {

    Map<Position, Piece> dummyBoard;
    BoardChecker boardChecker;

    @BeforeEach
    void setUp() {
        dummyBoard = new HashMap<>();

        dummyBoard.put(
                new Position(4, 1),
                new Piece(Camp.HAN, PieceType.GENERAL)
        );

        boardChecker = new Board(dummyBoard);
    }

    @Test
    @DisplayName("x좌표 상 이동할 거리가 1칸 초과면 예외가 발생한다.")
    void throwException_When_X_ForwardOverOne() {
        Position from = new Position(4, 1);
        Position to = new Position(6, 1);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatThrownBy(() -> generalAndGuard.validateMove(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 장군/사는 한 칸 직선 또는 궁 내부 대각선 이동만 가능합니다.");
    }

    @Test
    @DisplayName("y좌표 상 이동할 거리가 1칸 초과면 예외가 발생한다.")
    void throwException_When_Y_ForwardOverOne() {
        Position from = new Position(4, 1);
        Position to = new Position(4, 3);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatThrownBy(() -> generalAndGuard.validateMove(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 장군/사는 한 칸 직선 또는 궁 내부 대각선 이동만 가능합니다.");
    }

    @Test
    @DisplayName("경로상의 기물 리스트가 비어있는 경우 정상 이동한다.")
    void moveSuccess_When_PiecesIsEmpty() {
        Position from = new Position(4, 1);
        Position to = new Position(4, 2);

        Piece generalAndGuard = dummyBoard.get(from);

        assertThatCode(() -> generalAndGuard.validateMove(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("장군과 사는 궁성 내에서 대각선 한 칸 이동할 수 있다.")
    void moveSuccess_When_DiagonalInsidePalace() {
        Position from = new Position(4, 1);
        Position to = new Position(5, 2);

        dummyBoard.clear();
        dummyBoard.put(from, new Piece(Camp.HAN, PieceType.GENERAL));
        boardChecker = new Board(dummyBoard);

        Piece general = dummyBoard.get(from);

        assertThatCode(() -> general.validateMove(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("장군과 사는 궁성 내에서 두 칸 이상 움직일 수 없다.")
    void throwException_When_DiagonalMoveTwoStep() {
        Position from = new Position(4, 1);
        Position to = new Position(6, 3);

        Piece general = dummyBoard.get(from);

        assertThatThrownBy(() -> general.validateMove(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("장군과 사는 궁성 외부 영역으로 이동할 수 없다.")
    void throwException_When_MoveOutPalace() {
        Position from = new Position(5, 3);
        Position to = new Position(5, 4);

        dummyBoard.clear();
        dummyBoard.put(from, new Piece(Camp.HAN, PieceType.GENERAL));
        boardChecker = new Board(dummyBoard);

        Piece general = dummyBoard.get(from);

        assertThatThrownBy(() -> general.validateMove(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 장군/사는 궁성 밖으로 이동할 수 없습니다.");
    }
}
