package janggi.piece;

import janggi.Board;
import janggi.Player;
import janggi.Position;
import janggi.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SoldierTest {

    @Test
    @DisplayName("규칙에 맞게 움직일 수 있다")
    void move() {
        // given
        Piece soldier = new Soldier(Position.of(2, 2), Team.RED);

        Board board = Board.initialize(List.of(soldier));

        // when
        board.move(new Player(), Position.of(2, 2), Position.of(2, 3));

        // then
        assertThat(board.getPiece(Position.of(2, 3))).isInstanceOf(Soldier.class);
    }

}
