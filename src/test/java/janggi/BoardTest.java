package janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.diagonalMove.Ma;
import janggi.model.gimul.linearMove.Cha;
import janggi.model.gimul.linearMove.Pho;
import janggi.model.gimul.palace.Jang;
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
        Map<Position, AbstractGimul> board = new HashMap<>();

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

        //when
        Board movedBoard = board.move(cho, from, to);

        //then
        assertThatThrownBy(() -> movedBoard.move(cho, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 기물이 존재하지 않습니다.");
    }

    @DisplayName("기물패하면 게임 종료한다.")
    @Test
    void isGameOver() {
        //given
        Map<Position, AbstractGimul> gameOverBoard = new HashMap<>();

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

    @DisplayName("장이 잡히면 게임이 종료된다.")
    @Test
    void isGameOver_jang_captured() {
        Map<Position, AbstractGimul> gameOverBoard = new HashMap<>();

        gameOverBoard.put(
                new Position(Row.SEVEN, Column.FIVE),
                new Jang(Team.CHO)
        );

        Board board = new Board(gameOverBoard);

        assertThat(board.isGameOver()).isTrue();
    }

    @DisplayName("양쪽 장이 살아있으면 게임이 종료되지 않는다.")
    @Test
    void isGameOver_both_jang_alive() {
        Map<Position, AbstractGimul> runningBoard = new HashMap<>();

        runningBoard.put(
                new Position(Row.NINE, Column.FIVE),
                new Jang(Team.CHO)
        );
        runningBoard.put(
                new Position(Row.TWO, Column.FIVE),
                new Jang(Team.HAN)
        );

        Board board = new Board(runningBoard);

        assertThat(board.isGameOver()).isFalse();
    }

    @DisplayName("초나라 기물의 점수 합산을 반환한다.")
    @Test
    void calculateScore_cho() {
        Map<Position, AbstractGimul> board = new HashMap<>();
        board.put(new Position(Row.SEVEN, Column.FIVE), new Cha(Team.CHO));
        board.put(new Position(Row.SEVEN, Column.THREE), new Ma(Team.CHO));
        board.put(new Position(Row.TWO, Column.FIVE), new Jang(Team.HAN));

        Board gameBoard = new Board(board);

        assertThat(gameBoard.calculateScore(Team.CHO)).isEqualTo(new Score(18));
    }

    @DisplayName("한나라 기물의 점수 합산을 반환한다.")
    @Test
    void calculateScore_han() {
        Map<Position, AbstractGimul> board = new HashMap<>();
        board.put(new Position(Row.SEVEN, Column.FIVE), new Cha(Team.HAN));
        board.put(new Position(Row.SEVEN, Column.THREE), new Pho(Team.HAN));
        board.put(new Position(Row.NINE, Column.FIVE), new Jang(Team.CHO));

        Board gameBoard = new Board(board);

        assertThat(gameBoard.calculateScore(Team.HAN)).isEqualTo(new Score(21.5));
    }
}
