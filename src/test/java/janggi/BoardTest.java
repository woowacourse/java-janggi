package janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Board;
import janggi.model.Team;
import janggi.model.gimul.Gimul;
import janggi.model.gimul.Ma;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.Row;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    Board board;

    @BeforeEach
    void beforeEach() {
        Map<Position, Gimul> board = new HashMap<>();

        board.put(
                new Position(Row.SEVEN, Column.FIVE),
                new Ma(Team.CHO)
        );
        board.put(
                new Position(Row.EIGHT, Column.SEVEN),
                new Ma(Team.HAN)
        );
        board.put(
                new Position(Row.SIX, Column.SEVEN),
                new Ma(Team.CHO)
        );
        board.put(
                new Position(Row.SIX, Column.FIVE),
                new Ma(Team.CHO)
        );

        this.board = new Board(board);
    }

    @DisplayName("from에 기물이 없으면 예외가 발생한다.")
    @Test
    void move_empty() {
        //given
        Position from = new Position(Row.ONE, Column.ONE);
        Position to = new Position(Row.THREE, Column.TWO);
        Team cho = Team.CHO;

        //when & then
        assertThatThrownBy(() -> board.move(cho, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 기물이 존재하지 않습니다.");
    }

    @DisplayName("from에 기물이 다른 팀이면 예외가 발생한다.")
    @Test
    void move_different_team() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.FIVE, Column.FOUR);
        Team cho = Team.HAN;

        //when & then
        assertThatThrownBy(() -> board.move(cho, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상대편 기물을 움직일 수 없습니다.");
    }

    @DisplayName("from에 있는 기물이 다른 팀이면 예외가 발생한다.")
    @Test
    void move_gimul_on_path() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.FIVE, Column.FOUR);
        Team cho = Team.CHO;

        //when & then
        assertThatThrownBy(() -> board.move(cho, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로 기물을 움직일 수 없습니다.");
    }

    @DisplayName("to에 같은 팀 기물이 있으면 예외가 발생한다.")
    @Test
    void move_same_team_on_destination() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SIX, Column.SEVEN);
        Team cho = Team.CHO;

        //when & then
        assertThatThrownBy(() -> board.move(cho, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로 기물을 움직일 수 없습니다.");
    }

    @DisplayName("to에 있는 기물을 제거하고 to로 이동한다.")
    @Test
    void move_success() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.EIGHT, Column.SEVEN);
        Team cho = Team.CHO;

        //when & then
        assertThatCode(() -> board.move(cho, from, to))
                .doesNotThrowAnyException();
    }

    @DisplayName("기물패하면 게임 종료한다.")
    @Test
    void isGameOver() {
        //given
        Map<Position, Gimul> gameOverBoard = new HashMap<>();

        gameOverBoard.put(
                new Position(Row.SEVEN, Column.FIVE),
                new Ma(Team.CHO)
        );
        gameOverBoard.put(
                new Position(Row.SIX, Column.SEVEN),
                new Ma(Team.CHO)
        );
        gameOverBoard.put(
                new Position(Row.SIX, Column.FIVE),
                new Ma(Team.CHO)
        );

        Board board = new Board(gameOverBoard);

        //when & then
        assertThat(board.isGameOver()).isTrue();
    }
}