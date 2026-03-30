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

public class SoldierStrategyTest {
    Map<Position, Piece> dummyBoard;
    BoardChecker boardChecker;

    @BeforeEach
    void setUp() {
        dummyBoard = new HashMap<>();

        dummyBoard.put(
                new Position(1, 4),
                new Piece(Camp.HAN, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy(Camp.HAN))
        );
        dummyBoard.put(
                new Position(1, 7),
                new Piece(Camp.CHO, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy(Camp.CHO))
        );

        boardChecker = new Board(dummyBoard);
    }

    @Test
    @DisplayName("졸은 y축의 양의 방향으로 이동 시 예외를 발생한다.")
    void throwException_When_SoldierMove_Y_PlusDirection() {
        Position from = new Position(1, 7);
        Position to = new Position(1, 8);

        Piece soldier = dummyBoard.get(from);

        assertThatThrownBy(() -> soldier.move(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("병은 y축의 음의 방향으로 이동 시 예외를 발생한다.")
    void throwException_When_SoldierMove_Y_MinusDirection() {
        Position from = new Position(1, 4);
        Position to = new Position(1, 3);

        Piece soldier = dummyBoard.get(from);

        assertThatThrownBy(() -> soldier.move(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 경로상의 기물 리스트가 비어있는 경우 정상 이동한다.")
    void choSoldier_moveSuccessfully_When_PiecesIsEmpty() {
        Position from = new Position(1, 7);
        Position to = new Position(1, 6);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.move(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("병은 경로상의 기물 리스트가 비어있는 경우 정상 이동한다.")
    void hanSoldier_moveSuccessfully_When_PiecesIsEmpty() {
        Position from = new Position(1, 4);
        Position to = new Position(1, 5);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.move(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 좌측으로 한 칸 이동할 수 있다.")
    void moveSuccess_When_ChoSoldierMovesLeft() {
        Position from = new Position(1, 7);
        Position to = new Position(2, 7);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.move(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 우측으로 한 칸 이동할 수 있다.")
    void moveSuccess_When_ChoSoldierMovesRight() {
        Position from = new Position(3, 7);
        Position to = new Position(2, 7);

        dummyBoard.put(
                from,
                new Piece(Camp.CHO, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy(Camp.CHO))
        );
        boardChecker = new Board(dummyBoard);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.move(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("병은 좌측으로 한 칸 이동할 수 있다.")
    void moveSuccess_When_HanSoldierMovesLeft() {
        Position from = new Position(1, 4);
        Position to = new Position(2, 4);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.move(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("병은 우측으로 한 칸 이동할 수 있다.")
    void moveSuccess_When_HanSoldierMovesRight() {
        Position from = new Position(3, 4);
        Position to = new Position(2, 4);

        dummyBoard.put(
                from,
                new Piece(Camp.HAN, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy(Camp.HAN))
        );
        boardChecker = new Board(dummyBoard);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.move(from, to, boardChecker))
                .doesNotThrowAnyException();
    }
}
