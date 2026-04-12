package janggi.model.turn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.ScorePolicy;
import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.board.PlayingBoard;
import janggi.model.piece.Byeong;
import janggi.model.piece.diagonalMove.Ma;
import janggi.model.piece.diagonalMove.Sang;
import janggi.model.piece.palace.Sa;
import janggi.model.piece.straightMove.Cha;
import janggi.model.piece.straightMove.Pho;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameOverTest {

    @DisplayName("게임 종료 시 턴을 수행하고자 하면 예외 발생한다.")
    @Test
    void play() {
        //given
        Board emptyBoard = PlayingBoard.of(Map.of());

        //when
        assertThatThrownBy(() -> new GameOver(emptyBoard).play(new Position(Row.THREE, Column.EIGHT),
                new Position(Row.FIVE, Column.EIGHT)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("게임 종료 후 턴을 수행할 수 없습니다.");
    }

    @DisplayName("게임이 끝났다.")
    @Test
    void isGameOver() {
        //given
        Board emptyBoard = PlayingBoard.of(Map.of());

        //when
        assertThat(new GameOver(emptyBoard).isGameOver())
                .isTrue();
    }

    @DisplayName("게임의 승리팀을 계산한다.")
    @Test
    void getWinner() {
        //given
        Board board = PlayingBoard.of(Map.of(
                new Position(Row.ONE, Column.ONE), new Byeong(Team.CHO), // 2점, 초나라
                new Position(Row.ONE, Column.TWO), new Sa(Team.HAN), // 3점, 한나라
                new Position(Row.ONE, Column.THREE), new Sang(Team.CHO), // 3점, 초나라
                new Position(Row.ONE, Column.FOUR), new Ma(Team.HAN), // 5점, 한나라
                new Position(Row.ONE, Column.FIVE), new Pho(Team.CHO), // 7점, 초나라
                new Position(Row.ONE, Column.SIX), new Cha(Team.HAN) // 13점, 한나라
        ));

        GameOver gameOver = new GameOver(board);

        //when & then
        assertThat(gameOver.getWinner(new ScorePolicy()))
                .isEqualTo(Team.HAN);   //초나라 12점, 한나라 22.5점
    }
}
