package janggi.turn;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.diagonalMove.Ma;
import janggi.model.gimul.palace.Jang;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.Row;
import janggi.model.turn.ChoTurn;
import janggi.model.turn.HanTurn;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HanTurnTest {

    Board board;

    @BeforeEach
    void beforeEach() {
        Map<Position, AbstractGimul> board = new HashMap<>();

        board.put(new Position(Row.SEVEN, Column.FIVE), new Ma(Team.HAN));
        board.put(new Position(Row.EIGHT, Column.SEVEN), new Ma(Team.CHO));
        board.put(new Position(Row.SIX, Column.SEVEN), new Ma(Team.CHO));
        board.put(new Position(Row.SIX, Column.FIVE), new Ma(Team.CHO));
        board.put(new Position(Row.HAN_BACK, Column.FIVE), new Jang(Team.CHO));
        board.put(new Position(Row.TWO, Column.FIVE), new Jang(Team.HAN));

        this.board = new Board(board);
    }

    @DisplayName("초나라 턴을 반환한다.")
    @Test
    void play() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.EIGHT, Column.SEVEN);

        //when & then
        assertThat(new HanTurn(board).play(from, to))
                .isInstanceOf(ChoTurn.class);
    }

    @DisplayName("게임 진행 중인 상태이면 false를 반환한다.")
    @Test
    void isGameOver() {
        assertThat(new HanTurn(board).isGameOver())
                .isFalse();
    }
}
