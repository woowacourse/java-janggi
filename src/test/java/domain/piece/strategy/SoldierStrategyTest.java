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

public class SoldierStrategyTest {
    Map<Position, Piece> dummyBoard;
    PathChecker pathChecker;

    @BeforeEach
    void setUp() {
        dummyBoard = new HashMap<>();

        dummyBoard.put(new Position(1, 4), new Piece(Camp.HAN, PieceType.SOLDIER));
        dummyBoard.put(new Position(1, 7), new Piece(Camp.CHO, PieceType.SOLDIER));
        dummyBoard.put(new Position(4, 1), new Piece(Camp.CHO, PieceType.SOLDIER));
        dummyBoard.put(new Position(4, 3), new Piece(Camp.CHO, PieceType.SOLDIER));
        dummyBoard.put(new Position(4, 8), new Piece(Camp.HAN, PieceType.SOLDIER));
        dummyBoard.put(new Position(4, 10), new Piece(Camp.HAN, PieceType.SOLDIER));


        pathChecker = new Board(dummyBoard);
    }

    @Test
    @DisplayName("졸은 y축의 양의 방향으로 이동 시 예외를 발생한다.")
    void throwException_When_SoldierMove_Y_PlusDirection() {
        Position from = new Position(1, 7);
        Position to = new Position(1, 8);

        Piece soldier = dummyBoard.get(from);

        assertThatThrownBy(() -> soldier.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("병은 y축의 음의 방향으로 이동 시 예외를 발생한다.")
    void throwException_When_SoldierMove_Y_MinusDirection() {
        Position from = new Position(1, 4);
        Position to = new Position(1, 3);

        Piece soldier = dummyBoard.get(from);

        assertThatThrownBy(() -> soldier.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 경로상의 기물 리스트가 비어있는 경우 정상 이동한다.")
    void choSoldier_moveSuccessfully_When_PiecesIsEmpty() {
        Position from = new Position(1, 7);
        Position to = new Position(1, 6);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("병은 경로상의 기물 리스트가 비어있는 경우 정상 이동한다.")
    void hanSoldier_moveSuccessfully_When_PiecesIsEmpty() {
        Position from = new Position(1, 4);
        Position to = new Position(1, 5);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 좌측으로 한 칸 이동할 수 있다.")
    void moveSuccess_When_ChoSoldierMovesLeft() {
        Position from = new Position(1, 7);
        Position to = new Position(2, 7);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 우측으로 한 칸 이동할 수 있다.")
    void moveSuccess_When_ChoSoldierMovesRight() {
        Position from = new Position(3, 7);
        Position to = new Position(2, 7);

        dummyBoard.put(from, new Piece(Camp.CHO, PieceType.SOLDIER));
        pathChecker = new Board(dummyBoard);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("병은 좌측으로 한 칸 이동할 수 있다.")
    void moveSuccess_When_HanSoldierMovesLeft() {
        Position from = new Position(1, 4);
        Position to = new Position(2, 4);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("병은 우측으로 한 칸 이동할 수 있다.")
    void moveSuccess_When_HanSoldierMovesRight() {
        Position from = new Position(3, 4);
        Position to = new Position(2, 4);

        dummyBoard.put(from, new Piece(Camp.HAN, PieceType.SOLDIER));
        pathChecker = new Board(dummyBoard);

        Piece soldier = dummyBoard.get(from);

        assertThatCode(() -> soldier.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 현재 위치가 궁성 내부이고 진행 방향일 때 중앙을 포함한 대각선 이동이 가능하다")
    void moveSuccess_When_ChoSoldierCurrentPositionIsInPalace_And_Forward_And_IncludePalaceCenter() {
        Position from = new Position(4, 3);
        Position to = new Position(5, 2);

        Piece choSoldier = dummyBoard.get(from);

        assertThatCode(() -> choSoldier.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 현재 위치가 궁성 내부이고 진행 방향일 때 중앙을 포함하지 않은 대각선 이동은 예외가 발생한다")
    void throwException_When_ChoSoldierCurrentPositionIsInPalace_And_Forward_And_NotIncludePalaceCenter() {
        Position from = new Position(4, 3);
        Position to = new Position(3, 2);

        Piece choSoldier = dummyBoard.get(from);

        assertThatThrownBy(() -> choSoldier.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 현재 위치가 궁성 내부이고 중앙을 포함하더라도 진행 방향이 아닌 대각선 이동은 예외가 발생한다")
    void throwException_When_ChoSoldierCurrentPositionIsInPalace_And_IncludePalaceCenter_And_NotForward() {
        Position from = new Position(4, 1);
        Position to = new Position(5, 2);

        Piece choSoldier = dummyBoard.get(from);

        assertThatThrownBy(() -> choSoldier.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("병은 현재 위치가 궁성 내부이고 진행 방향일 때 중앙을 포함한 대각선 이동이 가능하다")
    void moveSuccess_When_HanSoldierCurrentPositionIsInPalace_And_Forward_And_IncludePalaceCenter() {
        Position from = new Position(4, 8);
        Position to = new Position(5, 9);

        Piece hanSoldier = dummyBoard.get(from);

        assertThatCode(() -> hanSoldier.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("병은 현재 위치가 궁성 내부이고 진행 방향일 때 중앙을 포함하지 않은 대각선 이동은 예외가 발생한다")
    void throwException_When_HanSoldierCurrentPositionIsInPalace_And_Forward_And_NotIncludePalaceCenter() {
        Position from = new Position(4, 8);
        Position to = new Position(3, 9);

        Piece hanSoldier = dummyBoard.get(from);

        assertThatThrownBy(() -> hanSoldier.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("병은 현재 위치가 궁성 내부이고 중앙을 포함하더라도 진행 방향이 아닌 대각선 이동은 예외가 발생한다")
    void throwException_When_HanSoldierCurrentPositionIsInPalace_And_IncludePalaceCenter_And_NotForward() {
        Position from = new Position(4, 10);
        Position to = new Position(5, 9);

        Piece hanSoldier = dummyBoard.get(from);

        assertThatThrownBy(() -> hanSoldier.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 궁성 내부에서 좌우로 움직일 수 있다")
    void moveLeftOrRightSuccess_When_ChoSoldierCurrentPositionIsInPalace() {
        Position from = new Position(4, 1);
        Position to = new Position(5, 1);

        Piece choSoldier = dummyBoard.get(from);

        assertThatCode(() -> choSoldier.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 궁성 내부에서 직진할 수 있다")
    void moveStraightSuccess_When_ChoSoldierCurrentPositionIsInPalace() {
        Position from = new Position(4, 3);
        Position to = new Position(4, 2);

        Piece choSoldier = dummyBoard.get(from);

        assertThatCode(() -> choSoldier.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("병은 궁성 내부에서 좌우로 움직일 수 있다")
    void moveLeftOrRightSuccess_When_HanSoldierCurrentPositionIsInPalace() {
        Position from = new Position(4, 8);
        Position to = new Position(5, 8);

        Piece hanSoldier = dummyBoard.get(from);

        assertThatCode(() -> hanSoldier.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("병은 궁성 내부에서 직진할 수 있다")
    void moveStraightSuccess_When_HanSoldierCurrentPositionIsInPalace() {
        Position from = new Position(4, 8);
        Position to = new Position(4, 9);

        Piece hanSoldier = dummyBoard.get(from);

        assertThatCode(() -> hanSoldier.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }
}
