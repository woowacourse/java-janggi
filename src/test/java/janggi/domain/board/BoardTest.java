package janggi.domain.board;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.board.strategy.ElephantHorseElephantHorse;
import janggi.domain.piece.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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
}