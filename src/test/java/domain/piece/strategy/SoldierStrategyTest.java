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

public class SoldierStrategyTest {
    Map<Position, Piece> dummyBoard;
    BoardChecker boardChecker;

    @BeforeEach
    void setUp() {
        dummyBoard = new HashMap<>();

        dummyBoard.put(
                new Position(1, 4),
                new Piece(Camp.HAN, PieceType.SOLDIER)
        );
        dummyBoard.put(
                new Position(1, 7),
                new Piece(Camp.CHO, PieceType.SOLDIER)
        );

        boardChecker = new Board(dummyBoard);
    }

    @Test
    @DisplayName("졸은 y축의 양의 방향으로 이동 시 예외를 발생한다.")
    void throwException_When_SoldierMove_Y_PlusDirection() {
        Position from = new Position(1, 7);
        Position to = new Position(1, 8);

        Piece soldier = dummyBoard.get(from);

        assertThatThrownBy(() -> soldier.validateMove(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸/병은 후퇴가 불가능합니다.");
    }

    @Test
    @DisplayName("병은 y축의 음의 방향으로 이동 시 예외를 발생한다.")
    void throwException_When_SoldierMove_Y_MinusDirection() {
        Position from = new Position(1, 4);
        Position to = new Position(1, 3);

        Piece soldier = dummyBoard.get(from);

        assertThatThrownBy(() -> soldier.validateMove(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸/병은 후퇴가 불가능합니다.");
    }

    @Test
    @DisplayName("졸은 경로상의 기물 리스트가 비어있는 경우 정상 이동한다.")
    void choSoldier_moveSuccessfully_When_PiecesIsEmpty() {
        Position from = new Position(1, 7);
        Position to = new Position(1, 6);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.validateMove(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 두 칸 이동할 수 없다.")
    void throwException_When_ChoSoldierMovesTwoSteps() {
        Position from = new Position(1, 7);
        Position to = new Position(1, 5);

        Piece soldier = dummyBoard.get(from);

        assertThatThrownBy(() -> soldier.validateMove(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸/병은 좌우 또는 전진으로 한 칸만 이동할 수 있습니다.");
    }

    @Test
    @DisplayName("병은 경로상의 기물 리스트가 비어있는 경우 정상 이동한다.")
    void hanSoldier_moveSuccessfully_When_PiecesIsEmpty() {
        Position from = new Position(1, 4);
        Position to = new Position(1, 5);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.validateMove(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("병은 두 칸 이동할 수 없다.")
    void throwException_When_HanSoldierMovesTwoSteps() {
        Position from = new Position(1, 4);
        Position to = new Position(1, 6);

        Piece soldier = dummyBoard.get(from);

        assertThatThrownBy(() -> soldier.validateMove(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸/병은 좌우 또는 전진으로 한 칸만 이동할 수 있습니다.");
    }

    @Test
    @DisplayName("졸은 좌측으로 한 칸 이동할 수 있다.")
    void moveSuccess_When_ChoSoldierMovesLeft() {
        Position from = new Position(1, 7);
        Position to = new Position(2, 7);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.validateMove(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 우측으로 한 칸 이동할 수 있다.")
    void moveSuccess_When_ChoSoldierMovesRight() {
        Position from = new Position(3, 7);
        Position to = new Position(2, 7);

        dummyBoard.put(
                from,
                new Piece(Camp.CHO, PieceType.SOLDIER)
        );
        boardChecker = new Board(dummyBoard);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.validateMove(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("병은 좌측으로 한 칸 이동할 수 있다.")
    void moveSuccess_When_HanSoldierMovesLeft() {
        Position from = new Position(1, 4);
        Position to = new Position(2, 4);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.validateMove(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("병은 우측으로 한 칸 이동할 수 있다.")
    void moveSuccess_When_HanSoldierMovesRight() {
        Position from = new Position(3, 4);
        Position to = new Position(2, 4);

        dummyBoard.put(
                from,
                new Piece(Camp.HAN, PieceType.SOLDIER)
        );
        boardChecker = new Board(dummyBoard);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.validateMove(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("병은 궁성 안에서 전방 대각선으로 한 칸 이동할 수 있다.")
    void moveSuccess_When_HanSoldierMovesForwardDiagonalInsidePalace() {
        Position from = new Position(4, 1);
        Position to = new Position(5, 2);

        dummyBoard.clear();
        dummyBoard.put(from, new Piece(Camp.HAN, PieceType.SOLDIER));
        boardChecker = new Board(dummyBoard);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.validateMove(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 궁성 안에서 전방 대각선으로 한 칸 이동할 수 있다.")
    void moveSuccess_When_ChoSoldierMovesForwardDiagonalInsidePalace() {
        Position from = new Position(4, 10);
        Position to = new Position(5, 9);

        dummyBoard.clear();
        dummyBoard.put(from, new Piece(Camp.CHO, PieceType.SOLDIER));
        boardChecker = new Board(dummyBoard);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.validateMove(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("병은 궁성 안에서도 후방 대각선으로 이동할 수 없다.")
    void throwException_When_HanSoldierMovesBackwardDiagonalInsidePalace() {
        Position from = new Position(5, 2);
        Position to = new Position(4, 1);

        dummyBoard.clear();
        dummyBoard.put(from, new Piece(Camp.HAN, PieceType.SOLDIER));
        boardChecker = new Board(dummyBoard);

        Piece soldier = dummyBoard.get(from);

        assertThatThrownBy(() -> soldier.validateMove(from, to, boardChecker))
                .hasMessage("[ERROR] 졸/병은 후퇴가 불가능합니다.");
    }
}
