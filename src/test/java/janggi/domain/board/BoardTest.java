package janggi.domain.board;

import janggi.domain.Camp;
import janggi.domain.position.Position;
import janggi.domain.board.strategy.ElephantHorseElephantHorse;
import janggi.domain.piece.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BoardTest {

    @DisplayName("보드 초기화가 잘 되는지 확인한다")
    @Test
    void initializeToBoard_Always_ReturnCorrectBoard() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());
        Map<Position, Piece> janggiBoard = board.janggiBoard();
        assertThat(janggiBoard.get(Position.of(1, 4))).isInstanceOf(General.class);
        assertThat(janggiBoard.get(Position.of(0, 0))).isInstanceOf(Chariot.class);
        assertThat(janggiBoard.get(Position.of(0, 8))).isInstanceOf(Chariot.class);
        assertThat(janggiBoard.get(Position.of(0, 3))).isInstanceOf(Advisor.class);
        assertThat(janggiBoard.get(Position.of(0, 5))).isInstanceOf(Advisor.class);
        assertThat(janggiBoard.get(Position.of(2, 1))).isInstanceOf(Cannon.class);
        assertThat(janggiBoard.get(Position.of(2, 7))).isInstanceOf(Cannon.class);
        for (int i = 0; i <= 8; i += 2) {
            assertThat(janggiBoard.get(Position.of(3, i))).isInstanceOf(Soldier.class);
        }
        assertThat(janggiBoard.get(Position.of(Camp.CHO.initRowPosition(), 1))).isInstanceOf(Elephant.class);
        assertThat(janggiBoard.get(Position.of(Camp.CHO.initRowPosition(), 2))).isInstanceOf(Horse.class);
        assertThat(janggiBoard.get(Position.of(Camp.CHO.initRowPosition(), 6))).isInstanceOf(Elephant.class);
        assertThat(janggiBoard.get(Position.of(Camp.CHO.initRowPosition(), 7))).isInstanceOf(Horse.class);

        assertThat(janggiBoard.get(Position.of(8, 4))).isInstanceOf(General.class);
        assertThat(janggiBoard.get(Position.of(9, 0))).isInstanceOf(Chariot.class);
        assertThat(janggiBoard.get(Position.of(9, 8))).isInstanceOf(Chariot.class);
        assertThat(janggiBoard.get(Position.of(9, 3))).isInstanceOf(Advisor.class);
        assertThat(janggiBoard.get(Position.of(9, 5))).isInstanceOf(Advisor.class);
        assertThat(janggiBoard.get(Position.of(7, 1))).isInstanceOf(Cannon.class);
        assertThat(janggiBoard.get(Position.of(7, 7))).isInstanceOf(Cannon.class);
        for (int i = 0; i <= 8; i += 2) {
            assertThat(janggiBoard.get(Position.of(6, i))).isInstanceOf(Soldier.class);
        }
        assertThat(janggiBoard.get(Position.of(Camp.HAN.initRowPosition(), 1))).isInstanceOf(Elephant.class);
        assertThat(janggiBoard.get(Position.of(Camp.HAN.initRowPosition(), 2))).isInstanceOf(Horse.class);
        assertThat(janggiBoard.get(Position.of(Camp.HAN.initRowPosition(), 6))).isInstanceOf(Elephant.class);
        assertThat(janggiBoard.get(Position.of(Camp.HAN.initRowPosition(), 7))).isInstanceOf(Horse.class);
    }

    @Test
    void 기물이_보드에_존재하지_않는다면_예외처리한다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());
        int row = 1;
        int col = 0;

        assertThatThrownBy(() -> board.selectPiece(row, col))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보드에 기물이 존재하지 않습니다.");
    }

    @Test
    void 기물이_보드에_존재한다면_기물을_반환한다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());
        int row = 0;
        int col = 0;

        Piece selectPiece = board.selectPiece(row, col);

        Assertions.assertThat(selectPiece).isInstanceOf(Chariot.class);
    }

    @Test
    void 기물이_없는_위치를_선택하면_예외가_발생한다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        assertThatThrownBy(() -> board.movePiece(5, 4, Position.of(4, 4)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보드에 기물이 존재하지 않습니다.");
    }

    @Test
    void 이동_규칙에_맞지_않는_위치로_이동하면_예외가_발생한다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        assertThatThrownBy(() -> board.movePiece(1, 1, Position.of(0, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 좌표입니다.");
    }

    @Test
    void 경로에_기물이_있으면_예외가_발생한다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        assertThatThrownBy(() -> board.movePiece(0, 4, Position.of(0, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("경로가 막혀있습니다.");
    }

    @Test
    void 도착지에_아군_기물이_있으면_예외가_발생한다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        assertThatThrownBy(() -> board.movePiece(0, 1, Position.of(0, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("목적지의 기물을 잡을 수 없습니다.");
    }

    @Test
    void 빈_위치로_이동하면_기물이_옮겨진다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        board.movePiece(4, 0, Position.of(3, 0));

        assertThat(board.janggiBoard().get(Position.of(4, 0))).isNotNull();
        assertThat(board.janggiBoard().get(Position.of(3, 0))).isNull();
    }

    @Test
    void 도착지에_적군_기물이_있으면_잡고_이동한다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());


        board.movePiece(4, 4, Position.of(3, 4));
        board.movePiece(5, 4, Position.of(4, 4));
        board.movePiece(6, 4, Position.of(5, 4)); // 한나라 졸(6,4)을 잡음

        Piece movedPiece = board.janggiBoard().get(Position.of(6, 4));
        assertThat(movedPiece.isSameCamp(Camp.CHO)).isTrue();
        assertThat(board.janggiBoard()).hasSize(31); // 32 - 1
    }
}
